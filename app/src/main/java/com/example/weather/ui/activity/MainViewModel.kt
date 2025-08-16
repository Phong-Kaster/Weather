package com.example.weather.ui.activity

import android.util.Log
import androidx.compose.runtime.key
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weather.WeatherApplication
import com.example.weather.core.CoreViewModel
import com.example.weather.data.datasource.remotektor.KtorHttpRequest
import com.example.weather.data.repository.SettingRepository
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.data.workmanager.WeatherWorker
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.HourlyForecast
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
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * [Kotlin Coroutines: how to merge asynchronous results in Kotlin?](https://medium.com/@jtlalka/kotlin-coroutines-how-to-merge-asynchronous-results-in-kotlin-674c3079edba)
 */
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val context: WeatherApplication,
    private val settingRepository: SettingRepository,
    private val weatherRepository: WeatherRepository,
    private val ktor: KtorHttpRequest,
) : CoreViewModel() {

    val chosenLocationKey = "353511"

    private var _locations = MutableStateFlow<ImmutableList<LocationAuto>?>(persistentListOf())
    val locations = _locations.asStateFlow()

    private var _showLoading = MutableStateFlow(false)
    val showLoading = _showLoading.asStateFlow()

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
        findWeathers()
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

    fun ktorSearchAutocomplete(keyword: String) {
        Log.d(TAG, "ktorSearchAutocomplete run with keyword $keyword")
        if (keyword.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO){
            ktor.searchAutocomplete(keyword = keyword)
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
                        _showLoading.value = false
                        if (status.data == null) {
                            onFailure("Location Info is null")
                        } else {
                            weather.value.locationInfo = status.data
                            onSuccess()
                        }
                    }

                    is Status.Failure -> {
                        _showLoading.value = false
                        onFailure("Accu Weather fail !")
                    }

                    is Status.Loading -> {
                        _showLoading.value = true
                    }
                }
            }
        }
    }


    /***************************************
     * search by locationKey
     */
    private  fun searchByLocationKey() {
        Log.d(TAG, "searchByLocationKey: ")
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.searchByLocationKey(locationKey = chosenLocationKey )
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
            ).collect { status ->
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


    fun findWeathers() {
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val listOfLocationInfo: List<LocationInfo> = weatherRepository.findAllLocationInfo()
            val listOfCurrentCondition =
                listOfLocationInfo.mapIndexed { index: Int, locationInfo: LocationInfo ->
                    async { findActualCurrentCondition(locationKey = locationInfo.locationKey) }
                }.awaitAll()

            val listOfHourlyForecast =
                listOfLocationInfo.mapIndexed { index: Int, locationInfo: LocationInfo ->
                    async {
                        get24HoursOfHourlyForecast(
                            locationKey = locationInfo.locationKey,
                            fetchFromRemote = false
                        )
                    }
                }.awaitAll()

            /*

                        val listOfWeather: List<Weather> =
                            listOfLocationInfo.zip(listOfCurrentCondition) { info, condition ->
                                Weather(locationInfo = info, currentCondition = condition)
                            } //  For instance, result: [("Red", 0.1f), ("Green", 0.5f), ("Blue", 0.9f)]
            */


            // Zipping three lists together
            val listOfWeather: List<Weather> =
                listOfLocationInfo
                    .zip(listOfCurrentCondition)
                    .zip(listOfHourlyForecast) { (info, condition), listOfHourly ->
                        Weather(
                            locationInfo = info,
                            currentCondition = condition,
                            listOfHourlyForecast = listOfHourly
                        )
                    }

            _weathers.value = listOfWeather.toImmutableList()
            _showLoading.value = false
        }
    }

    private suspend fun findActualCurrentCondition(
        locationKey: String,
        fetchFromRemote: Boolean = false,
    ): CurrentCondition {
        if (locationKey.isEmpty()) return CurrentCondition()

        return withContext(Dispatchers.IO) {
            weatherRepository.getCurrentCondition2(
                fetchFromRemote = fetchFromRemote,
                locationKey = locationKey
            )
        }
    }


    private suspend fun get24HoursOfHourlyForecast(
        locationKey: String,
        fetchFromRemote: Boolean = false
    ): List<HourlyForecast> {
        if (locationKey.isEmpty()) return listOf()

        return withContext(Dispatchers.IO) {
            weatherRepository.get24HoursOfHourlyForecast(
                locationKey = locationKey,
                fetchFromRemote = fetchFromRemote
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

    fun searchLocationByKey(locationKey: String) {
        if (locationKey.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            _weather.value.locationInfo =
                weatherRepository.searchInfoByLocationKey(locationKey = locationKey)
                    ?: LocationInfo()
        }
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext: CoroutineContext, throwable: Throwable ->
            Log.d(TAG, "----------> coroutineExceptionHandler")
            _showLoading.value = false
            when (throwable) {
                is UnauthorizedException -> {
                    Log.d(TAG, "UnauthorizedException")
                    _errorMessage.value = "UnauthorizedException"
                }

                is ServerNotFoundException -> {
                    Log.d(TAG, "ServerNotFoundException")
                    _errorMessage.value = "ServerNotFoundException"
                }

                is ForbiddenException -> {
                    Log.d(TAG, "ForbiddenException")
                    _errorMessage.value = "ForbiddenException"
                }

                is NoConnectivityException -> {
                    Log.d(TAG, "NoConnectivityException")
                    _errorMessage.value = "NoConnectivityException"
                }

                else -> {
                    throwable.message
                    _errorMessage.value = "Check exception for more detail"
                }
            }
            throwable.printStackTrace()
        }

}