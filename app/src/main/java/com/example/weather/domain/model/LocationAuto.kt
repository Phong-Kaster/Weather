package com.example.weather.domain.model

import androidx.compose.runtime.Stable

@Stable
class LocationAuto(
    val version: Int? = 0,
    val key: String? = "",
    val type: String? = "",
    val rank: Int? = 0,
    val localizedName: String? = "",
    val country: Country? = Country(),
    val administrativeArea: AdministrativeArea? = AdministrativeArea()
){
    companion object {
        fun getFakeData(): List<LocationAuto> {

            val country = Country.getFakeCountry()
            val administrativeArea = AdministrativeArea.getFakeAdministrativeArea()

            return listOf(
                LocationAuto(version = 1, key = "1024259", type ="City", rank = 63, localizedName = "Glienicke/Nordbahn", country = country, administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "995487", type ="City", rank = 75, localizedName = "Schildow", country = country, administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "173535", type ="City", rank = 83, localizedName = "Lindenberg", country = country, administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "173570", type ="City", rank = 83, localizedName = "Mühlenbeck", country = country,administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "2599961", type ="City", rank = 85, localizedName = "Birkholz", country = country,administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "2599879", type ="City", rank = 85, localizedName = "Gartenstadt Großziethen", country = country, administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "167900", type ="City", rank = 85, localizedName = "Großziethen", country = country, administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "3557587", type ="City", rank = 85, localizedName = "Klarahöh", country = country, administrativeArea = administrativeArea),
                LocationAuto(version = 1, key = "1013761", type ="City", rank = 85, localizedName = "Kleinziethen", country = country, administrativeArea = administrativeArea),
            )
        }
    }
}