package com.android.data

import com.android.data.response.UsersResponse
import io.reactivex.Single
import javax.inject.Inject

class UserApi @Inject constructor(private val userEndPoint: UserEndPoint) {

    fun getSearchedUsers(
        searchKeyword: String,
        page: Int,
        perPage: Int
    ): Single<UsersResponse> {
        return userEndPoint.getSearchedUsers(searchKeyword, page, perPage)
    }

}