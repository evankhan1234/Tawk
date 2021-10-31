package com.tawk.framework.mvvm.utils

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class FemirasDrawerLayout  @JvmOverloads constructor(context: Context,
                                                     attrs: AttributeSet? = null,
                                                     defStyleAttr: Int = 0
): DrawerLayout(context, attrs, defStyleAttr) {
    override fun open() {
        openDrawer(GravityCompat.START)
    }

    override fun close() {
        closeDrawer(GravityCompat.START)
    }
}