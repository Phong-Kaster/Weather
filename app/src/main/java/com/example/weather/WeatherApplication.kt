package com.example.weather

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/*****************************************************
 * # [Inject WorkManager with Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack#workmanager)
 */
@HiltAndroidApp
class WeatherApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}