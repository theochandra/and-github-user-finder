package com.android.domain.usecase

import com.android.domain.model.User
import com.android.domain.model.UserResult
import com.android.domain.repository.UserRepository
import com.android.domain.usecase.GetSearchedUsersUseCase.Result.Loading
import com.android.domain.usecase.GetSearchedUsersUseCase.Result.Success
import com.android.domain.usecase.GetSearchedUsersUseCase.Result.Failure
import io.reactivex.Observable
import javax.inject.Inject

class GetSearchedUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val userResult: UserResult) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(
        searchKeyword: String,
        page: Int,
        perPage: Int
    ): Observable<Result> {
        return userRepository.getSearchedUsers(searchKeyword, page, perPage)
            .toObservable()
            .map { Success(it) as Result }
            .onErrorReturn { Failure(it) }
            .startWith(Loading)
    }

}