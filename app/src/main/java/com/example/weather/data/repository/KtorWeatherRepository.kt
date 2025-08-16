package com.example.weather.data.repository

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

    /*********************************
     * # [Autocomplete search](https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/autocomplete)
     */
    suspend fun searchAutocomplete(keyword: String): Flow<Status<List<LocationAuto>>> {
        return flow {
            emit(value = Status.Loading())
            try {
                val response = ktorWeatherApi.searchAutocomplete(keyword)
                if (response.isEmpty()) {
                    emit(Status.Failure(message = "No results"))
                    return@flow
                }

                val outcome = response.map { it.toLocationInfoModel() }
                emit(value = Status.Success(outcome))
            } catch (ex: Exception) {
                ex.printStackTrace()
                emit(value = Status.Failure(message = ex.message ?: "Unknown error"))
            }
        }
    }
}
