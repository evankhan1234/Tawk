package com.tawk.framework.mvvm.ui.main.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.tawk.framework.mvvm.R
import com.tawk.framework.mvvm.data.database.entities.Schedule
import com.tawk.framework.mvvm.ui.main.Listener.SchedulerClickListener
import kotlinx.android.synthetic.main.item_layout.view.*


class ScheduleAdapter (val context: Activity,val scheduleList: List<Schedule>, val listener: SchedulerClickListener) : RecyclerView.Adapter<ScheduleAdapter
.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cellForRow = inflater.inflate(R.layout.item_layout, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {
        val pos=position +1
        Log.e("position","position"+pos)
        if (scheduleList[position].note.equals("")){
            holder.itemView.imgNote.visibility= View.GONE
        }
        else{
            holder.itemView.imgNote.visibility= View.VISIBLE
        }
        if(pos % 4  == 0){
            holder.itemView.imageViewAvataone.visibility=View.VISIBLE
            holder.itemView.imageViewAvatar.visibility=View.INVISIBLE
        }
        else{
            holder.itemView.imageViewAvatar.visibility=View.VISIBLE
            holder.itemView.imageViewAvataone.visibility=View.INVISIBLE
        }
        holder.itemView.setOnClickListener {
            listener.show(scheduleList[position])
        }
        holder.itemView.textViewUserName.text= scheduleList[position].login
        holder.itemView.imageViewAvatar.load(scheduleList[position].avatar_url) {
            transformations(CircleCropTransformation())
            crossfade(false)
            fallback(R.drawable.ic_menu_profile)
            placeholder(R.drawable.ic_menu_profile)
        }

    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
}