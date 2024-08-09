package com.example.weather.domain.mapper

import com.example.weather.data.datasource.remote.response.LocationAutoResponse
import com.example.weather.domain.model.AdministrativeArea
import com.example.weather.domain.model.Country
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.model.LocationInfo

object LocationAutoMapper {
    fun LocationAutoResponse.toLocationInfoModel() : LocationAuto {
        return LocationAuto(
            version = this.Version,
            key = this.Key,
            type = this.Type,
            rank = this.Rank,
            localizedName = this.LocalizedName,
            country = Country(
                id = this.CountryDto?.ID,
                localizedName = this.CountryDto?.LocalizedName,
                englishName = this.CountryDto?.EnglishName
            ),
            administrativeArea = AdministrativeArea(
                ID = this.AdministrativeAreaDto?.ID,
                localizedName = this.AdministrativeAreaDto?.LocalizedName,
                englishName = this.AdministrativeAreaDto?.EnglishName,
                level = this.AdministrativeAreaDto?.Level,
                localizedType =  this.AdministrativeAreaDto?.LocalizedType,
                englishType = this.AdministrativeAreaDto?.EnglishType,
                countryId = this.AdministrativeAreaDto?.CountryID
            )
        )
    }

    fun LocationAuto.toLocationInfoModel() : LocationInfo {
        return LocationInfo(
            id = 0,
            locationKey = this.key ?: "",
            localizedName = this.localizedName ?: "",
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
            )
        )
    }
}