package com.tawk.framework.mvvm.ui.main.adapter

import android.app.Activity
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawk.framework.mvvm.R

import kotlinx.android.synthetic.main.item_layout.view.*

import com.tawk.framework.mvvm.ui.main.Listener.PackageListener
import android.content.pm.PackageManager
import android.content.res.Resources

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.tawk.framework.mvvm.data.model.User
import com.tawk.framework.mvvm.databinding.ItemLayoutBinding
import com.tawk.framework.mvvm.paging.BaseListItemCallback
import com.tawk.framework.mvvm.paging.BasePagingDataAdapter
import com.tawk.framework.mvvm.paging.BaseViewHolder
import com.tawk.framework.mvvm.paging.ItemComparator
import com.tawk.framework.mvvm.ui.main.Listener.ActivityListener
import kotlin.math.ceil

class MainAdapter(
      cb: BaseListItemCallback<User>,
) : BasePagingDataAdapter<User>( cb , ItemComparator()) {

    var counnt=3

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_layout
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        val obj = getItem(position)
        obj?.let {
            if (holder.binding is ItemLayoutBinding) {
                holder.binding.container.setOnClickListener {
                    callback?.onItemClicked(obj)
                }
                var pos=position +1
                Log.e("position","position"+pos)
                if(pos % 4  == 0){ counnt += 4

                    holder.binding.imageViewAvataone.visibility=View.VISIBLE
                    holder.binding.imageViewAvatar.visibility=View.INVISIBLE
                    // holder.binding..setImageDrawable(null)
                }
                else{
                    holder.binding.imageViewAvatar.visibility=View.VISIBLE
                    holder.binding.imageViewAvataone.visibility=View.INVISIBLE
                }
            }
            holder.bind(obj, callback, position, 15)

         //   holder.itemView.imageViewAvatar.visibility=View.VISIBLE

        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        if (holder.binding is ItemLayoutBinding) {
            holder.binding.imageViewAvatar.setImageDrawable(null)
        }
        super.onViewRecycled(holder)
    }
}