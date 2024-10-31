package com.example.weather.domain.mapper

import com.example.weather.data.datasource.database.entity.LocationInfoEntity
import com.example.weather.domain.model.AdministrativeArea
import com.example.weather.domain.model.Country
import com.example.weather.domain.model.LocationInfo

object LocationInfoMapper {
    fun LocationInfoEntity.toModel() : LocationInfo {
        return LocationInfo(
            id = this.id,
            locationKey = this.locationKey,
            localizedName = this.localizedName,
            country = Country(
                id = this.country?.id,
                localizedName = this.country?.localizedName,
                englishName = this.country?.englishName
            ),
            administrativeArea = AdministrativeArea(
                ID = this.administrativeArea?.ID,
                localizedName = this.administrativeArea?.localizedName,
                englishName = this.administrativeArea?.englishName,
                level = this.administrativeArea?.level,
                localizedType =  this.administrativeArea?.localizedType,
                englishType = this.administrativeArea?.englishType,
                countryId = this.administrativeArea?.countryId
            ),
            timezone = this.timezone ?: "Europe/Berlin",
        )
    }

    fun LocationInfo.toEntity(): LocationInfoEntity {
        return LocationInfoEntity(
            id = this.id,
            locationKey = this.locationKey,
            localizedName = this.localizedName,
            country = Country(
                id = this.country?.id,
                localizedName = this.country?.localizedName,
                englishName = this.country?.englishName
            ),
            administrativeArea = AdministrativeArea(
                ID = this.administrativeArea?.ID,
                localizedName = this.administrativeArea?.localizedName,
                englishName = this.administrativeArea?.englishName,
                level = this.administrativeArea?.level,
                localizedType =  this.administrativeArea?.localizedType,
                englishType = this.administrativeArea?.englishType,
                countryId = this.administrativeArea?.countryId
            ),
            timezone = this.timezone
        )
    }
}