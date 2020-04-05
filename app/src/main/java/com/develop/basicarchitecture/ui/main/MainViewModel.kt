package com.develop.basicarchitecture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.basicarchitecture.network.ItemSourceFactory
import com.develop.basicarchitecture.network.dataclasses.ItemDataSource
import com.develop.basicarchitecture.network.dataclasses.SearchResponse
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {

    private val executor: Executor = Executors.newFixedThreadPool(5)
    private var restaurantPagedList: LiveData<PagedList<SearchResponse.Restaurant>>? = null

    fun providePagingConfig(): PagedList.Config = PagedList.Config.Builder()
        // If placeholders are disabled, not-yet-loaded content
        // will not be present in the list
        .setEnablePlaceholders(true)
        // Defines how far from the edge of loaded content an
        // access must be to trigger further loading.
        .setPrefetchDistance(5)
        // Defines how many items to load when first load occurs.
        .setInitialLoadSizeHint(35)
        // Defines the number of items loaded at once from the DataSource.
        .setPageSize(25)
        .build()


    fun search(searchKey: String) {
        restaurantPagedList = LivePagedListBuilder(
            ItemSourceFactory(Dispatchers.IO, searchKey),
            providePagingConfig())
            .setFetchExecutor(executor)
            .build()

    }

    fun getRestaurantLiveData(): LiveData<PagedList<SearchResponse.Restaurant>>? {
        return restaurantPagedList
    }
//    private fun initializedPagedListBuilder(config: PagedList.Config):
//            LivePagedListBuilder<Long, SearchResponse.Restaurant> {
//
//        val dataSourceFactory = object : DataSource.Factory<Long, SearchResponse.Restaurant>() {
//            override fun create(): DataSource<Long, SearchResponse.Restaurant> {
//                return ItemDataSource(Dispatchers.IO)
//            }
//        }
//        return LivePagedListBuilder(dataSourceFactory, config)
//    }


}
