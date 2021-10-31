package com.tawk.framework.mvvm.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.tawk.framework.mvvm.data.model.User
import com.tawk.framework.mvvm.databinding.ActivitySchedulerBinding
import com.tawk.framework.mvvm.ui.main.viewmodel.MainViewModel
import com.tawk.framework.mvvm.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import java.lang.Exception


@AndroidEntryPoint
class SchedulerActivity : AppCompatActivity(){
    private var listJob: Job? = null
    private var _binding: ActivitySchedulerBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()
    private var noteWatcher: TextWatcher? = null
    private var note: String = ""
    private var data: User?=null
    private var name: String = ""
    private var avatar: String = ""
    private var id: Long =0
    private var offline: Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySchedulerBinding.inflate(layoutInflater)
        setContentView(binding.root)
         name = intent.getStringExtra("name")!!
         id = intent.getLongExtra("id",0)
         avatar = intent.getStringExtra("avatar")!!
        note = intent.getStringExtra("note")!!
        observeLatestDataList(name)
        binding.imgBack.setOnClickListener {
            finish()
        }
        noteWatcher = binding.etNote.doAfterTextChanged {
            note = it.toString()
            binding.btnSave.isEnabled = note.isNotBlank() && note.isNotEmpty()
        }
        if (note.isNotBlank() && note.isNotEmpty()){
            binding.etNote.setText(note)
        }
        binding.btnSave.setOnClickListener {
            if (offline){
                mainViewModel.updateData(note,id)
            }
            else{
                mainViewModel.updateData(note,data?.id!!)
            }
            Utils.note="Resume"
            finish()
        }
    }

    private fun observeLatestDataList(name: String?)
    {
        listJob?.cancel()
        listJob = lifecycleScope.launchWhenResumed {

             try {
                 offline=false
                 data= mainViewModel.getDetails(name)
                 binding.tvName.text=data?.name
                 binding.tvSecondName.text="Name : "+data?.name
                 binding.tvCompany.text="Company : "+data?.company
                 binding.tvBlog.text="Blog : "+data?.blog
                 binding.tvFollowers.text="Followers : "+data?.followers
                 binding.tvFollowing.text="Following : "+data?.following
                 Utils.loadChannelImageOne(binding.imgAvatar,data?.avatar_url)
             } catch (e: Exception) {
                 offline=true
                 binding.tvName.text=name
                 binding.tvSecondName.text="Name : "+ name
                 binding.tvCompany.text="Company : Not showing of Internet Issues"
                 binding.tvBlog.text="Blog : Not showing of Internet Issues"
                 binding.tvFollowers.text="Followers : N/A "
                 binding.tvFollowing.text="Following : N/A"
                 Utils.loadChannelImageOne(binding.imgAvatar,data?.avatar_url)
             }

    }
    }
}