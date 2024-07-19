package com.example.weather.injection

import com.example.weather.data.repository.SettingRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EntryPointRepository {
    fun settingRepository(): SettingRepository
}