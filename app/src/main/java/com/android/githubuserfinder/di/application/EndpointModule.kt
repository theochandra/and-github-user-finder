package com.android.githubuserfinder.di.application

import com.android.data.UserEndPoint
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class EndpointModule {

    @Provides
    @Singleton
    fun provideUserEndPoint(retrofit: Retrofit): UserEndPoint {
        return retrofit.create(UserEndPoint::class.java)
    }

}