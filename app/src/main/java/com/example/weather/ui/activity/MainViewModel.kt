package com.example.weather.ui.activity

import android.location.Location
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.SettingRepository
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.model.LocationInfo
import com.example.weather.domain.model.Weather
import com.example.weather.domain.status.Status
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel
@Inject
constructor(
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

//    private var _weather = MutableStateFlow(Weather())
//    val weather = _weather.asStateFlow()

    private var _weathers = MutableStateFlow<List<Weather>>(mutableListOf())
    val weathers = _weathers.asStateFlow()

    private var _index = MutableStateFlow<Int>(0)
    val index = _index.asStateFlow()

    init {
        findAllLocationInfo()
        getCurrentCondition(
            locationKey = _weathers.value.find { it.locationInfo.locationKey == chosenLocationKey }?.locationInfo?.locationKey ?: "",
            fetchFromCache = true
        )
        searchLocationByKey(
            locationKey = _weathers.value.find { it.locationInfo.locationKey == chosenLocationKey }?.locationInfo?.locationKey ?: ""
        )
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ coroutineContext: CoroutineContext, throwable: Throwable ->
        Log.d(TAG, "----------> coroutineExceptionHandler")
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
//                            weather.value.locationInfo = status.data
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
    fun getCurrentCondition(
        locationKey: String,
        fetchFromCache: Boolean,
    ) {
//        Log.d(TAG, "getCurrentCondition ----------------->")
//        Log.d(TAG, "getCurrentCondition - locationKey = $locationKey")

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
            //weather.value.locationInfo = weatherRepository.searchInfoByLocationKey(locationKey = locationKey) ?: LocationInfo()
        }
    }

    private fun findAllLocationInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfLocationInfo: List<LocationInfo> = weatherRepository.findAllLocationInfo()
            val listOfWeather = mutableListOf<Weather>()


//            listOfLocationInfo.forEachIndexed { index: Int, locationInfo: LocationInfo  ->
//                val currentCondition = withContext(Dispatchers.IO) {
//                    weatherRepository.getCurrentCondition(fetchFromRemote = false, locationKey = locationInfo.locationKey)
//                }
//                val weather = Weather(
//                    locationInfo = locationInfo,
//                    currentCondition = currentCondition
//                )
//                listOfWeather.add(weather)
//            }
            _weathers.value = listOfWeather

            Log.d(TAG, "findAllLocationInfo - listOfLocationInfo size: ${listOfLocationInfo.size} ")
            Log.d(TAG, "findAllLocationInfo - weathers.value size: ${_weathers.value.size} ")
        }
    }
}