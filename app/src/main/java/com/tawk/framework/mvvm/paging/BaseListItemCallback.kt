package com.tawk.framework.mvvm.paging

import android.view.View

interface BaseListItemCallback<T: Any> {
    fun onItemClicked(item: T) {}
    fun onOpenMenu(view: View, item: T) {}
}