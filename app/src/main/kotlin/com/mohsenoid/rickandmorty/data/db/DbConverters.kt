package com.mohsenoid.rickandmorty.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel

class DbConverters {

    @TypeConverter
    fun locationToJson(location: DbLocationModel?): String? {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun jsonToLocation(locationJson: String?): DbLocationModel? {
        return Gson().fromJson(locationJson, DbLocationModel::class.java)
    }

    @TypeConverter
    fun originToJson(origin: DbOriginModel?): String? {
        return Gson().toJson(origin)
    }

    @TypeConverter
    fun jsonToOrigin(originJson: String?): DbOriginModel? {
        return Gson().fromJson(originJson, DbOriginModel::class.java)
    }
}
