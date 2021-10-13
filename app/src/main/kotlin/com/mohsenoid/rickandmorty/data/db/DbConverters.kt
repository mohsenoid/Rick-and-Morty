package com.mohsenoid.rickandmorty.data.db

import androidx.room.TypeConverter
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class DbConverters {

    @TypeConverter
    fun locationToJson(location: DbLocationModel?): String? {
        return location?.let { Json.encodeToString(DbLocationModel.serializer(), it) }
    }

    @TypeConverter
    fun jsonToLocation(locationJson: String?): DbLocationModel? {
        return locationJson?.let { Json.decodeFromString(DbLocationModel.serializer(), it) }
    }

    @TypeConverter
    fun originToJson(origin: DbOriginModel?): String? {
        return origin?.let { Json.encodeToString(DbOriginModel.serializer(), it) }
    }

    @TypeConverter
    fun jsonToOrigin(originJson: String?): DbOriginModel? {
        return originJson?.let { Json.decodeFromString(DbOriginModel.serializer(), it) }
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
