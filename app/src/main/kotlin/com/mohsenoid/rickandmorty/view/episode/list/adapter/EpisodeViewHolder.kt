package com.mohsenoid.rickandmorty.view.episode.list.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohsenoid.rickandmorty.databinding.ItemEpisodeBinding
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity

class EpisodeViewHolder internal constructor(internal val binding: ItemEpisodeBinding) :
    ViewHolder(binding.root) {

    fun setEpisode(episode: EpisodeEntity) {
        with(binding) {
            episodeName.text = episode.name
            episodeEpisode.text = episode.episode
            episodeAirDate.text = episode.airDate
        }
    }
}
