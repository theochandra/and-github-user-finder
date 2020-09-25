package com.android.githubuserfinder

class Constants {

    companion object {

        const val BASE_URL = "https://api.github.com/"

        const val SEARCH_SYNTAX = "in:login"
        const val USER_PER_PAGE = 20

        const val DEBOUNCE_TIME: Long = 400

    }

}