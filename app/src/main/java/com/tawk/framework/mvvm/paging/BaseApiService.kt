package com.tawk.framework.mvvm.paging

interface BaseApiService<T: Any> {
    suspend fun loadData(offset: Int): List<T>
}