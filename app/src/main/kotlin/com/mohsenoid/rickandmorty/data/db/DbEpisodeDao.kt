package com.mohsenoid.rickandmorty.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel

@Dao
interface DbEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: DbEpisodeModel)

    @Query(value = "SELECT * FROM episodes")
    suspend fun queryAllEpisodes(): List<DbEpisodeModel>

    @Query(value = "SELECT * FROM episodes LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisodeModel>
}
