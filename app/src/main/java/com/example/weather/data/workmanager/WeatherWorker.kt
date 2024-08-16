package com.example.weather.data.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.weather.WeatherApplication
import com.example.weather.data.repository.SettingRepository
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.LocationInfo
import com.example.weather.util.DateUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.concurrent.TimeUnit


/**
 * # [Getting started with WorkManager](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started)
 */
@HiltWorker
class WeatherWorker
@AssistedInject
constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val settingRepository: SettingRepository,
) : CoroutineWorker(context, workerParams) {

    private val TAG = "WorkManager"

    override suspend fun doWork(): Result {
        // Do the work here--in this case, upload the images.
        Log.d(TAG, "doWork - this is ${this.javaClass.simpleName}")
        val result = updateWeatherForecast()

        // Indicate whether the work finished successfully with the Result
        return result
    }

    private suspend fun updateWeatherForecast(): Result  {
        settingRepository.setLastTimeUpdate(date = Date())
        try {
            withContext(Dispatchers.IO) {
                val listOfLocationInfo: List<LocationInfo> = weatherRepository.findAllLocationInfo()
                listOfLocationInfo.mapIndexed { index: Int, locationInfo: LocationInfo ->
                    Log.d(TAG, "doWork - ${this.javaClass.simpleName} - updateWeatherForecast with index $index for ${locationInfo.administrativeArea?.localizedName} with locationKey ${locationInfo.locationKey}")
                    async {
                        findActualCurrentCondition(locationKey = locationInfo.locationKey)
                    }
                }.awaitAll()
            }


            val delayDuration = DateUtil.calculateInitialDelay(targetHour = 3, targetMinute = 0)
            val weatherWorker = OneTimeWorkRequestBuilder<WeatherWorker>()
                .setInitialDelay(
                    delayDuration,
                    TimeUnit.MILLISECONDS
                )
                .build()


            WorkManager.getInstance(applicationContext).enqueue(weatherWorker)

            // Indicate whether the work finished successfully with the Result
            return Result.success()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.failure()
        }
    }

    private suspend fun findActualCurrentCondition(locationKey: String) : CurrentCondition {
        if (locationKey.isEmpty()) return CurrentCondition()

        return withContext(Dispatchers.IO) {
            weatherRepository.getCurrentCondition2(
                fetchFromRemote = false,
                locationKey = locationKey
            )
        }
    }
}
