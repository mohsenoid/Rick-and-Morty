package com.mohsenoid.rickandmorty.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode

@Dao
interface DbEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(entityEpisode: DbEntityEpisode)

    @Query(value = "SELECT * FROM episodes")
    suspend fun queryAllEpisodes(): List<DbEntityEpisode>

    @Query(value = "SELECT * FROM episodes LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEntityEpisode>
}
