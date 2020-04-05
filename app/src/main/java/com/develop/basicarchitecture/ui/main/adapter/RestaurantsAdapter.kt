package com.develop.basicarchitecture.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.basicarchitecture.R
import com.develop.basicarchitecture.network.dataclasses.CuisineItem
import com.develop.basicarchitecture.network.dataclasses.ListItem
import com.develop.basicarchitecture.network.dataclasses.RestaurantItem
import com.develop.basicarchitecture.ui.main.DiffUtilCallBack
import kotlinx.android.synthetic.main.item_cuisine.view.*
import kotlinx.android.synthetic.main.item_restaurant.view.*


class RestaurantsAdapter() :
    PagedListAdapter<ListItem, RecyclerView.ViewHolder>(
        DiffUtilCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var  viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ListItem.TYPE_GENERAL -> {
                val v1: View = inflater.inflate(R.layout.item_restaurant, parent, false)
                viewHolder = RestaurantViewHolder(v1)
            }
            ListItem.TYPE_CUISINE -> {
                val v2: View = inflater.inflate(R.layout.item_cuisine, parent, false)
                viewHolder = CuisineViewHolder(v2)
            }
        }

        return viewHolder!!

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder.getItemViewType()) {
            ListItem.TYPE_GENERAL -> {
                val restaurantItem: RestaurantItem = getItem(position) as RestaurantItem
                val restaurantViewHolder: RestaurantViewHolder = viewHolder as RestaurantViewHolder
                restaurantViewHolder.bindPost(restaurantItem)
            }
            ListItem.TYPE_CUISINE -> {
                val cuisineItem: CuisineItem = getItem(position) as CuisineItem
                val cuisineViewHolder: CuisineViewHolder = viewHolder as CuisineViewHolder
                cuisineViewHolder.bindPost(cuisineItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getType() ?: 1
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPost(restaurantItem: RestaurantItem) {
            restaurantItem.restaurantItem.restaurant.apply {
                itemView.restaurantNameTv.text = name
                itemView.ratingVaueTv.text = userRating.ratingText
            }
        }
    }

    class CuisineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindPost(cuisineItem: CuisineItem) {
            cuisineItem.apply {
                itemView.cuisineNameTv.text = cuisineName

            }
        }
    }

}