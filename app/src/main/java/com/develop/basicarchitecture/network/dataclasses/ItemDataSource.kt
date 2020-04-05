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

class ItemDataSource(private val coroutineContext: CoroutineContext, val searchKey : String): PageKeyedDataSource<Long, ListItem>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    private val PAGE_SIZE = 100L

    val apiService = Repository()

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ListItem>
    ) {
        scope.launch  {
            val response = apiService.runSaerchQuery(searchKey,0,PAGE_SIZE)
            Log.i("testApi", "response?.code() : "+response?.code())

            if (response?.isSuccessful == true) {
                val searchQueryResponse = Gson().fromJson(response.body(), SearchResponse::class.java)
//                Log.i("testApi", searchQueryResponse.toString())
                val consolidatedList = getConsolidatedList(searchQueryResponse)
                Log.i("testApi", consolidatedList.toString())

                callback.onResult(consolidatedList, 0, (searchQueryResponse.resultsStart+searchQueryResponse.resultsShown).toLong())
            } else {
                scope.launch(Dispatchers.Main) {
                    UtilFunctions.toast(response?.errorBody()?.string() ?: "Error")
                }
            }
        }

    }

    private fun getConsolidatedList(searchQueryResponse: SearchResponse): MutableList<ListItem> {
        val map = searchQueryResponse.restaurants.groupBy { it.restaurant.cuisines }
        val consolidatedList = mutableListOf<ListItem>()

        Log.i("testApi", "map : " + map)
        for ((k, v) in map) {
            val cuisineItem = CuisineItem(k)
            consolidatedList.add(cuisineItem)

            val restList = mutableListOf<SearchResponse.Restaurant>()
            for (restaurant in v) {
                consolidatedList.add(RestaurantItem(restaurant))
            }
        }
        return consolidatedList
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ListItem>
    ) {
        scope.launch {
            try {
                val response =
                    apiService.runSaerchQuery(keyword = searchKey,start = params.key,count = PAGE_SIZE)

                if (response?.isSuccessful == true) {
                    val searchQueryResponse = Gson().fromJson(response.body(), SearchResponse::class.java)
//                    Log.i("testApi", searchQueryResponse.toString())
                    val consolidatedList = getConsolidatedList(searchQueryResponse)
                    Log.i("testApi", consolidatedList.toString())
                    callback.onResult(consolidatedList,(searchQueryResponse.resultsStart+searchQueryResponse.resultsShown).toLong())

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
        callback: LoadCallback<Long, ListItem>
    ) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}