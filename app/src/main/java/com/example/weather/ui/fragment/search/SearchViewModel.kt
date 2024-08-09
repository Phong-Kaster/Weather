package com.example.weather.ui.fragment.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.status.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val weatherRepository: WeatherRepository
) : CoreViewModel() {

    private var _showLoading = MutableStateFlow<Boolean>(false)
    val showLoading = _showLoading.asStateFlow()

    private var _locations = MutableStateFlow<ImmutableList<LocationAuto>?>(persistentListOf())
    val locations = _locations.asStateFlow()

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
     * get current condition
     */
    fun getCurrentCondition(locationKey: String, fetchFromCache: Boolean) {
        //Log.d(TAG, "getCurrentCondition ----------------->")
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getCurrentCondition(
                fetchFromRemote = fetchFromCache,
                locationKey = locationKey
            ).collect { status->
                Log.d(TAG, "getCurrentCondition - status = ${status.javaClass.simpleName}")
                when(status) {
                    is Status.Success -> {
                        _showLoading.value = false
                        //_currentCondition.value = status.data ?: CurrentCondition()
                        //Log.d(TAG, "getCurrentCondition - currentCondition: ${status.data?.temperature}")
                    }
                    is Status.Failure -> {
                        _showLoading.value = false
                        //_errorMessage.value = status.message ?: "Error"
                    }
                    is Status.Loading -> _showLoading.value = true
                }
            }
        }
    }
}