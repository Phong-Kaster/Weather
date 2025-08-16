package com.example.weather.data.repository

import android.util.Log
import com.example.weather.data.datasource.remotektor.KtorWeatherApi
import com.example.weather.domain.mapper.LocationAutoMapper.toLocationInfoModel
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.status.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Ktor Weather Repository
 *
 * Handles mapping, error handling, returns domain models
 */
@Singleton
class KtorWeatherRepository @Inject constructor(
    private val ktorWeatherApi: KtorWeatherApi // <-- depends on KtorWeatherApi, not HttpClient directly!
) {
    private val TAG = this.javaClass.simpleName
    /*********************************
     * # [Autocomplete search](https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/autocomplete)
     */
    fun searchAutocomplete(keyword: String): Flow<Status<List<LocationAuto>>> {
        return flow {
            emit(value = Status.Loading())
            try {
                val response = ktorWeatherApi.searchAutocomplete(keyword)
                val raw = response.firstOrNull()
                Log.d(TAG, "Raw = ${raw}")

                if (response.isEmpty()) {
                    emit(value = Status.Failure(message = "Accu Weather does not find any thing!"))
                    return@flow
                }

                val outcome: List<LocationAuto> = response.map { it.toLocationInfoModel() }
                Log.d(TAG, "------------------------------------------")
                Log.d(TAG, "The first item")
                if(outcome.isNotEmpty()){
                    val first = outcome.first()
                    val cityName = first.administrativeArea?.localizedName
                    val countryName = first.country?.localizedName

                    // todo: it still return NULL
                    Log.d(TAG, "searchAutocomplete - cityName: $cityName")
                    Log.d(TAG, "searchAutocomplete - countryName: $countryName")
                }



                emit(value = Status.Success(data = outcome))
            } catch (ex: Exception) {
                ex.printStackTrace()
                emit(value = Status.Failure(message = ex.message ?: "Unknown error"))
            }
        }
    }
}
