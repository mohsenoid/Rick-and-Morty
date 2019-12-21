package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel;
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity;

import org.jetbrains.annotations.NotNull;

public class OriginDataFactory {

    private OriginDataFactory() { /* this will prevent making a new object of this type */ }

    public static class Db {

        @NotNull
        public static DbOriginModel makeDbOriginModel() {
            return new DbOriginModel(DataFactory.randomString(), DataFactory.randomString());
        }
    }

    public static class Network {

        @NotNull
        public static NetworkOriginModel makeNetworkOriginModel() {
            return new NetworkOriginModel(DataFactory.randomString(), DataFactory.randomString());
        }
    }

    public static class Entity {

        @NotNull
        public static OriginEntity makeOriginEntity() {
            return new OriginEntity(DataFactory.randomString(), DataFactory.randomString());
        }
    }
}
