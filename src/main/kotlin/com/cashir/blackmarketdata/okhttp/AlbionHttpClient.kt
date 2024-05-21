package com.cashir.blackmarketdata.okhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class AlbionHttpClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    fun sendGetWebclientRequest(apiUrl: String): Response {
        val request = Request.Builder()
            .url(apiUrl)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response
    }
}
