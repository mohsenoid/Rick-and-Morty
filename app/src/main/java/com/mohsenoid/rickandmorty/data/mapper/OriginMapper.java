package com.mohsenoid.rickandmorty.data.mapper;

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel;
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity;

public class OriginMapper {

    private static OriginMapper instance;

    OriginDbMapper dbMapper = new OriginDbMapper();
    OriginEntityMapper entityMapper = new OriginEntityMapper();

    public static synchronized OriginMapper getInstance() {
        if (instance == null)
            instance = new OriginMapper();

        return instance;
    }

    public class OriginDbMapper implements Mapper<NetworkOriginModel, DbOriginModel> {

        @Override
        public DbOriginModel map(NetworkOriginModel input) {
            return new DbOriginModel(
                    input.getName(),
                    input.getUrl()
            );
        }
    }

    public class OriginEntityMapper implements Mapper<DbOriginModel, OriginEntity> {

        @Override
        public OriginEntity map(DbOriginModel input) {
            return new OriginEntity(
                    input.getName(),
                    input.getUrl()
            );
        }
    }
}
