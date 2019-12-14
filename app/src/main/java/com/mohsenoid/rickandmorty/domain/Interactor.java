package com.mohsenoid.rickandmorty.domain;

import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public interface Interactor {

    List<EpisodeModel> queryEpisodes();
}
