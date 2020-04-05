package com.develop.basicarchitecture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.develop.basicarchitecture.ui.main.paging.ItemSourceFactory
import com.develop.basicarchitecture.network.dataclasses.ListItem
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {

    private val executor: Executor = Executors.newFixedThreadPool(5)
    private var restaurantPagedList: LiveData<PagedList<ListItem>>? = null

    fun providePagingConfig(): PagedList.Config = PagedList.Config.Builder()
        // If placeholders are disabled, not-yet-loaded content
        // will not be present in the list
        .setEnablePlaceholders(true)
        // Defines how far from the edge of loaded content an
        // access must be to trigger further loading.
        .setPrefetchDistance(15)
        // Defines how many items to load when first load occurs.
        .setInitialLoadSizeHint(10)
        // Defines the number of items loaded at once from the DataSource.
        .setPageSize(10)
        .build()




    fun search(searchKey: String) {
        restaurantPagedList = LivePagedListBuilder(
            ItemSourceFactory(
                Dispatchers.IO,
                searchKey
            ),
            providePagingConfig())
            .setFetchExecutor(executor)
            .build()
    }

    fun getRestaurantLiveData(): LiveData<PagedList<ListItem>>? {
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
