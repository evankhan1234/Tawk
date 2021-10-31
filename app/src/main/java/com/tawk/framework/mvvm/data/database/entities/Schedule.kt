package com.tawk.framework.mvvm.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule (
    @PrimaryKey
    var id: Long = 0 ,
    val login: String= "",
    val node_id: String = "",
    val avatar_url: String = "",
    val gravatar_id: String = "",
    val url: String = "",
    val html_url: String = "",
    val followers_url: String = "",
    val starred_url: String = "",
    val subscriptions_url: String = "",
    val organizations_url: String = "",
    val repos_url: String = "",
    val events_url: String = "",
    val received_events_url: String = "",
    val type: String = "",
    val note: String = "",
    val site_admin: Boolean = false,

 )