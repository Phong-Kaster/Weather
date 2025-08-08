package com.example.weather.data.datasource.remotektor


import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.get

@Singleton
class KtorHttpRequest
@Inject
constructor(
    private val ktorHttpClient: HttpClient
) {
    private val TAG = KtorHttpRequest::class.java.simpleName

    suspend fun getGoogleFile() {
         try {
            // Builder approach
            val response1 = ktorHttpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "www.google.com"
                    encodedPath = "path/file.html"
                }
            }

            // Direct URL approach
            //val response2 = ktorHttpClient.get("https://www.google.com/path/file.html")

            val statusCode = response1.status.value
            when(statusCode) {
                in 200..299 -> {
                    val carDetails = response1.body<String>()
                    Log.d(TAG, "getGoogleFile response1 = $carDetails")
                }
                else -> {
                    Log.d(TAG, "getGoogleFile has error with status code $statusCode")
                }
            }

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}