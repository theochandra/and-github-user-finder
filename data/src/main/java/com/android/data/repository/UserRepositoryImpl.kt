package com.android.data.repository

import com.android.data.UserApi
import com.android.data.mapper.UserMapper
import com.android.domain.model.User
import com.android.domain.repository.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userMapper: UserMapper
) : UserRepository {

    override fun getSearchedUsers(
        searchKeyword: String,
        page: Int,
        perPage: Int
    ): Single<List<User>> {
        return userApi.getSearchedUsers(searchKeyword, page, perPage)
            .map { userMapper.map(it) }
    }

}