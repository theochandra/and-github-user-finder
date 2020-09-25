package com.android.githubuserfinder.di.screen

import com.android.githubuserfinder.di.scope.PerScreen
import com.android.githubuserfinder.presentation.main.MainActivity
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

    fun inject(mainActivity: MainActivity)

}