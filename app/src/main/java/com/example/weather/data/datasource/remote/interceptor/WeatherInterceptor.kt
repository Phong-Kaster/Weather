package com.example.weather.data.datasource.remote.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.weather.BuildConfig
import com.example.weather.configuration.Constant
import com.example.weather.data.repository.SettingRepository
import com.panda.wifipassword.data.api.exception.NoConnectivityException
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Interceptors in OkHttp - https://www.linkedin.com/pulse/interceptors-okhttp-mohamad-abuzaid/
 */
class WeatherInterceptor
@Inject
constructor(
    private val context: Context,
    private val settingRepository: SettingRepository
) : Interceptor {

    private val TAG : String = "WeatherInterceptor"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val isNetworkConnected = isNetworkConnected()
        if (!isNetworkConnected) throw NoConnectivityException()


        // Chain.request represents the current request/response chain.
        val originalRequest: Request = chain.request()

        Log.d(TAG, "intercept --------------------------------")
        Log.d(TAG, "intercept - apikey: ${BuildConfig.API_KEY}")

        val url: HttpUrl = originalRequest.url
            .newBuilder()
//            .addQueryParameter("apikey", Constant.ACCU_WEATHER_KEY)
            .addQueryParameter("apikey", BuildConfig.API_KEY)
            .addQueryParameter("details", "true")
            /*.addQueryParameter("language", settingRepository.getLanguage().code)*/
            .addQueryParameter("metric", "false")
            .build()

        val outputRequest = originalRequest
            .newBuilder()
            .addHeader("Authorization", "Bearer accessToken")
            .url(url)
            .build()


        val response: Response = chain.proceed(outputRequest)// pass the request/response on to the next interceptor or the server.
        return response
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        if (network != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities != null) {
                return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ))
            }
        }
        return false
    }
}