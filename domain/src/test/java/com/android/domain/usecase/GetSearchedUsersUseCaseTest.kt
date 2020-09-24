package com.android.domain.usecase

import com.android.domain.model.User
import com.android.domain.model.UserResult
import com.android.domain.repository.UserRepository
import com.android.domain.rx.RxJavaTestHooksResetRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import com.android.domain.usecase.GetSearchedUsersUseCase.Result.Loading
import com.android.domain.usecase.GetSearchedUsersUseCase.Result.Success
import com.android.domain.usecase.GetSearchedUsersUseCase.Result.Failure

@RunWith(MockitoJUnitRunner::class)
class GetSearchedUsersUseCaseTest {

    @get:Rule
    var rxJavaTestHooksResetRule = RxJavaTestHooksResetRule()

    @Mock lateinit var userRepository: UserRepository

    private lateinit var sut: GetSearchedUsersUseCase

    @Before
    fun setup() {
        sut = GetSearchedUsersUseCase(userRepository)
    }

    @Test
    fun `get searched user`() {
        given(userRepository.getSearchedUsers(any(), any(), any()))
            .willReturn(Single.error(Throwable()))

        sut.execute("theo", 1, 10).test()

        verify(userRepository).getSearchedUsers(any(), any(), any())
    }

    @Test
    fun `shows loading when start`() {
        given(userRepository.getSearchedUsers(any(), any(), any()))
            .willReturn(Single.just(mock()))

        sut.execute("theo", 1, 10).test()
            .assertValueAt(0) { it == Loading }
    }

    @Test
    fun `returns the success result when success get data`() {
        val userResult = UserResult(10, arrayListOf<User>())

        given(userRepository.getSearchedUsers(any(), any(), any()))
            .willReturn(Single.just(userResult))

        sut.execute("theo", 1, 10).test()
            .assertValueAt(1) { (it as Success).userResult == userResult }
    }

    @Test
    fun `returns the failure result when error get data`() {
        val throwable = Throwable()

        given(userRepository.getSearchedUsers(any(), any(), any()))
            .willReturn(Single.error(throwable))

        sut.execute("theo", 1, 10).test()
            .assertValueAt(1) { (it as Failure).throwable == throwable }
    }

}