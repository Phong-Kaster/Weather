package com.example.weather.ui.fragment.home

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.SettingRepository
import com.example.weather.data.repository.WeatherRepository
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

    private var _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ coroutineContext: CoroutineContext, throwable: Throwable ->
        Log.d(TAG, "----------> coroutineExceptionHandler")
        when(throwable){
            is UnauthorizedException -> Log.d(TAG, "UnauthorizedException")
            is ServerNotFoundException -> Log.d(TAG, "ServerNotFoundException")
            is ForbiddenException -> Log.d(TAG, "ForbiddenException")
            is NoConnectivityException -> Log.d(TAG, "NoConnectivityException")
            else -> throwable.message
        }
        throwable.printStackTrace()
    }

    fun setDarkMode(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setDarkMode(boolean)
        }
    }

    fun getCurrentCondition(locationKey: String) {
        Log.d(TAG, "getCurrentCondition - locationKey: $locationKey")
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            weatherRepository.getCurrentCondition(locationKey)
        }
    }
}