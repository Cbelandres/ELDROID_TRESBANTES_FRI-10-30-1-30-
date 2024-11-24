package com.example.guestlog_eldroid

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

object ApiClient {

    // Base URL of your Laravel API (replace with your actual API URL)
    private const val BASE_URL = "http://192.168.61.15:8000/"

    // Retrofit instance
    private val retrofit: Retrofit by lazy {
        // Optional: Logging Interceptor for debugging network requests
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // OkHttp client with logging interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL) // API base URL
            .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON
            .client(client)
            .build()
    }

    // API service to interact with the endpoints
    val guestApiService: GuestApiService by lazy {
        retrofit.create(GuestApiService::class.java) // Creates the API service
    }

}
