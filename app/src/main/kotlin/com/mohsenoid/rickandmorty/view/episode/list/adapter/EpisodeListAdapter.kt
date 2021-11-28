package com.mohsenoid.rickandmorty.view.episode.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.rickandmorty.databinding.ItemEpisodeBinding
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import java.util.ArrayList

class EpisodeListAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<EpisodeViewHolder>() {

    private var episodes: MutableList<ModelEpisode> = ArrayList()

    fun setEpisodes(episodes: List<ModelEpisode>) {
        this.episodes = episodes.toMutableList()
    }

    fun addMoreEpisodes(episodes: List<ModelEpisode>) {
        this.episodes.addAll(episodes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode: ModelEpisode = episodes[position]
        holder.setEpisode(episode)
        holder.binding.root.setOnClickListener { listener.onEpisodeRowClick(episode) }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    interface ClickListener {
        fun onEpisodeRowClick(episode: ModelEpisode)
    }
}
