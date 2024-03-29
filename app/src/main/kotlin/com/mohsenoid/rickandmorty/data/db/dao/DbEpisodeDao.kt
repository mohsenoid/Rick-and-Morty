package com.mohsenoid.rickandmorty.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode

@Dao
internal interface DbEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(entityEpisode: DbEpisode)

    @Query(value = "SELECT * FROM episodes")
    suspend fun queryAllEpisodes(): List<DbEpisode>

    @Query(value = "SELECT * FROM episodes LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisode>
}
