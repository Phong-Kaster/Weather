package com.example.weather.data.datasource.remotektor.plugin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.weather.data.repository.SettingRepository
import com.panda.wifipassword.data.api.exception.NoConnectivityException
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import com.example.weather.BuildConfig
import javax.inject.Inject

/**
 * KtorPlugin is identical to data/remote/interceptor/WeatherInterceptor in Retrofit
 */
class KtorPlugin
@Inject
constructor(
    private val context: Context,
    private val settingRepository: SettingRepository
) {
    private val TAG = this.javaClass.simpleName

    fun install(client: HttpClient) {
        client
            .plugin(HttpSend)
            .intercept { request ->

                if (!isNetworkConnected()) {
                    throw NoConnectivityException()
                }


                // Modify URL with query params
                val urlBuilder = URLBuilder(request.url)
                urlBuilder.parameters.append("apikey", BuildConfig.API_KEY)
                urlBuilder.parameters.append("details", "true")
                urlBuilder.parameters.append("metric", "false")
                // urlBuilder.parameters.append("language", settingRepository.getLanguage().code)
                val newUrl = urlBuilder.build()

                request.url.parameters.clear()
                request.url.takeFrom(newUrl)

                // Add Authorization header
                request.headers.append("Authorization", "Bearer accessToken")


                Log.d(TAG, "Adding API key and headers")

                Log.d(TAG, "newUrl is ${newUrl}")
                Log.d(TAG, "request is ${request}")

                execute(request) // continue with modified request
            }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}