package com.tawk.framework.mvvm.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.tawk.framework.mvvm.R
import com.tawk.framework.mvvm.data.model.User

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min

@Singleton
class BindingUtil @Inject constructor() {


//    companion object{
//        @BindingAdapter("loadChannelImage")
//        @JvmStatic
//        fun bindChannel(view: ImageView,imageUrl: String) {
//            view.load(imageUrl) {
//                transformations(CircleCropTransformation())
//                crossfade(false)
//                fallback(R.drawable.ic_activities_empty)
//                placeholder(R.drawable.ic_activities_empty)
//            }
//        }
//    }


}