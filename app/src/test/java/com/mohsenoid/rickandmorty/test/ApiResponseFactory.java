package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.model.LocationModel;
import com.mohsenoid.rickandmorty.model.OriginModel;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseFactory {

    public static class Episode {

        private static final int VALUE_ID = 1;
        private static final String VALUE_NAME = "Pilot";
        private static final String VALUE_AIR_DATE = "December 2, 2013";
        private static final String VALUE_EPISODE = "S01E01";
        private static final String VALUE_CHARACTER = "https://rickandmortyapi.com/api/character/1";
        private static final String VALUE_URL = "https://rickandmortyapi.com/api/episode/1";
        private static final String VALUE_CREATED = "2017-11-10T12:56:33.798Z";

        public static String EPISODES_JSON = "{\n" +
                "  \"info\": {\n" +
                "    \"count\": 1,\n" +
                "    \"pages\": 1,\n" +
                "    \"next\": \"\",\n" +
                "    \"prev\": \"\"\n" +
                "  },\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"id\": " + VALUE_ID + ",\n" +
                "      \"name\": \"" + VALUE_NAME + "\",\n" +
                "      \"air_date\": \"" + VALUE_AIR_DATE + "\",\n" +
                "      \"episode\": \"" + VALUE_EPISODE + "\",\n" +
                "      \"characters\": [\n" +
                "        \"" + VALUE_CHARACTER + "\"\n" +
                "      ],\n" +
                "      \"url\": \"" + VALUE_URL + "\",\n" +
                "      \"created\": \"" + VALUE_CREATED + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        public static List<EpisodeModel> episodesResponse() {
            List<EpisodeModel> episodes = new ArrayList<>();

            List<String> characters = new ArrayList<>();
            characters.add(VALUE_CHARACTER);

            EpisodeModel episode = new EpisodeModel(VALUE_ID, VALUE_NAME, VALUE_AIR_DATE, VALUE_EPISODE, characters, VALUE_URL, VALUE_CREATED);
            episodes.add(episode);

            return episodes;
        }
    }

    public static class Characters {

        public static String CHARACTERS_JSON = "[\n" +
                "  " + CharacterDetails.CHARACTER_DETAILS_JSON + "\n" +
                "]";

        public static List<CharacterModel> charactersResponse() {
            List<CharacterModel> characters = new ArrayList<>();

            CharacterModel character = CharacterDetails.characterResponse();
            characters.add(character);

            return characters;
        }
    }

    public static class CharacterDetails {

        private static final int VALUE_ID = 1;
        private static final String VALUE_NAME = "Rick Sanchez";
        private static final String VALUE_STATUS = "Alive";
        private static final String VALUE_SPECIES = "Human";
        private static final String VALUE_TYPE = "";
        private static final String VALUE_GENDER = "Male";
        private static final String VALUE_ORIGIN_NAME = "Earth (C-137)";
        private static final String VALUE_ORIGIN_URL = "https://rickandmortyapi.com/api/location/1";
        private static final String VALUE_LOCATION_NAME = "Earth (Replacement Dimension)";
        private static final String VALUE_LOCATION_URL = "https://rickandmortyapi.com/api/location/20";
        private static final String VALUE_IMAGE = "https://rickandmortyapi.com/api/character/avatar/1.jpeg";
        private static final String VALUE_EPISODE = "https://rickandmortyapi.com/api/episode/1";
        private static final String VALUE_URL = "https://rickandmortyapi.com/api/character/1";
        private static final String VALUE_CREATED = "2017-11-04T18:48:46.250Z";

        public static String CHARACTER_DETAILS_JSON = "{\n" +
                "  \"id\": " + VALUE_ID + ",\n" +
                "  \"name\": \"" + VALUE_NAME + "\",\n" +
                "  \"status\": \"" + VALUE_STATUS + "\",\n" +
                "  \"species\": \"" + VALUE_SPECIES + "\",\n" +
                "  \"type\": \"" + VALUE_TYPE + "\",\n" +
                "  \"gender\": \"" + VALUE_GENDER + "\",\n" +
                "  \"origin\": {\n" +
                "    \"name\": \"" + VALUE_ORIGIN_NAME + "\",\n" +
                "    \"url\": \"" + VALUE_ORIGIN_URL + "\"\n" +
                "  },\n" +
                "  \"location\": {\n" +
                "    \"name\": \"" + VALUE_LOCATION_NAME + "\",\n" +
                "    \"url\": \"" + VALUE_LOCATION_URL + "\"\n" +
                "  },\n" +
                "  \"image\": \"" + VALUE_IMAGE + "\",\n" +
                "  \"episode\": [\n" +
                "    \"" + VALUE_EPISODE + "\"\n" +
                "  ],\n" +
                "  \"url\": \"" + VALUE_URL + "\",\n" +
                "  \"created\": \"" + VALUE_CREATED + "\"\n" +
                "}";

        public static CharacterModel characterResponse() {
            OriginModel origin = new OriginModel(VALUE_ORIGIN_NAME, VALUE_ORIGIN_URL);
            LocationModel location = new LocationModel(VALUE_LOCATION_NAME, VALUE_LOCATION_URL);

            List<String> episodes = new ArrayList<>();
            episodes.add(VALUE_EPISODE);

            CharacterModel character = new CharacterModel(VALUE_ID, VALUE_NAME, VALUE_STATUS, VALUE_SPECIES, VALUE_TYPE, VALUE_GENDER, origin, location, VALUE_IMAGE, episodes, VALUE_URL, VALUE_CREATED);

            return character;
        }
    }
}
