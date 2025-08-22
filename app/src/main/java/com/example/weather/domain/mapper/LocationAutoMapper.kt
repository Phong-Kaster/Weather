package com.example.weather.domain.mapper

import com.example.weather.data.datasource.remote.response.GeopositionSearchResponse
import com.example.weather.data.datasource.remotektor.dto.KtorGeopositionSearchResponse
import com.example.weather.domain.model.AdministrativeArea
import com.example.weather.domain.model.Country
import com.example.weather.domain.model.LocationAuto
import com.example.weather.domain.model.LocationInfo

object LocationAutoMapper {
    fun GeopositionSearchResponse.toLocationInfoModel() : LocationAuto {
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

    fun KtorGeopositionSearchResponse.toLocationInfoModel() : LocationAuto {
        return LocationAuto(
            version = this.Version,
            key = this.Key,
            type = this.Type,
            rank = this.Rank,
            localizedName = this.LocalizedName,
            country = Country(
                id = this.Country?.ID,
                localizedName = this.Country?.localizedName,
                englishName = this.Country?.englishName
            ),
            administrativeArea = AdministrativeArea(
                ID = this.AdministrativeArea?.ID,
                localizedName = this.AdministrativeArea?.LocalizedName,
                englishName = this.AdministrativeArea?.EnglishName,
                level = this.AdministrativeArea?.Level,
                localizedType =  this.AdministrativeArea?.LocalizedType,
                englishType = this.AdministrativeArea?.EnglishType,
                countryId = this.AdministrativeArea?.CountryID
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
            ),
        )
    }
}