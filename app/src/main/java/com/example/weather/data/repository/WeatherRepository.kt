package com.example.weather.data.repository

import android.util.Log
import com.example.jetpack.network.dto.LocationGeoDto
import com.example.weather.data.datasource.database.WeatherDatabase
import com.example.weather.domain.mapper.CurrentConditionMapper.toModel
import com.example.weather.data.datasource.remote.WeatherApi
import com.example.weather.domain.mapper.LocationAutoMapper.toModel
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.status.Status
import com.example.weather.util.ApiUtil
import com.panda.wifipassword.data.api.exception.NoConnectivityException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository
@Inject
constructor(
    private val weatherDatabase: WeatherDatabase,
    private val weatherApi: WeatherApi
){
    private val TAG = "WeatherRepository"
    private val weatherCurrentConditionDao = weatherDatabase.currentConditionDao()

    suspend fun searchGeoposition(lntLng: String) : LocationGeoDto {
        return ApiUtil.fetchDataBody {  weatherApi.searchGeoposition(lnglat = lntLng) }
    }

    suspend fun searchAutocomplete(keyword: String): Flow<Status<List<LocationAuto>>> {
        Log.d(TAG, "searchAutocomplete -----------------------")
        return flow {
            emit(value = Status.Loading())
            try {
                val response = ApiUtil.fetchDataBody { weatherApi.searchAutocomplete(keyword = keyword) }
                Log.d(TAG, "searchAutocomplete - response size: ${response.size}")
                Log.d(TAG, "searchAutocomplete - response: $response")

                if (response.isEmpty()) {
                    emit(value = Status.Failure(message = "Accu Weather does not find any thing!"))
                    return@flow
                }

                val outcome: List<LocationAuto> = response.map { it.toModel() }

                emit(value = Status.Success(data = outcome))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "HttpException"))
            } catch (e: NoConnectivityException) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "No Internet connectivity"))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "IOException"))
            }

        }
    }

    suspend fun getCurrentCondition(
        fetchFromRemote: Boolean,
        locationKey: String
    ): Flow<Status<CurrentCondition>> {
        Log.d(TAG, "getCurrentCondition -----------------------")
        return flow {
            /** 1. Check database*/
            emit(value = Status.Loading(enabled = true))
            val localCurrentCondition = weatherCurrentConditionDao.findByLocationKey(locationKey = locationKey)
            val isDatabaseNotEmpty = localCurrentCondition.isNotEmpty()

            Log.d(TAG, "getCurrentCondition - localCurrentCondition size = ${localCurrentCondition.size}")
            Log.d(TAG, "getCurrentCondition - isDatabaseNotEmpty = $isDatabaseNotEmpty")
            if (isDatabaseNotEmpty) {
                emit(value = Status.Success(data = localCurrentCondition.map { it.toModel() }.first()))
            }


            /** 2. Check should we need to fetch from Accu Weather or not*/
            val justFetchFromLocal = isDatabaseNotEmpty && !fetchFromRemote
            Log.d(TAG, "getCurrentCondition - fetchFromRemote = $fetchFromRemote")
            Log.d(TAG, "getCurrentCondition - justFetchFromLocal = $justFetchFromLocal")
            if (justFetchFromLocal) {
                Log.d(TAG, "getCurrentCondition - case 1 - just fetch from database")
                emit(value = Status.Loading(enabled = false))
                return@flow
            }

            /** 3. Check current condition is outdated or not*/
            val localDate: Date = localCurrentCondition.map { it.toModel() }.first().toModel().date
            val date: Date = Date()



            /** 3. Let us fetch from Accu Weather*/
            Log.d(TAG, "getCurrentCondition - case 2 - fetch from Accu Weather")
            try {
                val response = ApiUtil.fetchDataBody { weatherApi.getCurrentCondition(locationKey = locationKey) }
                if (response.isEmpty()) {
                    emit(value = Status.Failure(message = "Accu Weather returns nothing"))
                    return@flow
                }

                val outcome : CurrentCondition = response.first().toModel(locationKey = locationKey)

                weatherCurrentConditionDao.deleteByLocationKey(locationKey = locationKey)
                weatherCurrentConditionDao.insert(entity = outcome.toModel())
                emit(value = Status.Success(data = outcome))

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "HttpException"))
            } catch (e: NoConnectivityException) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "No Internet connectivity"))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "IOException"))
            }
        }
    }
}