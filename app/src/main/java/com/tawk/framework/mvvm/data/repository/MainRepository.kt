package com.tawk.framework.mvvm.data.repository

import android.util.Log
import com.tawk.framework.mvvm.data.api.ApiHelper
import com.tawk.framework.mvvm.data.api.ApiService
import com.tawk.framework.mvvm.data.model.User
import com.tawk.framework.mvvm.paging.BaseApiService
import com.tawk.framework.mvvm.utils.Utils
import dagger.assisted.AssistedInject
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val toffeeApi: ApiService,
) : BaseApiService<User> {

    override suspend fun loadData(offset: Int): List<User> {
        try {
            val response = toffeeApi.getUser(offset)
            Log.e("response loadData","response"+response.body()?.size)

            return response.body()!!
        } catch (e: Exception) {
            Log.e("response loadData","response"+e.localizedMessage)
            return  emptyList()
        }

    }
}

