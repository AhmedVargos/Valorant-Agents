package com.ahmedvargos.remote.di

import android.content.Context
import com.ahmedvargos.remote.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier

@Qualifier
annotation class BaseUrl

@Qualifier
annotation class IsDebugBuild

@Qualifier
annotation class OkHttpLogger

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @BaseUrl
    internal fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @IsDebugBuild
    internal fun provideIsDebugBuild(): Boolean = BuildConfig.DEBUG

    @Provides
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @OkHttpLogger
    internal fun provideOkHttpClientLogger(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    internal fun provideOkHttpClient(
        @OkHttpLogger logger: Interceptor,
        @IsDebugBuild isDebugBuild: Boolean
    ): OkHttpClient {
        var client = OkHttpClient.Builder()
        if (isDebugBuild)
            client = OkHttpClient.Builder().addInterceptor(logger)

        return client.build()
    }

    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                ).asLenient()
            )
            .build()
    }
}
