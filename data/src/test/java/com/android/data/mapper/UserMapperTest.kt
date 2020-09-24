package com.android.data.mapper

import com.android.data.model.Item
import com.android.data.response.UsersResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class UserMapperTest {

    private lateinit var sut: UserMapper
    private lateinit var response: UsersResponse

    @Before
    fun setup() {
        sut = UserMapper()
        response = UsersResponse(
            totalCount = 100,
            incompleteResults = false,
            itemList = arrayListOf<Item>()
        )
    }

    @Test
    fun `maps total count`() {
        val result = sut.map(response)

        assertThat(result.totalCount, equalTo(response.totalCount))
    }

    @Test
    fun `maps item list`() {
        val result = sut.map(response)

        assertThat(result.userList, equalTo(response.itemList))
    }

}