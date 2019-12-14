package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

public class EpisodeDataFactory {

    private EpisodeDataFactory() { /* this will prevent making a new object */ }

    public static EpisodeModel makeEpisode() {
        return new EpisodeModel(
                DataFactory.randomInt(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomStringList(5),
                DataFactory.randomString(),
                DataFactory.randomString()
        );
    }

    public static List<EpisodeModel> makeEpisodeList(int count) {
        List<EpisodeModel> episodes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            EpisodeModel episode = makeEpisode();
            episodes.add(episode);
        }

        return episodes;
    }
}
