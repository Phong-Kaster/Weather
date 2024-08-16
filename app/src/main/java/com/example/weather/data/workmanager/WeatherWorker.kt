package com.example.weather.data.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weather.WeatherApplication
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.LocationInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


/**
 * # [Getting started with WorkManager](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started)
 */
@HiltWorker
class WeatherWorker
@AssistedInject
constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val weatherRepository: WeatherRepository
) : CoroutineWorker(context, workerParams) {

    private val TAG = "WorkManager"

    override suspend fun doWork(): Result {
        // Do the work here--in this case, upload the images.
        Log.d(TAG, "doWork - this is ${this.javaClass.simpleName}")

        // call weather repository find all location info
        updateWeatherForecast()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }

    private suspend fun updateWeatherForecast(){
        Log.d(TAG, "doWork - updateWeatherForecast")
        withContext(Dispatchers.IO) {
            val listOfLocationInfo: List<LocationInfo> = weatherRepository.findAllLocationInfo()
            listOfLocationInfo.mapIndexed { index: Int, locationInfo: LocationInfo ->
                Log.d(TAG, "doWork - updateWeatherForecast with index $index for ${locationInfo.administrativeArea?.localizedName}")
                async {
                    findActualCurrentCondition(locationKey = locationInfo.locationKey)
                }
            }.awaitAll()
        }
    }

    private suspend fun findActualCurrentCondition(locationKey: String) : CurrentCondition {
        Log.d(TAG, "doWork - updateWeatherForecast with location key = $locationKey ")
        if (locationKey.isEmpty()) return CurrentCondition()

        return withContext(Dispatchers.IO) {
            weatherRepository.getCurrentCondition2(
                fetchFromRemote = true,
                locationKey = locationKey
            )
        }
    }
}
