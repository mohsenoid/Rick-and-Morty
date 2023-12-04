package com.mohsenoid.rickandmorty.episode.data

import com.mohsenoid.rickandmorty.character.data.model.CharacterResponse
import com.mohsenoid.rickandmorty.episode.data.model.EpisodeResponse
import com.mohsenoid.rickandmorty.episode.domain.EpisodeRepository
import com.mohsenoid.rickandmorty.episode.domain.model.Character
import com.mohsenoid.rickandmorty.episode.domain.model.Episode

internal class EpisodeRepositoryImpl(
    private val remote: EpisodeRemoteDataSource,
) : EpisodeRepository {

    override suspend fun getEpisode(episodeId: Int): Episode {
        val episodeResponse = remote.fetchEpisode(episodeId)
        val characterIds: List<Int> = episodeResponse.characters.map { characterUrl ->
            characterUrl.substringAfterLast("/").toInt()
        }
        val characters = remote.fetchCharacters(characterIds).map { characterResponse ->
            characterResponse.toCharacter()
        }

        return episodeResponse.toEpisode(characters)
    }

    private fun EpisodeResponse.toEpisode(characters: List<Character>): Episode {
        return Episode(
            id = id,
            name = name,
            episode = episode,
            characters = characters,
        )

    }

    private fun CharacterResponse.toCharacter() = Character(
        id = id,
        name = name,
        status = status,
        image = image,
        created = created,
    )

    override fun close() {
        remote.close()
    }
}
