package com.android.githubuserfinder.di.application

import com.android.githubuserfinder.BaseApplication
import com.android.githubuserfinder.BuildConfig
import com.android.githubuserfinder.Constants
import com.android.githubuserfinder.interceptor.BasicAuthInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): BaseApplication {
        return application
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_APIARY)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

}