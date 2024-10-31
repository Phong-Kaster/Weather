package com.example.weather.ui.fragment.setting

import androidx.lifecycle.viewModelScope
import com.example.weather.core.CoreViewModel
import com.example.weather.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * [Kotlin Coroutines: how to merge asynchronous results in Kotlin?](https://medium.com/@jtlalka/kotlin-coroutines-how-to-merge-asynchronous-results-in-kotlin-674c3079edba)
 */
@HiltViewModel
class SettingViewModel
@Inject
constructor(
    private val settingRepository: SettingRepository,
) : CoreViewModel() {

    private var _isCelsiusEnabled = MutableStateFlow<Boolean>(false)
    val isCelsiusEnabled = _isCelsiusEnabled.asStateFlow()

    init {
        collectCelsiusEnabled()
    }


    private fun collectCelsiusEnabled() {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.isCelsiusEnabledFlow().collectLatest {
                _isCelsiusEnabled.value = it
            }
        }
    }

    fun setEnabledCelsius(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setEnableCelsius(boolean = boolean)
        }
    }
}