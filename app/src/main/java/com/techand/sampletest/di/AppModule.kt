package com.techand.sampletest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.techand.sampletest.data.network.Api
import com.techand.sampletest.data.network.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    @Singleton
    @Provides
    fun playlistApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

}