package com.example.weather.data.repository

import android.util.Log
import com.example.jetpack.network.dto.LocationGeoDto
import com.example.weather.data.datasource.database.WeatherDatabase
import com.example.weather.domain.mapper.CurrentConditionMapper.toModel
import com.example.weather.data.datasource.remote.WeatherApi
import com.example.weather.domain.mapper.LocationAutoMapper.toLocationInfoModel
import com.example.weather.domain.mapper.LocationInfoMapper.toEntity
import com.example.weather.domain.mapper.LocationInfoMapper.toModel
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.model.LocationInfo
import com.example.weather.domain.status.Status
import com.example.weather.util.ApiUtil
import com.example.weather.util.DateUtil
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
    private val currentConditionDao = weatherDatabase.currentConditionDao()
    private val locationInfoDao = weatherDatabase.locationInfoDao()

    suspend fun searchGeoposition(lntLng: String) : LocationGeoDto {
        return ApiUtil.fetchDataBody {  weatherApi.searchGeoposition(lnglat = lntLng) }
    }

    /**
     * search autocomplete
     */
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

                val outcome: List<LocationAuto> = response.map { it.toLocationInfoModel() }

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

    /**
     * get current condition
     */
    suspend fun getCurrentCondition(
        fetchFromRemote: Boolean,
        locationKey: String
    ): Flow<Status<CurrentCondition>> {
        Log.d(TAG, "getCurrentCondition -----------------------")
        return flow {

            /** 1. Check database*/
            emit(value = Status.Loading(enabled = true))
            val localCurrentCondition = currentConditionDao.findByLocationKey(locationKey = locationKey)
            val isDatabaseNotEmpty = localCurrentCondition.isNotEmpty()
            Log.d(TAG, "getCurrentCondition - localCurrentCondition size = ${localCurrentCondition.size}")
            Log.d(TAG, "getCurrentCondition - isDatabaseNotEmpty = $isDatabaseNotEmpty")
            if (isDatabaseNotEmpty) {
                emit(value = Status.Success(data = localCurrentCondition.map { it.toModel() }.first()))
            }


            /** 2. Check should we need to fetch from Accu Weather or not. If local data is outdated then call accu weather as usual*/
            val fetchFromLocalOnly = isDatabaseNotEmpty && !fetchFromRemote
            val isLocalNotOutdated = if (isDatabaseNotEmpty) {
                    val local = localCurrentCondition.map { it.toModel() }.first().toModel()
                    DateUtil.isLocalNotOutdated(fromDate = local.date, toDate = Date())
                } else false
            if (fetchFromLocalOnly && isLocalNotOutdated) {
                Log.d(TAG, "getCurrentCondition - case 1 - just fetch from database")
                emit(value = Status.Loading(enabled = false))
                return@flow
            }


            /** 3. Let us fetch from Accu Weather*/
            Log.d(TAG, "getCurrentCondition - case 2 - fetch from Accu Weather")
            try {
                val response = ApiUtil.fetchDataBody { weatherApi.getCurrentCondition(locationKey = locationKey) }
                if (response.isEmpty()) {
                    emit(value = Status.Failure(message = "Accu Weather returns nothing"))
                    return@flow
                }

                val outcome : CurrentCondition = response.first().toModel(locationKey = locationKey)

                currentConditionDao.deleteByLocationKey(locationKey = locationKey)
                currentConditionDao.insert(entity = outcome.toModel())
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

    /**
     * save location info
     */
    suspend fun saveLocationInfo(locationAuto: LocationAuto): Flow<Status<LocationInfo>> {
        return flow {
            emit(value = Status.Loading(enabled = true))
            val localLocationInfo = locationInfoDao.findByLocationKey(locationKey = locationAuto.key ?: "")
            val isDatabaseNotEmpty = localLocationInfo.isNotEmpty()

            Log.d(TAG, "saveLocationInfo - isDatabaseNotEmpty = $isDatabaseNotEmpty")
            
            if (isDatabaseNotEmpty) {
                emit(value = Status.Success(data = localLocationInfo.map { it.toModel() }.first()))
                return@flow
            }

            try {
                val model: LocationInfo = locationAuto.toLocationInfoModel()
                Log.d(TAG, "saveLocationInfo - model: $model")
                locationInfoDao.insert(model.toEntity())
                emit(value = Status.Success(data = model))
                Log.d(TAG, "saveLocationInfo - run get current condition")
            } catch (e: IOException) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "IOException"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(value = Status.Failure(message = "Exception"))
            }
        }
    }
}