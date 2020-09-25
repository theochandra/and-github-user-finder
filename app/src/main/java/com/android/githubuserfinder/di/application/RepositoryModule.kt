package com.android.githubuserfinder.di.application

import com.android.data.UserApi
import com.android.data.mapper.UserMapper
import com.android.data.repository.UserRepositoryImpl
import com.android.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        userMapper: UserMapper
    ): UserRepository {
        return UserRepositoryImpl(userApi, userMapper)
    }

}