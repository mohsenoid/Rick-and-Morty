package episodes.domain

interface EpisodesRepository {
    suspend fun getEpisodes(page: Int): List<Episode>
    fun close()
}
