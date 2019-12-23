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

    @Query("SELECT * FROM episodes LIMIT ${DbConstants.PAGE_SIZE} OFFSET (:page - 1) * ${DbConstants.PAGE_SIZE}")
    suspend fun queryAllEpisodes(page: Int): List<DbEpisodeModel>
}
