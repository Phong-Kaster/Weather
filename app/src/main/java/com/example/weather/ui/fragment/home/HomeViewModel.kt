package com.example.weather.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val settingRepository: SettingRepository
) : ViewModel()
{
    fun setDarkMode(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setDarkMode(boolean)
        }
    }
}