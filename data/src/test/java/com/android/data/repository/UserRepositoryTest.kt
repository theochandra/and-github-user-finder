package com.android.data.repository

import com.android.data.UserApi
import com.android.data.mapper.UserMapper
import com.android.data.response.UsersResponse
import com.android.domain.model.UserResult
import com.android.domain.repository.UserRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Mock lateinit var userApi: UserApi
    @Mock lateinit var userMapper: UserMapper
    private lateinit var sut: UserRepository

    @Before
    fun setup() {
        sut = UserRepositoryImpl(userApi, userMapper)
    }

    @Test
    fun `getSearchedUsers gets the searched users`() {
        given(userApi.getSearchedUsers(any(), any(), any())).willReturn(Single.just(mock()))
        given(userMapper.map(any<UsersResponse>())).willReturn(mock())

        sut.getSearchedUsers(
            searchKeyword = "theo in:login",
            page = 1,
            perPage = 10
        ).test()
        verify(userApi).getSearchedUsers(any(), any(), any())
    }

    @Test
    fun `getSearchedUsers maps the response`() {
        val response = mock<UsersResponse>()

        given(userApi.getSearchedUsers(any(), any(), any())).willReturn(Single.just(response))
        given(userMapper.map(any<UsersResponse>())).willReturn(mock())

        sut.getSearchedUsers(
            searchKeyword = "theo in:login",
            page = 1,
            perPage = 10
        ).test()
        verify(userMapper).map(eq(response))
    }

    @Test
    fun `getSearchedUsers returns mapped response`() {
        val response = mock<UsersResponse>()
        val mappedResponse = mock<UserResult>()

        given(userApi.getSearchedUsers(any(), any(), any())).willReturn(Single.just(response))
        given(userMapper.map(any<UsersResponse>())).willReturn(mappedResponse)

        sut.getSearchedUsers(
            searchKeyword = "theo in:login",
            page = 1,
            perPage = 10
        ).test().assertValue(mappedResponse)
    }

    @Test
    fun `getSearchedUsers returns error on failure`() {
        val error = Throwable()

        given(userApi.getSearchedUsers(any(), any(), any())).willReturn(Single.error(error))

        sut.getSearchedUsers(
            searchKeyword = "theo in:login",
            page = 1,
            perPage = 10
        ).test().assertError(error)
    }

}