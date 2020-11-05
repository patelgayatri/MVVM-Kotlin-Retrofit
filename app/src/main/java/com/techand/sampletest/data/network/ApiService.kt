package com.techand.sampletest.data.network

import com.techand.sampletest.data.models.Album
import com.techand.sampletest.data.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://jsonplaceholder.typicode.com/"


interface ApiService {

    @GET("/users")
    suspend fun getUserData(): Response<List<User>>

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId: String): Response<List<Album>>
}

