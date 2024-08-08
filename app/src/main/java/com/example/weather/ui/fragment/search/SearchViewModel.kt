package com.example.weather.ui.fragment.search

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val weatherRepository: WeatherRepository
) : CoreViewModel() {
    fun searchAutocomplete(keyword: String) {
        Log.d(ContentValues.TAG, "searchAutocomplete - keyword: $keyword")
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.searchAutocomplete(keyword = keyword)
        }
    }
}