package com.mohsenoid.rickandmorty.view.episode.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

public class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {

    private final ClickListener listener;

    private List<EpisodeModel> episodes = new ArrayList<>();

    public EpisodeListAdapter(ClickListener listener) {
        this.listener = listener;
    }

    public void setEpisodes(List<EpisodeModel> episodes) {
        this.episodes = episodes;
    }

    public void addMoreEpisodes(List<EpisodeModel> episodes) {
        this.episodes.addAll(episodes);
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        final EpisodeModel episode = episodes.get(position);

        holder.setEpisode(episode);

        holder.view.setOnClickListener(v -> {
            if (listener != null) listener.onEpisodeRowClick(episode);
        });
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public interface ClickListener {
        void onEpisodeRowClick(EpisodeModel episode);
    }
}
