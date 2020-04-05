package com.develop.basicarchitecture.ui.main.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.develop.basicarchitecture.R
import com.develop.basicarchitecture.TopApplicationClass
import com.develop.basicarchitecture.network.Repository
import com.develop.basicarchitecture.network.dataclasses.CuisineItem
import com.develop.basicarchitecture.network.dataclasses.ListItem
import com.develop.basicarchitecture.network.dataclasses.RestaurantItem
import com.develop.basicarchitecture.network.dataclasses.SearchResponse
import com.develop.basicarchitecture.utility.UtilFunctions
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemDataSource(private val coroutineContext: CoroutineContext, val searchKey: String) :
    PageKeyedDataSource<Long, ListItem>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    private val PAGE_SIZE = 20L
    val apiService = Repository()

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ListItem>
    ) {
        try {
            scope.launch {
                val response = apiService.runSearchQuery(searchKey, 0, PAGE_SIZE)
                if (response?.isSuccessful == true) {
                    val searchQueryResponse =
                        Gson().fromJson(response.body(), SearchResponse::class.java)
                    val consolidatedList = getConsolidatedList(searchQueryResponse)
                    callback.onResult(
                        consolidatedList,
                        0,
                        (searchQueryResponse.resultsStart + searchQueryResponse.resultsShown).toLong()
                    )
                } else {
                    showError(
                        response?.errorBody()?.string() ?: TopApplicationClass.getInstance()
                            .getString(R.string.failed_data_fetching)
                    )
                }
            }
        } catch (exception: Exception) {
            showError(TopApplicationClass.getInstance().getString(R.string.failed_data_fetching))
        }

    }

    private fun getConsolidatedList(searchQueryResponse: SearchResponse): MutableList<ListItem> {

        val responseList = searchQueryResponse.restaurants
        val map = mutableMapOf<String, MutableList<SearchResponse.Restaurant>>()
//            searchQueryResponse.restaurants.groupBy { it.restaurant.cuisines }

        for (item in responseList) {
            val cuisines = item.restaurant.cuisines
            for (cuisine in cuisines.split(",")) {
                if (map.containsKey(cuisine)) {
                    val list = map[cuisine]
                    list?.let {
                        it.add(item)
                        map.put(cuisine, it)
                    }
                } else {
                    val list = mutableListOf<SearchResponse.Restaurant>()
                    list.add(item)
                    map[cuisine] = list
                }
            }
        }

        val consolidatedList = mutableListOf<ListItem>()

        for ((k, v) in map) {
            val cuisineItem =
                CuisineItem(k)
            consolidatedList.add(cuisineItem)

            for (restaurant in v) {
                consolidatedList.add(
                    RestaurantItem(
                        restaurant
                    )
                )
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
                    apiService.runSearchQuery(
                        keyword = searchKey,
                        start = params.key,
                        count = PAGE_SIZE
                    )

                if (response?.isSuccessful == true) {
                    val searchQueryResponse =
                        Gson().fromJson(response.body(), SearchResponse::class.java)
//                    Log.i("testApi", searchQueryResponse.toString())
                    val consolidatedList = getConsolidatedList(searchQueryResponse)
                    Log.i("testApi", consolidatedList.toString())
                    callback.onResult(
                        consolidatedList,
                        (searchQueryResponse.resultsStart + searchQueryResponse.resultsShown).toLong()
                    )

                } else {
                    showError(
                        response?.errorBody()?.string() ?: TopApplicationClass.getInstance()
                            .getString(R.string.failed_data_fetching)
                    )
                }
            } catch (exception: Exception) {
                showError(
                    TopApplicationClass.getInstance().getString(R.string.failed_data_fetching)
                )
            }
        }
    }

    private fun showError(message: String) {
        scope.launch(Dispatchers.Main) {
            UtilFunctions.toast(message)
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ListItem>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}