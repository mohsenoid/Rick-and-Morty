package com.mohsenoid.rickandmorty.data.db.converters

import androidx.room.TypeConverter
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

internal class DbTypeConverters {

    @TypeConverter
    fun locationToJson(location: DbLocation?): String? {
        return location?.let { Json.encodeToString(DbLocation.serializer(), it) }
    }

    @TypeConverter
    fun jsonToLocation(locationJson: String?): DbLocation? {
        return locationJson?.let { Json.decodeFromString(DbLocation.serializer(), it) }
    }

    @TypeConverter
    fun originToJson(origin: DbOrigin?): String? {
        return origin?.let { Json.encodeToString(DbOrigin.serializer(), it) }
    }

    @TypeConverter
    fun jsonToOrigin(originJson: String?): DbOrigin? {
        return originJson?.let { Json.decodeFromString(DbOrigin.serializer(), it) }
    }

    @TypeConverter
    fun listOfIntToString(list: List<Int>?): String? {
        return list?.let { Json.encodeToString(ListSerializer(Int.serializer()), it) }
    }

    @TypeConverter
    fun stringToListOfInt(string: String?): List<Int>? {
        return string?.let { Json.decodeFromString(ListSerializer(Int.serializer()), it) }
    }
}
