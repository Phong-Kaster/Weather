package com.example.weather.ui.activity

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weather.WeatherApplication
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.SettingRepository
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.data.workmanager.WeatherWorker
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.model.LocationInfo
import com.example.weather.domain.model.Weather
import com.example.weather.domain.status.Status
import com.example.weather.util.DateUtil
import com.panda.wifipassword.data.api.exception.ForbiddenException
import com.panda.wifipassword.data.api.exception.NoConnectivityException
import com.panda.wifipassword.data.api.exception.ServerNotFoundException
import com.panda.wifipassword.data.api.exception.UnauthorizedException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val context: WeatherApplication,
    private val settingRepository: SettingRepository,
    private val weatherRepository: WeatherRepository,
) : CoreViewModel() {

     val chosenLocationKey = "1024259"

    private var _locations = MutableStateFlow<ImmutableList<LocationAuto>?>(persistentListOf())
    val locations = _locations.asStateFlow()

//    private var _chosenLocationInfo = MutableStateFlow<LocationInfo>(LocationInfo())
//    var chosenLocationInfo = _chosenLocationInfo.asStateFlow()

    private var _showLoading = MutableStateFlow(false)
    val showLoading = _showLoading.asStateFlow()

//    private var _currentCondition = MutableStateFlow(CurrentCondition())
//    val currentCondition = _currentCondition.asStateFlow()

    private var _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private var _weather = MutableStateFlow(Weather())
    val weather = _weather.asStateFlow()

    private var _weathers = MutableStateFlow<ImmutableList<Weather>>(persistentListOf())
    val weathers = _weathers.asStateFlow()

    private var _index = MutableStateFlow<Int>(0)
    val index = _index.asStateFlow()

    private val workerManager = WorkManager.getInstance(context)

    init {
        getCurrentCondition(
            locationKey = _weather.value.locationInfo.locationKey.ifEmpty { chosenLocationKey },
            fetchFromCache = true
        )
        searchLocationByKey(
            locationKey = _weather.value.locationInfo.locationKey.ifEmpty { chosenLocationKey }
        )

        findWeathers()

        getHourlyForecast(
            locationKey = chosenLocationKey
        )
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ coroutineContext: CoroutineContext, throwable: Throwable ->
        Log.d(TAG, "----------> coroutineExceptionHandler")
        _showLoading.value = false
        when(throwable){
            is UnauthorizedException -> {
                Log.d(TAG, "UnauthorizedException")
                _errorMessage.value = "UnauthorizedException"
            }
            is ServerNotFoundException -> {
                Log.d(TAG, "ServerNotFoundException")
                _errorMessage.value = "ServerNotFoundException"
            }
            is ForbiddenException ->  {
                Log.d(TAG, "ForbiddenException")
                _errorMessage.value = "ForbiddenException"
            }
            is NoConnectivityException -> {
                Log.d(TAG, "NoConnectivityException")
                _errorMessage.value = "NoConnectivityException"
            }
            else ->{
                throwable.message
                _errorMessage.value = "Check exception for more detail"
            }
        }
        throwable.printStackTrace()
    }

    /***************************************
     * search auto complete
     */
    fun searchAutocomplete(keyword: String) {
        if (keyword.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository
                .searchAutocomplete(keyword = keyword)
                .collect { status ->
                    when (status) {
                        is Status.Success -> {
                            _showLoading.value = false
                            _locations.value = status.data?.toImmutableList()
                            Log.d(TAG, "searchAutocomplete - location: ${locations.value?.size}")
                        }
                        is Status.Failure -> {
                            _locations.value = persistentListOf()
                            _showLoading.value = false
                        }
                        is Status.Loading -> {
                            _showLoading.value = true
                        }
                    }
                }
        }
    }


    /***************************************
     * save Location Info
     */
    fun saveLocationInfo(
        locationAuto: LocationAuto,
        onFailure: (String) -> Unit = {},
        onSuccess: () -> Unit = {},
    ) {
        if (locationAuto.key.isNullOrEmpty()) {
            onFailure("Location key is null or empty")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.saveLocationInfo(locationAuto).collect { status ->
                Log.d(TAG, "saveLocationInfo - status: ${status.javaClass.simpleName} ")
                when (status) {
                    is Status.Success -> {
                        if (status.data == null) {
                            onFailure("Location Info is null")
                        } else {
                            weather.value.locationInfo = status.data
                            onSuccess()
                        }
                    }
                    is Status.Failure -> { onFailure("Accu Weather fail !") }
                    is Status.Loading -> {}
                }
            }
        }
    }


    /***************************************
     * get current condition
     */
    private fun getCurrentCondition(
        locationKey: String,
        fetchFromCache: Boolean = false,
    ) {
        if (locationKey.isEmpty()) return


        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getCurrentCondition(
                fetchFromRemote = fetchFromCache,
                locationKey = locationKey
            ).collect { status->
                //Log.d(TAG, "getCurrentCondition - status = ${status.javaClass.simpleName}")
                when (status) {
                    is Status.Success -> {
                        _showLoading.value = false
                        //weather.value.currentCondition = status.data ?: CurrentCondition()
                        //Log.d(TAG, "getCurrentCondition - currentCondition: ${status.data?.temperature}")
                    }

                    is Status.Failure -> {
                        _showLoading.value = false
                        _errorMessage.value = status.message ?: "Error"
                    }

                    is Status.Loading -> _showLoading.value = status.enabled
                }
            }
        }
    }


    fun setDarkMode(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setDarkMode(boolean)
        }
    }

    fun searchLocationByKey(locationKey: String) {
        if (locationKey.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            _weather.value.locationInfo = weatherRepository.searchInfoByLocationKey(locationKey = locationKey) ?: LocationInfo()
        }
    }

    fun findWeathers() {
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val listOfLocationInfo: List<LocationInfo> = weatherRepository.findAllLocationInfo()
            val listOfCurrentCondition = listOfLocationInfo.mapIndexed { index: Int, locationInfo: LocationInfo  ->
                async {
                    findActualCurrentCondition(locationKey = locationInfo.locationKey)
                }
            }.awaitAll()


//            listOfCurrentCondition.forEachIndexed { index: Int, currentCondition: CurrentCondition ->
//                val weather = Weather(
//                    currentCondition = currentCondition,
//                    locationInfo = listOfLocationInfo[index]
//                )
//                listOfWeather.add(weather)
//            }

            val listOfWeather: List<Weather> = listOfLocationInfo.zip(listOfCurrentCondition) { info, condition ->
                    Weather(locationInfo = info, currentCondition = condition)
                }

            _weathers.value = listOfWeather.toImmutableList()
            _showLoading.value = false
        }
    }

    private suspend fun findActualCurrentCondition(locationKey: String) : CurrentCondition {
        if (locationKey.isEmpty()) return CurrentCondition()

        return withContext(Dispatchers.IO) {
            weatherRepository.getCurrentCondition2(
                fetchFromRemote = true,
                locationKey = locationKey
            )
        }
    }

    /*******************************************************
     * # WORKER MANAGER
     *
     * # [Work constraints](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started/define-work#work-constraints)
     * */
    fun executeWeatherWorker() {
        Log.d(TAG, "doWork - executeWeatherWorker")

        val delayDuration = DateUtil.calculateInitialDelay(targetHour = 3, targetMinute = 0)

        // this worker requires WI-FI connection
        val weatherConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        // Add initial delay for 3 AM. Do not use periodic worker because they
        // can not run at specific time

        val weatherWorker = OneTimeWorkRequestBuilder<WeatherWorker>()
            .addTag("weather")
            .setConstraints(weatherConstraint)
            .setInitialDelay(
                delayDuration,
                TimeUnit.MILLISECONDS
            )
            .build()

        workerManager.enqueue(weatherWorker)
    }

    private fun getHourlyForecast(locationKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.get1HourOfHourlyForecast(locationKey = locationKey)
        }
    }
}