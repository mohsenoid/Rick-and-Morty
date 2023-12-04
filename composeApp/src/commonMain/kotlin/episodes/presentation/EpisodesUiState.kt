package episodes.presentation

import episodes.domain.Episode

data class EpisodesUiState(
    val episodes: List<Episode> = emptyList(),
)
