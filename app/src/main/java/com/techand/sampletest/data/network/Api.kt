package com.techand.sampletest.data.network

import com.techand.sampletest.data.models.Album
import com.techand.sampletest.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://jsonplaceholder.typicode.com/"


interface Api {

    @GET("/users")
    suspend fun getUserData(): List<User>

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId: String): List<Album>
}

