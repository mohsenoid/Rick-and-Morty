package com.mohsenoid.rickandmorty.view.episode.list.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import org.jetbrains.annotations.NotNull;

public class EpisodeViewHolder extends RecyclerView.ViewHolder {

    public final View view;

    private final TextView episodeName;
    private final TextView episodeEpisode;
    private final TextView episodeAirDate;

    EpisodeViewHolder(View view) {
        super(view);
        this.view = view;

        episodeName = view.findViewById(R.id.episode_name);
        episodeEpisode = view.findViewById(R.id.episode_episode);
        episodeAirDate = view.findViewById(R.id.episode_air_date);
    }

    public void setEpisode(@NotNull EpisodeModel episode) {
        episodeName.setText(episode.getName());
        episodeEpisode.setText(episode.getEpisode());
        episodeAirDate.setText(episode.getAirDate());
    }
}
