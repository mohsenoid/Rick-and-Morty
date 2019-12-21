package com.mohsenoid.rickandmorty.data.mapper;

import androidx.annotation.VisibleForTesting;

import com.mohsenoid.rickandmorty.data.Serializer;
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel;
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;

import java.util.ArrayList;
import java.util.List;

public class CharacterMapper {

    private static CharacterMapper instance;

    public CharacterDbMapper dbMapper = new CharacterDbMapper();
    public CharacterEntityMapper entityMapper = new CharacterEntityMapper();

    private OriginMapper originMapper;
    private LocationMapper locationMapper;

    public CharacterMapper(OriginMapper originMapper, LocationMapper locationMapper) {
        this.originMapper = originMapper;
        this.locationMapper = locationMapper;
    }

    public static synchronized CharacterMapper getInstance(OriginMapper originMapper, LocationMapper locationMapper) {
        if (instance == null)
            instance = new CharacterMapper(originMapper, locationMapper);

        return instance;
    }

    public class CharacterDbMapper implements Mapper<NetworkCharacterModel, DbCharacterModel> {
        @VisibleForTesting
        public static final String ALIVE = "alive";

        @Override
        public DbCharacterModel map(NetworkCharacterModel input) {
            return new DbCharacterModel(
                    input.getId(),
                    input.getName(),
                    input.getStatus(),
                    input.getStatus().equalsIgnoreCase(ALIVE),
                    input.getSpecies(),
                    input.getType(),
                    input.getGender(),
                    originMapper.dbMapper.map(input.getOrigin()),
                    locationMapper.dbMapper.map(input.getLocation()),
                    input.getImage(),
                    Serializer.serializeStringList(input.getEpisodes()),
                    input.getUrl(),
                    input.getCreated(),
                    null
            );
        }
    }

    public class CharacterEntityMapper implements Mapper<DbCharacterModel, CharacterEntity>, ListMapper<DbCharacterModel, CharacterEntity> {

        private static final String SEPARATOR = "/";

        @Override
        public CharacterEntity map(DbCharacterModel model) {
            return new CharacterEntity(
                    model.getId(),
                    model.getName(),
                    model.getStatus(),
                    model.getStatusAlive(),
                    model.getSpecies(),
                    model.getType(),
                    model.getGender(),
                    originMapper.entityMapper.map(model.getOrigin()),
                    locationMapper.entityMapper.map(model.getLocation()),
                    model.getImage(),
                    Serializer.mapStringListToIntegerList(getEpisodeIds(Serializer.deserializeStringList(model.getSerializedEpisodes()))),
                    model.getUrl(),
                    model.getCreated(),
                    model.getKilledByUser()
            );
        }

        private List<String> getEpisodeIds(List<String> episodes) {
            ArrayList<String> ids = new ArrayList<>();

            for (String url : episodes) {
                String[] parts = url.split(SEPARATOR);
                String id = parts[parts.length - 1];
                ids.add(id);
            }
            return ids;
        }

        @Override
        public List<CharacterEntity> map(List<DbCharacterModel> models) {
            List<CharacterEntity> entities = new ArrayList<>();

            for (DbCharacterModel model : models) {
                entities.add(map(model));
            }

            return entities;
        }
    }
}
