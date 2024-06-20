package com.mohsenoid.rickandmorty.view.episode.list.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohsenoid.rickandmorty.databinding.ItemEpisodeBinding
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem

class EpisodeViewHolder internal constructor(internal val binding: ItemEpisodeBinding) :
    ViewHolder(binding.root) {

    fun setEpisode(episode: ViewEpisodeItem) {
        binding.episode = episode
    }
}
