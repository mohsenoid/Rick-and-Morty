package com.mohsenoid.rickandmorty.data.episodes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.episodes.entity.EpisodeEntity

@Dao
internal interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisode(episode: EpisodeEntity)

    @Query("SELECT * FROM episodes WHERE page = :page ORDER BY id ASC")
    fun getEpisodes(page: Int): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE id = :episodeId")
    fun getEpisode(episodeId: Int): EpisodeEntity?
}
