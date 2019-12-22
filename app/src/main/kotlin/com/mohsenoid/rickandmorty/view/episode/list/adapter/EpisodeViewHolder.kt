package com.mohsenoid.rickandmorty.view.episode.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodeViewHolder internal constructor(internal val view: View) : ViewHolder(view) {

    fun setEpisode(episode: EpisodeEntity) {
        view.episodeName.text = episode.name
        view.episodeEpisode.text = episode.episode
        view.episodeAirDate.text = episode.airDate
    }
}
