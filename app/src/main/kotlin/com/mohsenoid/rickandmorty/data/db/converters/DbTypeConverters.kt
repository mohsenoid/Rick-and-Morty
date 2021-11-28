package com.mohsenoid.rickandmorty.data.db.converters

import androidx.room.TypeConverter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

internal class DbTypeConverters {

    @TypeConverter
    fun locationToJson(location: DbEntityLocation?): String? {
        return location?.let { Json.encodeToString(DbEntityLocation.serializer(), it) }
    }

    @TypeConverter
    fun jsonToLocation(locationJson: String?): DbEntityLocation? {
        return locationJson?.let { Json.decodeFromString(DbEntityLocation.serializer(), it) }
    }

    @TypeConverter
    fun originToJson(origin: DbEntityOrigin?): String? {
        return origin?.let { Json.encodeToString(DbEntityOrigin.serializer(), it) }
    }

    @TypeConverter
    fun jsonToOrigin(originJson: String?): DbEntityOrigin? {
        return originJson?.let { Json.decodeFromString(DbEntityOrigin.serializer(), it) }
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
