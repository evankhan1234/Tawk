package com.tawk.framework.mvvm.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.tawk.framework.mvvm.R
import com.tawk.framework.mvvm.databinding.LoadStateFooterViewItemBinding

class ListLoadStateViewHolder(
        private val binding: LoadStateFooterViewItemBinding,
        retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    /*init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }*/

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
        /*if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading*/
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ListLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.load_state_footer_view_item, parent, false)
            val binding = LoadStateFooterViewItemBinding.bind(view)
            return ListLoadStateViewHolder(binding, retry)
        }
    }
}
