package com.android.githubuserfinder.di.screen

import com.android.githubuserfinder.di.scope.PerScreen
import com.android.githubuserfinder.presentation.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ScreenModule(private val activity: BaseActivity) {

    @PerScreen
    @Provides
    fun providesActivity(): BaseActivity {
        return activity
    }

}