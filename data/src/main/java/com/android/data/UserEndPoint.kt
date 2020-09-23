package com.android.data

import com.android.data.response.UsersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserEndPoint {

    @GET("search/users")
    fun getSearchedUsers(
        @Query("q") searchKeyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<UsersResponse>

}