package com.example.menudrawer.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
private const val BASE_URL_LIBROS = "http://10.0.2.2:8080/"

object RetrofitClient {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(ApiService::class.java)
    }

    val serviceLibros: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_LIBROS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
