package com.tawk.framework.mvvm.paging

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tawk.framework.mvvm.extension.px

class MarginItemDecoration(spaceHeightDp: Int, private val clipBottom: Boolean = false) : RecyclerView.ItemDecoration() {

    private val spaceHeight = spaceHeightDp.px

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if(!clipBottom || parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1) ?: -1) {
                bottom = spaceHeight
            }
        }
    }
}
