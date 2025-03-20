package com.kenzosutanto.pilotcheck

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CheckWxApiService {
    @GET("metar/{icao}/decoded")
    suspend fun getMetar(
        @Path("icao") icao: String,
        @Query("x-api-key") apiKey: String
    ): MetarResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.checkwx.com/"

    val api: CheckWxApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CheckWxApiService::class.java)
    }
}
