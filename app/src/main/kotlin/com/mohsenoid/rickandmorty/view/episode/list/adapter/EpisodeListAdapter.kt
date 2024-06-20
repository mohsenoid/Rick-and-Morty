package com.mohsenoid.rickandmorty.view.episode.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.rickandmorty.databinding.ItemEpisodeBinding
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem
import java.util.ArrayList

class EpisodeListAdapter :
    RecyclerView.Adapter<EpisodeViewHolder>() {

    private var episodes: MutableList<ViewEpisodeItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setEpisodes(episodes: List<ViewEpisodeItem>) {
        this.episodes = episodes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode: ViewEpisodeItem = episodes[position]
        holder.setEpisode(episode)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }
}
