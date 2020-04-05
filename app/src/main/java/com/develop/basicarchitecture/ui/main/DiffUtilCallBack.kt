package com.develop.basicarchitecture.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.develop.basicarchitecture.network.dataclasses.ListItem
import com.develop.basicarchitecture.network.dataclasses.RestaurantItem

class DiffUtilCallBack : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return (oldItem is RestaurantItem && newItem is RestaurantItem)
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return (oldItem is RestaurantItem && newItem is RestaurantItem)
    }

}