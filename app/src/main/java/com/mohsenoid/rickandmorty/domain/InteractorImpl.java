package com.mohsenoid.rickandmorty.domain;

import com.mohsenoid.rickandmorty.data.service.ApiClient;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public class InteractorImpl implements Interactor {

    private Repository repository;
    private ApiClient apiClient;

    InteractorImpl(Repository repository, ApiClient apiClient) {
        this.repository = repository;
        this.apiClient = apiClient;
    }

    @Override
    public List<EpisodeModel> queryEpisodes() {
        // TODO:
        return null;
    }
}
