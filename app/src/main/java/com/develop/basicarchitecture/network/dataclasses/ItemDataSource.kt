package com.develop.basicarchitecture.network.dataclasses

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.develop.basicarchitecture.network.Repository
import com.develop.basicarchitecture.network.utility.UtilFunctions
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemDataSource(val coroutineContext: CoroutineContext,val searchKey : String): PageKeyedDataSource<Long, SearchResponse.Restaurant>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    private val PAGE_SIZE = 10L

    val apiService = Repository()

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, SearchResponse.Restaurant>
    ) {
        scope.launch  {
            val response = apiService.runSaerchQuery(searchKey,0,PAGE_SIZE)
            Log.i("testApi", "response?.code() : "+response?.code())


            if (response?.isSuccessful == true) {
                val searchQueryResponse = Gson().fromJson(response.body(), SearchResponse::class.java)
                Log.i("testApi", searchQueryResponse.toString())
                callback.onResult(searchQueryResponse.restaurants, 0, (searchQueryResponse.resultsStart+searchQueryResponse.resultsShown).toLong())

            } else {
                scope.launch(Dispatchers.Main) {
                    UtilFunctions.toast(response?.errorBody()?.string() ?: "Error")
                }
            }
        }

    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, SearchResponse.Restaurant>
    ) {
        scope.launch {
            try {
                val response =
                    apiService.runSaerchQuery(keyword = searchKey,start = params.key,count = PAGE_SIZE)

                if (response?.isSuccessful == true) {
                    val searchQueryResponse = Gson().fromJson(response.body(), SearchResponse::class.java)
                    Log.i("testApi", searchQueryResponse.toString())
                    callback.onResult(searchQueryResponse.restaurants,(searchQueryResponse.resultsStart+searchQueryResponse.resultsShown).toLong())

                } else {
                    scope.launch(Dispatchers.Main) {
                        UtilFunctions.toast(response?.errorBody()?.string() ?: "Error")
                    }                }


            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, SearchResponse.Restaurant>
    ) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}