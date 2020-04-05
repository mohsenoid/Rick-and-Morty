package com.mohsenoid.rickandmorty.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel

@Dao
interface DbEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisode(episode: DbEpisodeModel)

    @Query(value = "SELECT * FROM episodes")
    fun queryAllEpisodes(): LiveData<List<DbEpisodeModel>>

    @Query(value = "SELECT * FROM episodes LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun queryAllEpisodesByPage(page: Int, pageSize: Int): LiveData<List<DbEpisodeModel>>
}
