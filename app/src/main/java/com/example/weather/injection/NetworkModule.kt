package com.example.weather.injection

import android.content.Context
import android.os.Environment
import com.example.weather.BuildConfig
import com.example.weather.configuration.Constant
import com.example.weather.data.datasource.remote.WeatherApi
import com.example.weather.data.datasource.remote.interceptor.WeatherInterceptor
import com.example.weather.data.repository.SettingRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    @Singleton
    @Provides
    @Named("cached")
    fun provideOkHttpClient(@ApplicationContext context: Context, settingRepository: SettingRepository): OkHttpClient {
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)

        val interceptor = WeatherInterceptor(context = context, settingRepository = settingRepository)

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .readTimeout(Constant.TIME_OUT_API, TimeUnit.SECONDS)
            .writeTimeout(Constant.TIME_OUT_API, TimeUnit.SECONDS)
            .connectTimeout(Constant.TIME_OUT_API, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    @Named("non_cached")
    fun provideNonCachedOkHttpClient(@ApplicationContext context: Context, settingRepository: SettingRepository): OkHttpClient {

        val interceptor = WeatherInterceptor(context = context, settingRepository = settingRepository)

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .readTimeout(Constant.TIME_OUT_API, TimeUnit.SECONDS)
            .writeTimeout(Constant.TIME_OUT_API, TimeUnit.SECONDS)
            .connectTimeout(Constant.TIME_OUT_API, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@Named("non_cached") client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addConverterFactory(JacksonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit.Builder): WeatherApi {
        return retrofit.baseUrl(BuildConfig.API_URL).build().create(WeatherApi::class.java)
    }
}