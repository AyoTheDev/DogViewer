package com.ayo.api.endpoints

import com.ayo.api.model.DogApiResponseItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface EndPoints {

    @GET("/v1/breeds")
    suspend fun getDogs(): List<DogApiResponseItem>

    @GET("/v1/breeds/search")
    suspend fun searchDogs(@Query("q") query: String): List<DogApiResponseItem>

}
