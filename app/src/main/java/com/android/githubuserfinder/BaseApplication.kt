package com.android.githubuserfinder

import android.app.Application
import com.android.githubuserfinder.di.application.ApplicationComponent
import com.android.githubuserfinder.di.application.ApplicationModule
import com.android.githubuserfinder.di.application.DaggerApplicationComponent

class BaseApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        inject()
    }

    fun inject() {
        component = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)).build()
        component.inject(this)
    }

}