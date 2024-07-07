package com.mohsenoid.rickandmorty.data.episodes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.episodes.entity.EpisodeEntity

@Dao
internal interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: EpisodeEntity)

    @Query("SELECT * FROM episodes WHERE page = :page ORDER BY id ASC")
    suspend fun getEpisodes(page: Int): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE id = :episodeId")
    suspend fun getEpisode(episodeId: Int): EpisodeEntity?
}
