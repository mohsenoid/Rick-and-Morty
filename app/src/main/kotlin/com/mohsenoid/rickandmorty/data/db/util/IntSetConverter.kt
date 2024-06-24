package com.mohsenoid.rickandmorty.data.db.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class IntSetConverter {
    @TypeConverter
    fun fromString(value: String): Set<Int> {
        val setType = object : TypeToken<Set<Int>>() {}.type
        return Gson().fromJson(value, setType)
    }

    @TypeConverter
    fun toString(set: Set<Int>): String {
        return Gson().toJson(set)
    }
}
