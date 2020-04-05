package com.develop.basicarchitecture.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.develop.basicarchitecture.network.dataclasses.SearchResponse

class DiffUtilCallBack : DiffUtil.ItemCallback<SearchResponse.Restaurant>() {
    override fun areItemsTheSame(oldItem: SearchResponse.Restaurant, newItem: SearchResponse.Restaurant): Boolean {
        return oldItem.restaurant.id == newItem.restaurant.id
    }

    override fun areContentsTheSame(oldItem: SearchResponse.Restaurant, newItem: SearchResponse.Restaurant): Boolean {
        return oldItem.restaurant.name == newItem.restaurant.name
                && oldItem.restaurant.location == newItem.restaurant.location
                && oldItem.restaurant.allReviewsCount == newItem.restaurant.allReviewsCount
    }

}