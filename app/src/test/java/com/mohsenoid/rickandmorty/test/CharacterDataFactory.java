package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.LocationModel;
import com.mohsenoid.rickandmorty.model.OriginModel;

import java.util.ArrayList;
import java.util.List;

public class CharacterDataFactory {

    private CharacterDataFactory() { /* this will prevent making a new object of this type */ }

    public static CharacterModel makeCharacter() {
        return new CharacterModel(
                DataFactory.randomInt(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                new OriginModel(DataFactory.randomString(), DataFactory.randomString()),
                new LocationModel(DataFactory.randomString(), DataFactory.randomString()),
                DataFactory.randomString(),
                DataFactory.randomStringList(5),
                DataFactory.randomString(),
                DataFactory.randomString()
        );
    }

    public static List<CharacterModel> makeCharactersList(int count) {
        List<CharacterModel> characters = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            CharacterModel character = makeCharacter();
            characters.add(character);
        }

        return characters;
    }
}
