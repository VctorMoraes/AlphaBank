package com.victor.alphabank.data.network.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.victor.alphabank.core.NoConnectionException
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException
import javax.inject.Inject

class NoConnectionInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnectionAvailable()) {
            throw NoConnectionException()
        }
        return try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            Log.e("SERVER NOT FOUND", e.stackTraceToString())
            Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("")
                .header("Default-Response", "True")
                .body(
                    "{ \"status\": \"DENIED\" }".toResponseBody("application/json".toMediaTypeOrNull())
                )
                .build()
        }

    }

    private fun isConnectionAvailable(): Boolean {
        val manager =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val activeNetwork = manager.activeNetwork

        return manager.getNetworkCapabilities(activeNetwork)?.let { networkCapabilities ->
            return@let networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}
