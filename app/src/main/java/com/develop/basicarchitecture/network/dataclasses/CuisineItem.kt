package com.develop.basicarchitecture.network.dataclasses

class CuisineItem(val cuisineName : String) : ListItem(){
    override fun getType(): Int {
        return TYPE_CUISINE
    }

}