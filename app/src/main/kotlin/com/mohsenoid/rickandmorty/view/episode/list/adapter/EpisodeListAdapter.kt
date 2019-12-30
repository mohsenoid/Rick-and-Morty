package com.mohsenoid.rickandmorty.view.episode.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import java.util.ArrayList

class EpisodeListAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<EpisodeViewHolder>() {

    private var episodes: MutableList<EpisodeEntity> = ArrayList()

    fun setEpisodes(episodes: List<EpisodeEntity>) {
        this.episodes = episodes.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode: EpisodeEntity = episodes[position]
        holder.setEpisode(episode)
        holder.view.setOnClickListener { listener.onEpisodeRowClick(episode) }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    interface ClickListener {

        fun onEpisodeRowClick(episode: EpisodeEntity)
    }
}
