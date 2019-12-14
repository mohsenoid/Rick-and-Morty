package com.mohsenoid.rickandmorty.ui.episode.list.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import org.jetbrains.annotations.NotNull;

public class EpisodeViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    private EpisodeModel episode;

    private TextView episodeName;
    private TextView episodeEpisode;
    private TextView episodeAirDate;

    public EpisodeViewHolder(View view) {
        super(view);
        this.view = view;

        episodeName = view.findViewById(R.id.episode_name);
        episodeEpisode = view.findViewById(R.id.episode_episode);
        episodeAirDate = view.findViewById(R.id.episode_air_date);
    }

    public EpisodeModel getCharacter() {
        return episode;
    }

    public void setEpisode(@NotNull EpisodeModel episode) {
        this.episode = episode;

        episodeName.setText(episode.getName());
        episodeEpisode.setText(episode.getEpisode());
        episodeAirDate.setText(episode.getAirDate());
    }
}
