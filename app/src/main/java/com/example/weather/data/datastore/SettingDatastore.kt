package com.example.weather.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weather.WeatherApplication
import com.example.weather.configuration.Constant
import com.example.weather.configuration.Language
import com.example.weather.util.DateUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingDatastore
@Inject
constructor(application: WeatherApplication) {
    // At the top level of your kotlin file:
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = Constant.SETTING_DATASTORE)
    private val datastore = application.datastore


    // keys of datastore
    private val languageKey = stringPreferencesKey("languageKey")
    private val enableIntroKey = booleanPreferencesKey("enableIntroKey")
    private val enableLanguageIntroKey = booleanPreferencesKey("enableLanguageIntroKey")
    private val enableDarkModeKey = booleanPreferencesKey("enableDarkModeKey")
    private val lastTimeUpdateKey = longPreferencesKey("updatedLastTimeKey")

    private val isCelsiusEnabledKey = booleanPreferencesKey("isCelsiusKey")

    // Enable Intro
    var enableIntro: Boolean
        get() = runBlocking { datastore.data.first()[enableIntroKey] ?: true }
        set(value) = runBlocking { datastore.edit { pref -> pref[enableIntroKey] = value } }

    // Enable Language Intro
    var enableLanguageIntro: Boolean
        get() = runBlocking { datastore.data.first()[enableLanguageIntroKey] ?: true }
        set(value) = runBlocking { datastore.edit { pref -> pref[enableLanguageIntroKey] = value } }

    // Language
    var language: Language
        get() = Language.getByCode(
            runBlocking {
                datastore.data.first()[languageKey]
            })
        set(value) = runBlocking {
            datastore.edit { mutablePreferences -> mutablePreferences[languageKey] = value.code }
        }

    val languageFlow: Flow<Language> = datastore.data.map { mutablePreferences ->
        Language.getByCode(code = mutablePreferences[languageKey])
    }

    // Enable Intro
    var enableDarkMode: Boolean
        get() = runBlocking { datastore.data.first()[enableDarkModeKey] ?: true }
        set(value) = runBlocking { datastore.edit { pref -> pref[enableDarkModeKey] = value } }

    var lastTimeUpdate: Date
        get() = DateUtil.fromLongToDate(
            runBlocking {
                datastore.data.first()[lastTimeUpdateKey]
            }
        )
        set(value) = run { runBlocking { datastore.edit { pref -> pref[lastTimeUpdateKey] = value.time } } }

    // Enable Intro
    var isCelsiusEnabled: Boolean
        get() = runBlocking { datastore.data.first()[isCelsiusEnabledKey] ?: true }
        set(value) = runBlocking { datastore.edit { pref -> pref[isCelsiusEnabledKey] = value } }

    val isCelsiusEnabledFlow: Flow<Boolean> = datastore.data.map { mutablePreferences -> datastore.data.first()[isCelsiusEnabledKey] ?: true }
}