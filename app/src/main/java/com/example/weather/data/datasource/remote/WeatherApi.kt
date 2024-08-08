package com.example.weather.data.datasource.remote

import com.example.jetpack.network.dto.LocationAutoDto
import com.example.jetpack.network.dto.LocationGeoDto
import com.example.weather.data.datasource.remote.response.CurrentConditionResponse
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
    suspend fun searchAutocomplete( @Query("q") keyword: String ): Response<List<LocationAutoDto>>


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

//    @GET("/forecasts/v1/hourly/72hour/{locationKey}")
//    fun get12HoursForecast(
//        @Path("locationKey") locationKey: String
//    ): Deferred<Response<List<HourlyResponse>>>
//
//    @GET("/forecasts/v1/daily/10day/{locationKey}")
//    fun get10DaysForecast(
//        @Path("locationKey") locationKey: String
//    ): Deferred<Response<DailyBaseResponse>>
//
//    @GET("/imagery/v1/maps/radsat/1024x1024/{locationKey}")
//    fun getMaps(
//        @Path("locationKey") locationKey: String
//    ): Deferred<Response<MapResponse>>

//    @POST("https://api.reliefweb.int/v1/reports?appname=WeatherLive")
//    fun getListDisaster(@Body text: JsonObject = JsonObject()): Deferred<Response<BaseListResponse<DisasterListResponse>>>
//
//    @GET("https://api.reliefweb.int/v1/reports/{id}")
//    fun getListDisaster(@Path("id") id: String): Deferred<Response<BaseListResponse<DisasterDetailResponse>>>
}