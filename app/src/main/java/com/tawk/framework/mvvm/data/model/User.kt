package com.tawk.framework.mvvm.data.model

import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("login")
    val login: String = "",
    @SerializedName("node_id")
    val node_id: String = "",
    @SerializedName("avatar_url")
    val avatar_url: String = "",

    @SerializedName("gravatar_id")
    val gravatar_id: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("html_url")
    val html_url: String = "",
    @SerializedName("followers_url")
    val followers_url: String = "",

    @SerializedName("starred_url")
    val starred_url: String = "",
    @SerializedName("subscriptions_url")
    val subscriptions_url: String = "",
    @SerializedName("organizations_url")
    val organizations_url: String = "",
    @SerializedName("repos_url")
    val repos_url: String = "",

    @SerializedName("events_url")
    val events_url: String = "",
    @SerializedName("received_events_url")
    val received_events_url: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("site_admin")
    val site_admin: Boolean = false,
    @SerializedName("note")
    var note: String = "",

    @SerializedName("name")
    val name: String = "",
    @SerializedName("company")
    val company: String = "",
    @SerializedName("blog")
    val blog: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("followers")
    var followers: Int = 0,
    @SerializedName("following")
    var following: Int = 0,

): Parcelable {

}