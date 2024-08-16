package com.example.weather.data.datasource.remote

import com.example.weather.data.datasource.remote.response.LocationAutoResponse
import com.example.jetpack.network.dto.LocationGeoDto
import com.example.weather.data.datasource.remote.response.CurrentConditionResponse
import com.example.weather.data.datasource.remote.response.HourlyForecastResponse
import com.example.weather.domain.model.HourlyForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    /*********************************
     * # [Geoposition Search](https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search)
     */
    @GET("/locations/v1/cities/geoposition/search")
    fun searchGeoposition( @Query("q") lnglat: String ): Response<LocationGeoDto>


    /*********************************
     * # [Autocomplete search](https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/autocomplete)
     */
    @GET("/locations/v1/cities/autocomplete")
    suspend fun searchAutocomplete( @Query("q") keyword: String ): Response<List<LocationAutoResponse>>


    /*********************************
     * # [Search by locationKey](https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/%7BlocationKey%7D)
     */
//    @GET("/locations/v1/{locationKey}")
//    fun searchLocationByKey( @Path("locationKey") locationKey: String ): Deferred<Response<LocationGeoDto>>

    /***********************************
     * # [Current Conditions](https://developer.accuweather.com/accuweather-current-conditions-api/apis/get/currentconditions/v1/%7BlocationKey%7D)
     */
    @GET("/currentconditions/v1/{locationKey}")
    suspend fun getCurrentCondition(@Path("locationKey") locationKey: String): Response<List<CurrentConditionResponse>>

    /***********************************
     * # [24 Hours of Hourly Forecasts](https://developer.accuweather.com/accuweather-forecast-api/apis/get/forecasts/v1/hourly/24hour/%7BlocationKey%7D)
     */
    @GET("forecasts/v1/hourly/24hour/{locationKey}")
    suspend fun get24HoursOfHourlyForecast(@Path("locationKey") locationKey: String): Response<List<HourlyForecastResponse>>

    /***********************************
     * # [1 Hour of Hourly Forecasts](https://developer.accuweather.com/accuweather-forecast-api/apis/get/forecasts/v1/hourly/1hour/%7BlocationKey%7D)
     */
    @GET("/forecasts/v1/hourly/1hour/{locationKey}")
    suspend fun get1HourOfHourlyForecast(@Path("locationKey") locationKey: String): Response<List<HourlyForecastResponse>>
}