package com.android.domain.repository

import com.android.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun getSearchedUsers(
        searchKeyword: String,
        page: Int,
        perPage: Int
    ): Single<List<User>>

}