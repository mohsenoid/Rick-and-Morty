package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel;
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;

import java.util.ArrayList;
import java.util.List;

public class CharacterDataFactory {

    private CharacterDataFactory() { /* this will prevent making a new object of this type */ }

    public static class Db {

        public static DbCharacterModel makeDbCharacterModelWithIdAndStatus(int characterId, String status, Boolean isAlive, Boolean isKilledByUser) {
            return new DbCharacterModel(
                    characterId,
                    DataFactory.randomString(),
                    status,
                    isAlive,
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    OriginDataFactory.Db.makeDbOriginModel(),
                    LocationDataFactory.Db.makeDbLocationModel(),
                    DataFactory.randomString(),
                    DataFactory.randomString() + "," + DataFactory.randomString() + "," + DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    isKilledByUser
            );
        }

        public static DbCharacterModel makeDbCharacterModelWithId(int characterId) {
            return makeDbCharacterModelWithIdAndStatus(characterId, DataFactory.randomString(), DataFactory.randomBoolean(), false);
        }

        public static DbCharacterModel makeDbCharacterModel() {
            return makeDbCharacterModelWithId(DataFactory.randomInt());
        }

        public static List<DbCharacterModel> makeDbCharactersModelList(int count) {
            List<DbCharacterModel> characters = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                DbCharacterModel character = makeDbCharacterModel();
                characters.add(character);
            }

            return characters;
        }
    }

    public static class Network {

        public static NetworkCharacterModel makeNetworkCharacterModelWithId(int characterId) {
            return new NetworkCharacterModel(
                    characterId,
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    OriginDataFactory.Network.makeNetworkOriginModel(),
                    LocationDataFactory.Network.makeNetworkLocationModel(),
                    DataFactory.randomString(),
                    DataFactory.randomStringList(5),
                    DataFactory.randomString(),
                    DataFactory.randomString()
            );
        }

        public static NetworkCharacterModel makeNetworkCharacterModel() {
            return makeNetworkCharacterModelWithId(DataFactory.randomInt());
        }

        public static List<NetworkCharacterModel> makeNetworkCharactersModelList(int count) {
            List<NetworkCharacterModel> characters = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                NetworkCharacterModel character = makeNetworkCharacterModel();
                characters.add(character);
            }

            return characters;
        }
    }

    public static class Entity {

        public static CharacterEntity makeCharacterEntityWithIdAndStatus(int characterId, String status, Boolean isAlive, Boolean isKilledByUser) {
            return new CharacterEntity(
                    characterId,
                    DataFactory.randomString(),
                    status,
                    isAlive,
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    OriginDataFactory.Entity.makeOriginEntity(),
                    LocationDataFactory.Entity.makeLocationEntity(),
                    DataFactory.randomString(),
                    DataFactory.randomIntList(5),
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    isKilledByUser
            );
        }

        public static CharacterEntity makeCharacterEntityWithId(int characterId) {
            return makeCharacterEntityWithIdAndStatus(characterId, DataFactory.randomString(), DataFactory.randomBoolean(), false);
        }

        public static CharacterEntity makeCharacterEntity() {
            return makeCharacterEntityWithId(DataFactory.randomInt());
        }

        public static List<CharacterEntity> makeEntityCharactersModelList(int count) {
            List<CharacterEntity> characters = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                CharacterEntity character = makeCharacterEntity();
                characters.add(character);
            }

            return characters;
        }
    }
}
