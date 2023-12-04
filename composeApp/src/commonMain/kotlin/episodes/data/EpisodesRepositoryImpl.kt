package episodes.data

import episodes.domain.Episode
import episodes.domain.EpisodesRepository

internal class EpisodesRepositoryImpl(
    private val remote: EpisodesRemoteDataSource,
) : EpisodesRepository {

    override suspend fun getEpisodes(page: Int): List<Episode> {
        return remote.fetchEpisodes(page).results.map {
            Episode(
                id = it.id,
                name = it.name,
                airDate = it.airDate,
                episode = it.episode,
                characters = it.characters,
                created = it.created,
            )
        }
    }

    override fun close() {
        remote.close()
    }
}
