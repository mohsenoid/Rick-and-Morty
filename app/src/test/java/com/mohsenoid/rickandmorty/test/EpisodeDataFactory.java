package com.mohsenoid.rickandmorty.test;


import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel;
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity;

import java.util.ArrayList;
import java.util.List;

public class EpisodeDataFactory {

    private EpisodeDataFactory() { /* this will prevent making a new object */ }

    public static class Db {
        public static DbEpisodeModel makeDbEpisodeModelWithId(int episodeId) {
            return new DbEpisodeModel(
                    episodeId,
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString() + "," + DataFactory.randomString() + "," + DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString()
            );
        }

        public static DbEpisodeModel makeDbEpisodeModel() {
            return makeDbEpisodeModelWithId(DataFactory.randomInt());
        }

        public static List<DbEpisodeModel> makeDbEpisodesModelList(int count) {
            List<DbEpisodeModel> episodes = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                DbEpisodeModel episode = makeDbEpisodeModel();
                episodes.add(episode);
            }

            return episodes;
        }
    }

    public static class Network {

        public static NetworkEpisodeModel makeNetworkEpisodeModelWithId(int episodeId) {
            return new NetworkEpisodeModel(
                    episodeId,
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomStringList(5),
                    DataFactory.randomString(),
                    DataFactory.randomString()
            );
        }

        public static NetworkEpisodeModel makeNetworkEpisodeModel() {
            return makeNetworkEpisodeModelWithId(DataFactory.randomInt());
        }

        public static List<NetworkEpisodeModel> makeNetworkEpisodesModelList(int count) {
            List<NetworkEpisodeModel> episodes = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                NetworkEpisodeModel episode = makeNetworkEpisodeModel();
                episodes.add(episode);
            }

            return episodes;
        }
    }

    public static class Entity {

        public static EpisodeEntity makeEpisodeEntityWithId(int episodeId) {
            return new EpisodeEntity(
                    episodeId,
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomIntList(5),
                    DataFactory.randomString(),
                    DataFactory.randomString()
            );
        }

        public static EpisodeEntity makeEpisodeEntity() {
            return makeEpisodeEntityWithId(DataFactory.randomInt());
        }

        public static List<EpisodeEntity> makeEpisodesEntityList(int count) {
            List<EpisodeEntity> episodes = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                EpisodeEntity episode = makeEpisodeEntity();
                episodes.add(episode);
            }

            return episodes;
        }
    }
}
