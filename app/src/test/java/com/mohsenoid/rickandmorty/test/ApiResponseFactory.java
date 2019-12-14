package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseFactory {

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
