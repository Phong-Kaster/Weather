package com.example.weather.util

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.panda.wifipassword.data.api.exception.ForbiddenException
import com.panda.wifipassword.data.api.exception.NoConnectivityException
import com.panda.wifipassword.data.api.exception.ServerNotFoundException
import com.panda.wifipassword.data.api.exception.UnauthorizedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

data class BaseResponse<T>(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T? = null
)

object ApiUtil {
    suspend inline fun <reified T : Any> fetchData(crossinline call: () -> Response<BaseResponse<T>>): T {
        var result: T? = null

        val response = call()
        withContext(Dispatchers.Main) {
            try {
                if (response.isSuccessful) {
                    result = response.body()?.data!!
                } else {
                    Log.d("ApiUtil", "Error occurred with code ${response.code()}")
                    when (response.code()) {
                        401 -> throw UnauthorizedException()
                        403 -> throw ServerNotFoundException()
                        404 -> throw ForbiddenException()
                        else -> throw Exception()
                    }
                }
            } catch (e: NoConnectivityException) {
                throw NoConnectivityException()
            } catch (e: Throwable) {
                Log.d("ApiUtil", "Error: ${e.message}")
                throw Exception(e)
            }
        }

        return result ?: throw Exception()
    }

    suspend inline fun <reified T : Any> fetchDataBody(crossinline call: suspend () -> Response<T>): T {
        var result: T? = null


        withContext(Dispatchers.Main) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    result = response.body()
                } else {
                    Log.d("ApiUtil", "Error occurred with code ${response.code()}")
                    when (response.code()) {
                        401 -> throw UnauthorizedException()
                        403 -> throw ForbiddenException()
                        404 -> throw ServerNotFoundException()
                        else -> throw Exception()
                    }
                }
            } catch (e: NoConnectivityException) {
                throw NoConnectivityException()
            } catch (e: Throwable) {
                Log.d("ApiUtil", "Error: ${e.message}")
                throw Exception(e)
            }
        }

        return result ?: throw Exception()
    }
}