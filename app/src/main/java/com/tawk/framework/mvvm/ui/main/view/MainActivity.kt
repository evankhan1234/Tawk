package com.tawk.framework.mvvm.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.tawk.framework.mvvm.R
import com.tawk.framework.mvvm.data.database.entities.Schedule
import com.tawk.framework.mvvm.data.model.User
import com.tawk.framework.mvvm.databinding.ActivityMainBinding
import com.tawk.framework.mvvm.extension.ConnectionWatcher
import com.tawk.framework.mvvm.extension.showLoadingAnimation
import com.tawk.framework.mvvm.extension.showToast
import com.tawk.framework.mvvm.paging.BaseListItemCallback
import com.tawk.framework.mvvm.paging.ListLoadStateAdapter
import com.tawk.framework.mvvm.ui.main.Listener.SchedulerClickListener
import com.tawk.framework.mvvm.ui.main.adapter.MainAdapter
import com.tawk.framework.mvvm.ui.main.adapter.ScheduleAdapter
import com.tawk.framework.mvvm.ui.main.viewmodel.MainViewModel
import com.tawk.framework.mvvm.utils.Util
import com.tawk.framework.mvvm.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BaseListItemCallback<User> , SchedulerClickListener {
    private var listJob: Job? = null
    private lateinit var adapters: MainAdapter
    private  var scheduleAdapter: ScheduleAdapter?=null
    @Inject
    lateinit var connectionWatcher: ConnectionWatcher
    private val mainViewModel by viewModels<MainViewModel>()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var searchWatcher: TextWatcher? = null
    private var search: String = ""
     var offline: Boolean ?=false
     var isActive: Boolean ?=false
    private var scheduleList: List<Schedule>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var isInitialized = false
        adapters = MainAdapter(this)
        observeListId()


        lifecycleScope.launchWhenResumed {
            adapters.loadStateFlow.collectLatest {
                val isLoading = it.source.refresh is LoadState.Loading || !isInitialized
                val isEmpty = adapters.itemCount <= 0 && ! it.source.refresh.endOfPaginationReached
                binding.placeholder.isVisible = isLoading
                binding.recyclerView.isVisible = !isEmpty && !isLoading
                binding.etSearch.isVisible = !isEmpty && !isLoading
                binding.placeholder.showLoadingAnimation(isLoading)
                adapters.withLoadStateFooter(ListLoadStateAdapter { adapters.retry() })
                binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.recyclerView.adapter = adapters
                isInitialized = true
                binding.recyclerView.setHasFixedSize(true)
            }
        }

        binding.recyclerViewSearch.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = scheduleAdapter

        }
        binding.recyclerViewOffline.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = scheduleAdapter

        }

        if(!connectionWatcher.isOnline) {
            showToast(getString(R.string.show_offline_message))
            observeList()
            offline=true
            isActive=true
            binding.recyclerViewOffline.visibility=View.VISIBLE
        }
        else{
            offline=false
            isActive=false

            binding.recyclerViewOffline.visibility=View.GONE
            Handler().postDelayed({
                observeLatestDataList()
            },500

            )

        }
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val da= Util.isNetworkConnected(baseContext)
                if (da){

                    if (offline!!){
                        isActive=false
                        observeLatestDataList()
                        adapters.retry()


                        offline=false
                    }

                }
                else{
                    offline=true
                    isActive=true
                }
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {

            }
        }.start()

        searchWatcher = binding.etSearch.doAfterTextChanged {
            search = it.toString()
            if (search.isNotBlank() && search.isNotEmpty()){
                binding.recyclerViewSearch.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.INVISIBLE
                binding.recyclerViewOffline.visibility=View.INVISIBLE
                searchList("$search%")
            }
            else{
                binding.recyclerView.visibility=View.VISIBLE
                binding.recyclerViewOffline.visibility=View.VISIBLE
                binding.recyclerViewSearch.visibility=View.GONE
            }

        }

    }


    override fun onItemClicked(item: User) {
        super.onItemClicked(item)
        val shareIntent= Intent(this, SchedulerActivity::class.java)
        shareIntent.putExtra("name",item.login)
        shareIntent.putExtra("id",item.id)
        shareIntent.putExtra("avatar",item.avatar_url)
        shareIntent.putExtra("note",item.note)
        startActivity(shareIntent)
    }
    private fun searchList(name:String){

        binding.placeholder.visibility=View.GONE
        binding.etSearch.visibility=View.VISIBLE

        lifecycleScope.launchWhenStarted {
            mainViewModel.getSearchData(name).collectLatest {
                Log.e("UPLOAD 2", "Collecting ->>> ${it.size}")
                if(it.isNotEmpty())
                {
                    binding.recyclerView.visibility=View.INVISIBLE
                    binding.recyclerViewSearch.visibility=View.VISIBLE
                    Log.e("data","data"+it.size)
                    searchData(it)
                }
                else{
                    binding.recyclerView.visibility=View.VISIBLE
                    binding.recyclerViewSearch.visibility=View.GONE
                }

            }
        }

    }

    private fun observeList() {

        Handler().postDelayed({
            binding.placeholder.visibility=View.GONE
            binding.etSearch.visibility=View.VISIBLE
            binding.recyclerViewOffline.visibility=View.VISIBLE
            binding.recyclerView.visibility=View.GONE

            lifecycleScope.launchWhenStarted {
                mainViewModel.getData().collectLatest {
                    Log.e("UPLOAD 2", "Collecting ->>> ${it.size}")
                    if(it.isNotEmpty())
                    {

                        Log.e("data","data"+it.size)
                        data(it)
                    }
                    else{

                    }

                }
            }
        }, 600)

    }

    private fun observeListId(){
        lifecycleScope.launchWhenStarted {
            mainViewModel.getData().collectLatest {
                Log.e("UPLOAD 2", "Collecting ->>> ${it.size}")
                if(it.isNotEmpty())
                {


                    Log.e("data","data"+it.size)
                    scheduleList=it
                }
                else{

                }

            }
        }
    }
    fun searchData( scheduleList: List<Schedule>){
        binding.recyclerViewSearch.layoutManager =LinearLayoutManager(this)
        scheduleAdapter = ScheduleAdapter(this, scheduleList,this)
        binding.recyclerViewSearch.adapter = scheduleAdapter
    }
    fun data( scheduleList: List<Schedule>){

        if (isActive!!){
            binding.recyclerViewOffline.layoutManager =LinearLayoutManager(this)
            scheduleAdapter = ScheduleAdapter(this, scheduleList,this)
            binding.recyclerViewOffline.adapter = scheduleAdapter
        }
        Log.e("data","data"+scheduleList.size)

    }
    private fun observeLatestDataList() {
        listJob?.cancel()
        listJob = lifecycleScope.launchWhenResumed {
            adapters.refresh()
            mainViewModel.loadLatestData().collectLatest {
                adapters.submitData(it.map { channel ->
                    try {
                        if (scheduleList?.size!!>0 && scheduleList!=null)
                        {
                            for(list in scheduleList!!){
                                if (channel.id==list.id){
                                    Log.e("list.note","list.note"+list.note)
                                    //    Log.e("list.note","list.note"+scheduleList?.size)
                                    channel.apply {
                                        note = list.note
                                    }
                                }


                            }
                        }
                    } catch (e: Exception) {
                    }

                    mainViewModel.insertData(channel)
                    channel
                })
            }


        }
    }

    override fun onResume() {
        super.onResume()
        if (Utils.note.equals("Resume")){
            binding.recyclerViewOffline.visibility=View.GONE
            binding.recyclerViewSearch.visibility=View.GONE
            adapters.refresh()
            Utils.note=""
        }
    }

    override fun show(user: Schedule) {
        val shareIntent= Intent(this, SchedulerActivity::class.java)
        shareIntent.putExtra("name",user.login)
        shareIntent.putExtra("id",user.id)
        shareIntent.putExtra("avatar",user.avatar_url)
        shareIntent.putExtra("note",user.note)
        startActivity(shareIntent)
    }
}