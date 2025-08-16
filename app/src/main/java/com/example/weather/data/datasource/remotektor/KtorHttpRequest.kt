package com.example.weather.data.datasource.remotektor


import android.util.Log
import com.example.weather.data.datasource.remote.response.GeopositionSearchResponse
import com.example.weather.domain.model.LocationAuto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorHttpRequest
@Inject
constructor(
    private val ktorHttpClient: HttpClient
) {
    private val TAG = KtorHttpRequest::class.java.simpleName

    /**
     * Search for locations based on a keyword.
     * This method uses the AccuWeather API to perform an autocomplete search.
     *
     * @param keyword The keyword to search for.
     */
    suspend fun searchAutocomplete(keyword: String): List<LocationAuto> {
        Log.d(TAG, "Ktor searchAutocomplete called with keyword: $keyword")
        return try {
            val response: List<GeopositionSearchResponse> =
                ktorHttpClient.get("https://dataservice.accuweather.com/locations/v1/cities/autocomplete") {
                    url {
                        parameters.append("q", keyword)
                    }
                }.body()

            Log.d(TAG, "searchAutocomplete response size: ${response.size}")

            listOf()
        } catch (e: Exception) {
            Log.e(TAG, "searchAutocomplete error", e)
            emptyList()
        }
    }

}