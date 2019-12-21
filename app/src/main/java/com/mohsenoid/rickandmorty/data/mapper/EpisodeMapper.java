package com.mohsenoid.rickandmorty.data.mapper;

import com.mohsenoid.rickandmorty.data.Serializer;
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel;
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity;

import java.util.ArrayList;
import java.util.List;

public class EpisodeMapper {

    private static EpisodeMapper instance;

    public EpisodeDbMapper dbMapper = new EpisodeDbMapper();
    public EpisodeEntityMapper entityMapper = new EpisodeEntityMapper();

    public static synchronized EpisodeMapper getInstance() {
        if (instance == null)
            instance = new EpisodeMapper();

        return instance;
    }

    public class EpisodeDbMapper implements Mapper<NetworkEpisodeModel, DbEpisodeModel> {

        private static final String SEPARATOR = "/";

        @Override
        public DbEpisodeModel map(NetworkEpisodeModel networkModel) {
            return new DbEpisodeModel(
                    networkModel.getId(),
                    networkModel.getName(),
                    networkModel.getAirDate(),
                    networkModel.getEpisode(),
                    Serializer.serializeStringList(getCharacterIds(networkModel.getCharacters())),
                    networkModel.getUrl(),
                    networkModel.getCreated()
            );
        }

        private List<String> getCharacterIds(List<String> characters) {
            ArrayList<String> ids = new ArrayList<>();

            for (String url : characters) {
                String[] parts = url.split(SEPARATOR);
                String id = parts[parts.length - 1];
                ids.add(id);
            }
            return ids;
        }
    }

    public class EpisodeEntityMapper implements Mapper<DbEpisodeModel, EpisodeEntity>, ListMapper<DbEpisodeModel, EpisodeEntity> {

        @Override
        public EpisodeEntity map(DbEpisodeModel model) {
            return new EpisodeEntity(
                    model.getId(),
                    model.getName(),
                    model.getAirDate(),
                    model.getEpisode(),
                    Serializer.mapStringListToIntegerList(Serializer.deserializeStringList(model.getSerializedCharacterIds())),
                    model.getUrl(),
                    model.getCreated()
            );
        }

        @Override
        public List<EpisodeEntity> map(List<DbEpisodeModel> models) {
            List<EpisodeEntity> entities = new ArrayList<>();

            for (DbEpisodeModel model : models) {
                entities.add(map(model));
            }

            return entities;
        }
    }
}
