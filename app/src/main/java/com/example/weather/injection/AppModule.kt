package com.example.weather.injection

import android.content.Context
import com.example.weather.WeatherApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext context: Context): WeatherApplication {
        return context as WeatherApplication
    }

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.Default + SupervisorJob())
}