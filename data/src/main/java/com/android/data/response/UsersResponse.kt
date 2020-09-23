package com.android.data.response

import com.android.data.model.Item
import com.google.gson.annotations.SerializedName

data class UsersResponse (
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val itemList: List<Item>
)