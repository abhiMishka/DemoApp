package com.develop.basicarchitecture.network

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpAPIService {

    @GET(EndPoints.SEARCH_QUERY)
    suspend fun search(@Query("q") query : String,
                       @Query("start") start : Long,
                       @Query("count") count : Long): Response<JsonElement>?

}