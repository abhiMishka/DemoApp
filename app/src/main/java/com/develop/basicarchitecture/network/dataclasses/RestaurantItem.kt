package com.develop.basicarchitecture.network.dataclasses

class RestaurantItem(val restaurantItem : SearchResponse.Restaurant) : ListItem(){

    override fun getType(): Int {
        return TYPE_GENERAL
    }

}