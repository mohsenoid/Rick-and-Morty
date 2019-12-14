package com.mohsenoid.rickandmorty.data.service;

import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.io.IOException;
import java.util.List;

public interface ApiClient {

    List<EpisodeModel> getEpisodes(int page) throws IOException;
}
