package com.android.githubuserfinder.di.application

import com.android.githubuserfinder.BaseApplication
import com.android.githubuserfinder.di.screen.ScreenComponent
import com.android.githubuserfinder.di.screen.ScreenModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, EndpointModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(activity: BaseApplication)

    fun plus(screenModule: ScreenModule): ScreenComponent

}