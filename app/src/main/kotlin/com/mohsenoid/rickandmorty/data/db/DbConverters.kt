package com.mohsenoid.rickandmorty.data.db

import androidx.room.TypeConverter
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import kotlinx.serialization.serializer

class DbConverters {

    private val json = Json(JsonConfiguration.Stable)

    @TypeConverter
    fun locationToJson(location: DbLocationModel?): String? {
        return location?.let { json.stringify(DbLocationModel.serializer(), it) }
    }

    @TypeConverter
    fun jsonToLocation(locationJson: String?): DbLocationModel? {
        return locationJson?.let { json.parse(DbLocationModel.serializer(), it) }
    }

    @TypeConverter
    fun originToJson(origin: DbOriginModel?): String? {
        return origin?.let { json.stringify(DbOriginModel.serializer(), it) }
    }

    @TypeConverter
    fun jsonToOrigin(originJson: String?): DbOriginModel? {
        return originJson?.let { json.parse(DbOriginModel.serializer(), it) }
    }

    @TypeConverter
    fun listOfIntToString(list: List<Int>?): String? {
        return list?.let { json.stringify(Int.serializer().list, it) }
    }

    @TypeConverter
    fun stringToListOfInt(string: String?): List<Int>? {
        return string?.let { json.parse(Int.serializer().list, it) }
    }
}
