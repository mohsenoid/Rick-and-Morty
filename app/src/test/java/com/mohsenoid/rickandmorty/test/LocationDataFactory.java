package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel;
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity;

import org.jetbrains.annotations.NotNull;

public class LocationDataFactory {

    private LocationDataFactory() { /* this will prevent making a new object of this type */ }

    public static class Db {

        @NotNull
        public static DbLocationModel makeDbLocationModel() {
            return new DbLocationModel(DataFactory.randomString(), DataFactory.randomString());
        }
    }

    public static class Network {

        @NotNull
        public static NetworkLocationModel makeNetworkLocationModel() {
            return new NetworkLocationModel(DataFactory.randomString(), DataFactory.randomString());
        }
    }

    public static class Entity {

        @NotNull
        public static LocationEntity makeLocationEntity() {
            return new LocationEntity(DataFactory.randomString(), DataFactory.randomString());
        }
    }
}
