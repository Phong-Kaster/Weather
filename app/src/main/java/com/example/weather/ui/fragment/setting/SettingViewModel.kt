package com.example.weather.ui.fragment.setting

import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
@Inject
constructor(
    private val settingRepository: SettingRepository,
) : CoreViewModel() {

    private var _lastTimeUpdate = MutableStateFlow(Date())
    val lastTimeUpdate = _lastTimeUpdate.asStateFlow()

    init {
        getLastTimeUpdate()
    }

    fun getLastTimeUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            _lastTimeUpdate.value = settingRepository.getLastTimeUpdate()
        }
    }

    fun setLastTimeUpdate(date: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setLastTimeUpdate(date)
        }
    }
}