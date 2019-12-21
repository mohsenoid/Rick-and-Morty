package com.mohsenoid.rickandmorty.data.mapper;

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel;
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity;

public class LocationMapper {

    private static LocationMapper instance;

    LocationDbMapper dbMapper = new LocationDbMapper();
    LocationEntityMapper entityMapper = new LocationEntityMapper();

    public static synchronized LocationMapper getInstance() {
        if (instance == null)
            instance = new LocationMapper();

        return instance;
    }

    public class LocationDbMapper implements Mapper<NetworkLocationModel, DbLocationModel> {

        @Override
        public DbLocationModel map(NetworkLocationModel input) {
            return new DbLocationModel(
                    input.getName(),
                    input.getUrl()
            );
        }
    }

    public class LocationEntityMapper implements Mapper<DbLocationModel, LocationEntity> {

        @Override
        public LocationEntity map(DbLocationModel input) {
            return new LocationEntity(
                    input.getName(),
                    input.getUrl()
            );
        }
    }
}
