package com.tawk.framework.mvvm.paging

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tawk.framework.mvvm.BR

class BaseViewHolder(val binding: ViewDataBinding)
    :RecyclerView.ViewHolder(binding.root) {

    fun bind(obj: Any, callback: Any?, position: Int, selected: Any? = null) {

        binding.setVariable(BR.callback, callback)
        binding.setVariable(12, position)
        binding.setVariable(BR.data, obj)
        if(selected != null) {
            binding.setVariable(BR._all, selected)
        }

        binding.executePendingBindings()
    }
}