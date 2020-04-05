package com.develop.basicarchitecture.network.dataclasses

abstract class ListItem {

    companion object{
        const val TYPE_CUISINE = 0
        const val TYPE_GENERAL = 1
    }

    abstract fun getType(): Int
}