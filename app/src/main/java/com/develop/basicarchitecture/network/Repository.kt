package com.develop.basicarchitecture.network


class Repository {
    private val client : HttpAPIService  = ApiClient.getClient().create(HttpAPIService::class.java)
    suspend fun runSaerchQuery(keyword : String, start : Long, count : Long) =
        client.search(keyword,start,count)
}