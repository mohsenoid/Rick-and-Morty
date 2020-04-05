package com.mohsenoid.rickandmorty.view.episode.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import kotlinx.coroutines.launch

class EpisodeListViewModel(
    application: Application,
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : AndroidViewModel(application) {

    private val _episodes: MutableLiveData<List<EpisodeEntity>> = MutableLiveData()
    val episodes: LiveData<List<EpisodeEntity>>
        get() = _episodes

    private val _loadingStatus: MutableLiveData<LoadingStatus> = MutableLiveData()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    private val page: MutableLiveData<Int> = MutableLiveData(1)

    fun loadEpisodes() {
        _loadingStatus.value = LoadingStatus.Loading
        page.value = 1
        getEpisodes()
    }

    fun loadMoreEpisodes(page: Int) {
        _loadingStatus.value = LoadingStatus.LoadingMore
        this.page.value = page
        getEpisodes()
    }

    private fun getEpisodes() {
        if (!configProvider.isOnline()) {
            _loadingStatus.value = LoadingStatus.Offline
        }

        viewModelScope.launch {
            try {
                repository.getEpisodes(page.value ?: 1).observeForever { result ->
                    if (result.isEmpty()) {
                        _loadingStatus.value = LoadingStatus.ReachedEndOfList
                    } else {

                        if (page.value == 1) {
                            _episodes.value = result
                            _loadingStatus.value = LoadingStatus.LoadSuccessful
                        } else {
                            _episodes.value = _episodes.value?.plus(result) ?: result
                            _loadingStatus.value = LoadingStatus.LoadMoreSuccessful
                        }
                    }
                }
            } catch (e: Exception) {
                _loadingStatus.value = LoadingStatus.Failed(e)
            }
        }
    }

    sealed class LoadingStatus {
        object LoadSuccessful : LoadingStatus()
        object LoadMoreSuccessful : LoadingStatus()
        object Loading : LoadingStatus()
        object LoadingMore : LoadingStatus()
        object ReachedEndOfList : LoadingStatus()
        object Offline : LoadingStatus()
        class Failed(val throwable: Throwable) : LoadingStatus()
    }
}
