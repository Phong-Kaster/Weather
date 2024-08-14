package com.example.weather.ui.fragment.splash

import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val weatherRepository: WeatherRepository,
) : CoreViewModel() {

    private val _isDatabaseEmpty = MutableStateFlow(false)
    val isDatabaseEmpty = _isDatabaseEmpty.asStateFlow()

    fun isDatabaseEmpty() {
        viewModelScope.launch(Dispatchers.IO){
           _isDatabaseEmpty.value = weatherRepository.findAllLocationInfo().isEmpty()
        }
    }
}