package com.mohsenoid.rickandmorty.data.episodes.mapper

import com.mohsenoid.rickandmorty.data.episodes.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisode
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisodeEntity
import com.mohsenoid.rickandmorty.data.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import org.junit.Test

class EpisodeMapperTest {
    @Test
    fun toEpisodeEntity() {
        // Given

        // When
        val actualEpisodeEntity = TEST_CHARACTER_REMOTE_MODEL.toEpisodeEntity(page = PAGE)

        // Then
        kotlin.test.assertEquals(TEST_CHARACTER_ENTITY, actualEpisodeEntity)
    }

    @Test
    fun toEpisode() {
        // Given

        // When
        val actualEpisode = TEST_CHARACTER_ENTITY.toEpisode()

        // Then
        kotlin.test.assertEquals(TEST_CHARACTER, actualEpisode)
    }

    companion object {
        private const val PAGE = 0

        private val TEST_CHARACTER_REMOTE_MODEL =
            EpisodeRemoteModel(
                id = 1,
                name = "name",
                airDate = "airDate",
                episode = "episode",
                characters =
                    setOf(
                        "https://rickandmortyapi.com/api/character/1",
                        "https://rickandmortyapi.com/api/character/2",
                        "https://rickandmortyapi.com/api/character/3",
                    ),
                url = "url",
                created = "created",
            )

        private val TEST_CHARACTER_ENTITY =
            EpisodeEntity(
                id = 1,
                page = PAGE,
                name = "name",
                airDate = "airDate",
                episode = "episode",
                characters = setOf(1, 2, 3),
            )

        private val TEST_CHARACTER =
            Episode(
                id = 1,
                name = "name",
                airDate = "airDate",
                episode = "episode",
                characters = setOf(1, 2, 3),
            )
    }
}
