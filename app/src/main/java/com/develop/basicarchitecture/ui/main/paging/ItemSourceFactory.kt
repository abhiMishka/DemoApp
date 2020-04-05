package com.develop.basicarchitecture.ui.main.paging

import android.util.Log
import androidx.paging.DataSource
import com.develop.basicarchitecture.network.dataclasses.ListItem
import kotlin.coroutines.CoroutineContext

class ItemSourceFactory(private val coroutineContext: CoroutineContext, private val searchKey : String) : DataSource.Factory<Long, ListItem>() {
    override fun create(): DataSource<Long, ListItem> {
        return ItemDataSource(
            coroutineContext,
            searchKey
        )
    }
}