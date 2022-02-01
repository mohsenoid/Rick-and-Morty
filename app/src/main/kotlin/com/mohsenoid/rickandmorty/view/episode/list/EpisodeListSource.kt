package com.mohsenoid.rickandmorty.view.episode.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.domain.model.PageQueryResult
import com.mohsenoid.rickandmorty.view.mapper.toViewEpisodeItem
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem

class EpisodeListSource(val repository: Repository) : PagingSource<Int, ViewEpisodeItem>() {

    override fun getRefreshKey(state: PagingState<Int, ViewEpisodeItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ViewEpisodeItem> {
        val nextPage = params.key ?: 1
        return when (val episodesResult = repository.getEpisodes(page = nextPage)) {
            is PageQueryResult.Successful -> {
                LoadResult.Page(
                    data = episodesResult.data.map(ModelEpisode::toViewEpisodeItem),
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (episodesResult.data.isEmpty()) null else nextPage + 1,
                )
            }
            else -> {
                LoadResult.Error(Exception("End of the list!"))
            }
        }
    }
}
