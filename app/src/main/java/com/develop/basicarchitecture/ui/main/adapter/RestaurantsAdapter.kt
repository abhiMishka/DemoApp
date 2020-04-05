package com.develop.basicarchitecture.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.basicarchitecture.R
import com.develop.basicarchitecture.network.dataclasses.SearchResponse
import com.develop.basicarchitecture.ui.main.DiffUtilCallBack
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantsAdapter() :
    PagedListAdapter<SearchResponse.Restaurant, RestaurantsAdapter.RestaurantViewHolder>(
        DiffUtilCallBack()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RestaurantViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bindPost(it) }
    }


    class RestaurantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindPost(restaurant : SearchResponse.Restaurant){
            restaurant.restaurant.apply {
                itemView.restaurantNameTv.text = name
                itemView.cuisineNameTv.text = cuisines
            }
        }


    }

}