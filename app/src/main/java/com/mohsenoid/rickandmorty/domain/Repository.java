package com.mohsenoid.rickandmorty.domain;

import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public interface Repository {

    List<EpisodeModel> queryEpisodes();
}
