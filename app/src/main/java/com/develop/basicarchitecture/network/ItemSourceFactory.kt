package com.develop.basicarchitecture.network

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.develop.basicarchitecture.network.dataclasses.ItemDataSource
import com.develop.basicarchitecture.network.dataclasses.SearchResponse
import kotlinx.coroutines.Deferred
import kotlin.coroutines.CoroutineContext

class ItemSourceFactory(val coroutineContext: CoroutineContext, val searchKey : String) : DataSource.Factory<Long, SearchResponse.Restaurant>() {
    override fun create(): DataSource<Long, SearchResponse.Restaurant> {
        Log.i("testApi", "ItemSourceFactory  create called")

        return ItemDataSource(coroutineContext,searchKey)
    }
}