package com.ayo.api.di

import android.content.Context
import com.ayo.api.BuildConfig
import com.ayo.api.endpoints.EndPoints
import com.ayo.api.interceptors.NetworkConnectivityInterceptor
import com.ayo.api.interceptors.NetworkResponseInterceptor
import com.ayo.api.services.ApiServiceImpl
import com.ayo.api.utils.addQueryParameterInterceptor
import com.ayo.domain.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    companion object {
        private const val TIME_OUT = 1L
        private const val BASE_URL = "https://api.thedogapi.com/v1/"
        private const val API_KEY_FIELD = "x-api-key"
        private const val API_KEY = "2fbc8037-7510-41ac-b077-92a1a5f5010d"

    }

    @Provides
    fun provideEndpoints(retrofit: Retrofit): EndPoints {
        return retrofit.create(EndPoints::class.java)
    }

    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideHttpClient(
        context: Context,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkConnectivityInterceptor(context))
            .addInterceptor(NetworkResponseInterceptor())
            .addQueryParameterInterceptor(API_KEY_FIELD, API_KEY)
            .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    fun provideApiService(endPoints: EndPoints): ApiService {
        return ApiServiceImpl(endPoints)
    }
}
