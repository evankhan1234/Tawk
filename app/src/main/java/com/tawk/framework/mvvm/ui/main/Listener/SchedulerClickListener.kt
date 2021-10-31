package com.tawk.framework.mvvm.ui.main.Listener

import com.tawk.framework.mvvm.data.database.entities.Schedule
import com.tawk.framework.mvvm.data.model.User

interface SchedulerClickListener {
    fun show(user: Schedule)
}