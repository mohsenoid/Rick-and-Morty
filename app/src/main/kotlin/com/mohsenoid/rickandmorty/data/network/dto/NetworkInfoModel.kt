package com.mohsenoid.rickandmorty.data.network.dto

import org.json.JSONObject

data class NetworkInfoModel(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
) {

    companion object {
        private const val TAG_COUNT = "count"
        private const val TAG_PAGES = "pages"
        private const val TAG_NEXT = "next"
        private const val TAG_PREV = "prev"

        fun fromJson(jsonObject: JSONObject): NetworkInfoModel {
            val count = jsonObject.getInt(TAG_COUNT)
            val pages = jsonObject.getInt(TAG_PAGES)
            val next = jsonObject.getString(TAG_NEXT)
            val prev = jsonObject.getString(TAG_PREV)

            return NetworkInfoModel(
                count = count,
                pages = pages,
                next = next,
                prev = prev
            )
        }
    }
}
