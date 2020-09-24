package com.android.data.mapper

import com.android.data.model.Item
import com.android.data.response.UsersResponse
import com.android.domain.model.User
import com.android.domain.model.UserResult
import javax.inject.Inject

/**
 * UserMapper maps the users response objects into the user objects
 */
class UserMapper @Inject constructor() {

    fun map(response: UsersResponse): UserResult {
        return UserResult(response.totalCount, response.itemList.map { map(it) })
    }

    private fun map(item: Item): User {
        return User(
            id = item.id,
            login = item.login,
            avatarUrl = item.avatarUrl
        )
    }

}