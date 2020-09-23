package com.android.data.model

import com.google.gson.annotations.SerializedName

data class Item (
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)