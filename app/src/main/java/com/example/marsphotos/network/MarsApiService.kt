package com.example.marsphotos.network

import retrofit2.Retrofit
import retrofit2.http.GET
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

class MarsApiService {
    interface MarsApiService {
        @GET("photos")
        suspend fun getPhotos(): List<MarsPhoto>
    }
}

object MarsApi {
    val retrofitService : MarsApiService.MarsApiService by lazy {
        retrofit.create(MarsApiService.MarsApiService::class.java)
    }
}