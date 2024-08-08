package com.example.weather.ui.fragment.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.SettingRepository
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.status.Status
import com.panda.wifipassword.data.api.exception.ForbiddenException
import com.panda.wifipassword.data.api.exception.NoConnectivityException
import com.panda.wifipassword.data.api.exception.ServerNotFoundException
import com.panda.wifipassword.data.api.exception.UnauthorizedException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val settingRepository: SettingRepository,
    private val weatherRepository: WeatherRepository,
) : CoreViewModel() {

    private var _showLoading = MutableStateFlow(false)
    val showLoading = _showLoading.asStateFlow()

    private var _currentCondition = MutableStateFlow(CurrentCondition())
    val currentCondition = _currentCondition.asStateFlow()

    private var _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

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

    fun setDarkMode(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setDarkMode(boolean)
        }
    }

    fun getCurrentCondition(locationKey: String, fetchFromCache: Boolean) {
        //Log.d(TAG, "getCurrentCondition ----------------->")
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getCurrentCondition(
                fetchFromRemote = fetchFromCache,
                locationKey = locationKey
            ).collect { status->
                Log.d(TAG, "getCurrentCondition - status: ${status.javaClass.simpleName}")
                when(status) {
                    is Status.Success -> {
                        _showLoading.value = false
                        _currentCondition.value = status.data ?: CurrentCondition()
                        //Log.d(TAG, "getCurrentCondition - currentCondition: ${status.data?.temperature}")
                    }
                    is Status.Failure -> {
                        _showLoading.value = false
                        _errorMessage.value = status.message ?: "Error"
                    }
                    is Status.Loading -> {
                        Log.d(TAG, "getCurrentCondition - showLoading: ${status.showLoading}")
                        _showLoading.value = status.showLoading
                    }
                }
            }
        }
    }

    fun searchAutocomplete(keyword: String) {
        Log.d(ContentValues.TAG, "searchAutocomplete - keyword: $keyword")
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.searchAutocomplete(keyword = keyword)
        }
    }
}