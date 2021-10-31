package com.tawk.framework.mvvm.extension

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.facebook.shimmer.ShimmerFrameLayout
import java.text.SimpleDateFormat
import java.util.*

private const val TITLE_PATTERN = "^[\\w\\d_.-]+$"
//private const val EMAIL_PATTERN = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,4}$"
private const val ADDRESS_PATTERN = ""
private const val DESCRIPTION_PATTERN = ""
private const val PHONE_PATTERN = "^(?:\\+8801|01)(?:\\d{9})$"



fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

val Int.dp: Int get() {
    return (this/Resources.getSystem().displayMetrics.density).toInt()
}

val Float.dp: Float get() {
    return (this/Resources.getSystem().displayMetrics.density)
}

val Int.sp: Int get() {
    return (this/Resources.getSystem().displayMetrics.scaledDensity).toInt()
}

val Float.sp: Float get() {
    return (this/Resources.getSystem().displayMetrics.scaledDensity)
}

val Int.px: Int get() {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

val Float.px: Float get() {
    return (this * Resources.getSystem().displayMetrics.density)
}

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

fun Long.toFormattedDate(): String{
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dhaka"))
    val cal = Calendar.getInstance(TimeZone.getDefault())
    cal.timeInMillis = this
    val dateGMT = cal.time
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    return sdf.format(dateGMT)
}

fun ViewGroup.showLoadingAnimation(isStart: Boolean) {
    this.forEach {
        if (it is ShimmerFrameLayout) {
            if (isStart && !it.isShimmerStarted) {
                it.startShimmer()
            }
            else {
                it.stopShimmer()
            }
        }
    }
}


