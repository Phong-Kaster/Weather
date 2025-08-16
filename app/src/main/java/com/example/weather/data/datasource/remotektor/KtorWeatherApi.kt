package com.example.weather.data.datasource.remotektor

import com.example.weather.data.datasource.remote.response.GeopositionSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Ktor Weather Api is a class that Responsibility: only build/make the HTTP request, and return DTOs raw data.
 * + Layer that ONLY calls Ktor endpoints, returns DTOs
 * + Purely network, returns DTO, throws exceptions
 */
class KtorWeatherApi
@Inject
constructor(
    private val ktorHttpClient: HttpClient
) {
    /*********************************
     * # [Autocomplete search](https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/autocomplete)
     */
    suspend fun searchAutocomplete(keyword: String): List<GeopositionSearchResponse> {
        return ktorHttpClient.get("https://dataservice.accuweather.com/locations/v1/cities/autocomplete") {
            url { parameters.append("q", keyword) }
        }.body()
    }
}
