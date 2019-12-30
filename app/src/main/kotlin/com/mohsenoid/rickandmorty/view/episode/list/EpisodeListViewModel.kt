package com.mohsenoid.rickandmorty.view.episode.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.data.exception.EndOfListException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeListViewModel(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : ViewModel() {

    private var page = 1

    private val episodes: MutableLiveData<List<EpisodeEntity>> by lazy {
        val liveData = MutableLiveData<List<EpisodeEntity>>()
        viewModelScope.launch { getEpisodes() }
        return@lazy liveData
    }

    fun getEpisodesLiveData(): LiveData<List<EpisodeEntity>> {
        return episodes
    }

    private val state: MutableLiveData<State> by lazy {
        val liveData = MutableLiveData<State>()
        liveData.postValue(State.Loading.FirstPage)
        return@lazy liveData
    }

    fun getStateLiveData(): LiveData<State> = state

    fun loadEpisodes() {
        state.postValue(State.Loading.FirstPage)
        page = 1

        viewModelScope.launch {
            getEpisodes()
        }
    }

    fun loadMoreEpisodes(page: Int) {
        state.postValue(State.Loading.MorePage)
        this.page = page

        viewModelScope.launch {
            getEpisodes()
        }
    }

    private suspend fun getEpisodes() {
        if (!configProvider.isOnline()) {
            state.postValue(State.Offline)
        }

        try {
            val result: List<EpisodeEntity> = repository.getEpisodes(page)
            if (page == 1) {
                episodes.postValue(result)
            } else {
                episodes.postValue(episodes.value!!.plus(result))
            }
        } catch (e: Exception) {
            if (e is EndOfListException) {
                state.postValue(State.EndOfList)
            } else {
                state.postValue(State.Error(e.message ?: e.toString()))
            }
        }
    }

    sealed class State {

        sealed class Loading : State() {
            object FirstPage : Loading()
            object MorePage : Loading()
        }

        object EndOfList : State()
        object Offline : State()
        class Error(val errorMessage: String) : State()
    }

    class Factory @Inject internal constructor(
        private val repository: Repository,
        private val configProvider: ConfigProvider
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EpisodeListViewModel(repository, configProvider) as T
        }
    }
}
