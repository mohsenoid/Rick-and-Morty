package com.mohsenoid.rickandmorty.view.episode.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mohsenoid.rickandmorty.util.StatusProvider
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class EpisodeListViewModel(
    private val episodeListSource: EpisodeListSource,
    private val statusProvider: StatusProvider,
) : ViewModel() {

    val episodes: Flow<PagingData<ViewEpisodeItem>> = Pager(PagingConfig(pageSize = 20)) {
        episodeListSource
    }.flow.cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<EpisodeListUiState> =
        MutableStateFlow(EpisodeListUiState())
    val uiState: StateFlow<EpisodeListUiState> = _uiState

    private val _selectedEpisodeCharacterIds: Channel<IntArray> = Channel()
    val selectedEpisodeCharacterIds: Flow<IntArray> = _selectedEpisodeCharacterIds.receiveAsFlow()

    // init {
    //     loadEpisodes()
    // }

    // fun loadEpisodes() {
    //     _uiState.value = EpisodeListUiState(isRefreshing = true)
    //
    //     page = 1
    //     getEpisodes()
    // }

    // fun loadMoreEpisodes() {
    //     _uiState.value = _uiState.value.copy(loadingMore = true)
    //     this.page++
    //     getEpisodes()
    // }

    // private fun getEpisodes() {
    //     if (!statusProvider.isOnline()) {
    //         _uiState.value = _uiState.value.copy(isOffline = true)
    //     }
    //
    // viewModelScope.launch {
    //     when (val result = repository.getEpisodes(page)) {
    //         is PageQueryResult.Successful -> {
    //             if (page == 1) {
    //                 _uiState.value =
    //                     _uiState.value.copy(episodes = result.data.toViewEpisodeItems())
    //             } else {
    //                 val episodes = _uiState.value.episodes + result.data.toViewEpisodeItems()
    //                 _uiState.value = _uiState.value.copy(episodes = episodes)
    //             }
    //         }
    //         PageQueryResult.EndOfList -> {
    //             _uiState.value = _uiState.value.copy(isEndOfList = true)
    //         }
    //         PageQueryResult.Error -> {
    //             _uiState.value = _uiState.value.copy(error = true)
    //         }
    //     }
    //
    //     _uiState.value = _uiState.value.copy(isRefreshing = false, loadingMore = false)
    // }
    // }

    // private fun List<ModelEpisode>.toViewEpisodeItems(): List<ViewEpisodeItem> =
    //     map { episode ->
    //         episode.toViewEpisodeItem {
    //             _selectedEpisodeCharacterIds.trySend(episode.characterIds.toIntArray())
    //         }
    //     }

    fun onEpisodeSelected(characterIds: List<Int>) {
        _selectedEpisodeCharacterIds.trySend(characterIds.toIntArray())
    }
}
