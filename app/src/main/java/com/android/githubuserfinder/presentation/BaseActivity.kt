package com.android.githubuserfinder.presentation

import androidx.appcompat.app.AppCompatActivity
import com.android.githubuserfinder.BaseApplication
import com.android.githubuserfinder.di.application.ApplicationComponent
import com.android.githubuserfinder.di.screen.ScreenModule

open class BaseActivity : AppCompatActivity() {

    val screenComponent by lazy {
        getApplicationComponent().plus(ScreenModule(this))
    }

    private fun getApplicationComponent(): ApplicationComponent {
        return (application as BaseApplication).component
    }

}