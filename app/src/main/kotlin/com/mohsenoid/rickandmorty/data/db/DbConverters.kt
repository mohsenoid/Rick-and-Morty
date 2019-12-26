package com.mohsenoid.rickandmorty.data.db

import androidx.room.TypeConverter
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.util.extension.deserializeStringList
import com.mohsenoid.rickandmorty.util.extension.fromJson
import com.mohsenoid.rickandmorty.util.extension.mapStringListToIntegerList
import com.mohsenoid.rickandmorty.util.extension.serializeIntegerListToCsv
import com.mohsenoid.rickandmorty.util.extension.toJson

class DbConverters {

    @TypeConverter
    fun locationToJson(location: DbLocationModel?): String? {
        return location?.toJson()
    }

    @TypeConverter
    fun jsonToLocation(locationJson: String?): DbLocationModel? {
        return locationJson?.fromJson()
    }

    @TypeConverter
    fun originToJson(origin: DbOriginModel?): String? {
        return origin?.toJson()
    }

    @TypeConverter
    fun jsonToOrigin(originJson: String?): DbOriginModel? {
        return originJson?.fromJson()
    }

    @TypeConverter
    fun listOfIntToString(list: List<Int>?): String? {
        return list?.serializeIntegerListToCsv()
    }

    @TypeConverter
    fun stringToListOfInt(string: String?): List<Int>? {
        return string?.deserializeStringList()?.mapStringListToIntegerList()
    }
}
