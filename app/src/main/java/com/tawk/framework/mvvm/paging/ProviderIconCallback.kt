package com.banglalink.toffee.common.paging

import com.tawk.framework.mvvm.paging.BaseListItemCallback

interface ProviderIconCallback<T: Any>: BaseListItemCallback<T> {
    fun onProviderIconClicked(item: T){}
}