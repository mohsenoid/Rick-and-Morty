package com.mohsenoid.rickandmorty.view.episode.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.domain.model.PageQueryResult
import com.mohsenoid.rickandmorty.util.StatusProvider
import com.mohsenoid.rickandmorty.view.mapper.toViewEpisodeItem
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EpisodeListViewModel(
    private val repository: Repository,
    private val statusProvider: StatusProvider,
) : ViewModel() {

    val uiState: MutableState<EpisodeListUiState> = mutableStateOf(EpisodeListUiState())

    private val _selectedEpisodeCharacterIds: Channel<IntArray> = Channel()
    val selectedEpisodeCharacterIds: Flow<IntArray> = _selectedEpisodeCharacterIds.receiveAsFlow()

    private var page = 1

    init {
        loadEpisodes()
    }

    fun loadEpisodes() {
        uiState.value = EpisodeListUiState(loading = true)

        page = 1
        getEpisodes()
    }

    fun loadMoreEpisodes(page: Int) {
        uiState.value = uiState.value.copy(loadingMore = true)
        this.page = page
        getEpisodes()
    }

    private fun getEpisodes() {
        if (!statusProvider.isOnline()) {
            uiState.value = uiState.value.copy(isOffline = true)
        }

        viewModelScope.launch {
            when (val result = repository.getEpisodes(page)) {
                is PageQueryResult.Successful -> {
                    if (page == 1) {
                        uiState.value =
                            uiState.value.copy(episodes = result.data.toViewEpisodeItems())
                    } else {
                        val episodes = uiState.value.episodes + result.data.toViewEpisodeItems()
                        uiState.value = uiState.value.copy(episodes = episodes)
                    }
                }
                PageQueryResult.EndOfList -> uiState.value =
                    uiState.value.copy(isEndOfList = true)
                PageQueryResult.Error -> uiState.value = uiState.value.copy(error = true)
            }

            uiState.value = uiState.value.copy(loading = false, loadingMore = false)
        }
    }

    private fun List<ModelEpisode>.toViewEpisodeItems(): List<ViewEpisodeItem> =
        map { episode ->
            episode.toViewEpisodeItem {
                _selectedEpisodeCharacterIds.trySend(episode.characterIds.toIntArray())
            }
        }
}
