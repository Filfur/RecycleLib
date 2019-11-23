package com.noteamname.androidhackathon.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.noteamname.androidhackathon.networking.models.Book
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import java.net.SocketTimeoutException


class NetworkingService {
    suspend fun getBooks(): List<Book> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://white-skunk-73.localtunnel.me/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()

        try {
            val service = retrofit.create<FundamentalsService>(FundamentalsService::class.java)

            val response = service.getBooks()
            return if (response.isSuccessful) {
                response.body() ?: listOf()
            } else {
                listOf()
            }
        } catch (ex: SocketTimeoutException) {
            return listOf()
        }
    }
}