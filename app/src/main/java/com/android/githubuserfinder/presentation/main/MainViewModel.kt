package com.android.githubuserfinder.presentation.main

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import com.android.domain.model.User
import com.android.domain.usecase.GetSearchedUsersUseCase
import com.android.domain.usecase.GetSearchedUsersUseCase.Result
import com.android.githubuserfinder.Constants
import com.android.githubuserfinder.ext.addTo
import com.android.githubuserfinder.presentation.listeners.InfiniteScrollListener
import com.android.githubuserfinder.rx.StickyAction
import com.android.githubuserfinder.utils.roundUp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getSearchedUsersUseCase: GetSearchedUsersUseCase
) {

    val disposable = CompositeDisposable()

    val userList = ObservableArrayList<User>()
    val isProgressBarVisible = ObservableBoolean()
    val isActionClearVisible = ObservableBoolean()

    val showErrorGettingUser = StickyAction<ErrorState>()

    private var totalPage: Int = 0
    private var currentPage: Int = 1
    private var keywords: String = ""

    // search github user
    fun searchUsername(searchKeyword: String = keywords) {
        if (currentPage < totalPage) {
            currentPage++
            userList.add(null)
            loadData(keywords)
        }
        if (currentPage == 1) {
            keywords = searchKeyword
            loadData(searchKeyword)
        }
    }

    // get the list of searched users
    private fun loadData(searchKeyword: String) {
        getSearchedUsersUseCase
            .execute(
                "$searchKeyword ${Constants.SEARCH_SYNTAX}",
                currentPage,
                Constants.USER_PER_PAGE
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleResult(it) }
            .addTo(disposable)
    }

    // handle result from getSearchedUsersUseCase
    private fun handleResult(result: Result) {
        if (currentPage == 1)
            isProgressBarVisible.set(result == Result.Loading)

        when (result) {
            is Result.Success -> {
                InfiniteScrollListener.isLoading = false
                if (currentPage == 1) {
                    triggerDataNotFound(result.userResult.totalCount)
                    setTotalPageValue(result.userResult.totalCount)
                }
                if (currentPage > 1)
                    removeNullItem()
                userList.addAll(result.userResult.userList)
            }
            is Result.Failure -> {
                InfiniteScrollListener.isLoading = false
                if (currentPage > 1)
                    removeNullItem()
                showErrorGettingUser.trigger(ErrorState.UNEXPECTED_ERROR)
            }
        }
    }

    // set total page after get total count from getSearchedUsersUseCase
    private fun setTotalPageValue(totalCount: Int) {
        if (totalPage != 0) return
        totalPage = roundUp(totalCount, Constants.USER_PER_PAGE)
    }

    // remove loading item view
    private fun removeNullItem() {
        if (userList.last() == null)
            userList.removeAt(userList.size - 1)
    }

    // trigger error when data not found
    private fun triggerDataNotFound(totalPage: Int) {
        if (totalPage == 0)
            showErrorGettingUser.trigger(ErrorState.DATA_NOT_FOUND)
    }

    // re initialize page value
    fun reInitPageValue() {
        totalPage = 0
        currentPage = 1
    }

    // hide progress bar visibility
    fun hideProgressBar() {
        isProgressBarVisible.set(false)
    }

    // clear user list
    fun clearUserList() {
        userList.clear()
    }

    // clean up method
    fun clearDisposable() {
        disposable.clear()
    }

}