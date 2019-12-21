package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel

object NetworkResponseFactory {

    object Episode {

        private const val VALUE_ID = 1
        private const val VALUE_NAME = "Pilot"
        private const val VALUE_AIR_DATE = "December 2, 2013"
        private const val VALUE_EPISODE = "S01E01"
        private const val VALUE_CHARACTER = "https://rickandmortyapi.com/api/character/1"
        private const val VALUE_URL = "https://rickandmortyapi.com/api/episode/1"
        private const val VALUE_CREATED = "2017-11-10T12:56:33.798Z"

        const val EPISODES_JSON = "{\n" +
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
                "}"

        fun episodesResponse(): List<NetworkEpisodeModel> {
            val episode = NetworkEpisodeModel(
                id = VALUE_ID,
                name = VALUE_NAME,
                airDate = VALUE_AIR_DATE,
                episode = VALUE_EPISODE,
                characters = arrayListOf(VALUE_CHARACTER),
                url = VALUE_URL,
                created = VALUE_CREATED
            )

            return arrayListOf(episode)
        }
    }

    object Characters {

        const val CHARACTERS_JSON = "[\n  ${CharacterDetails.CHARACTER_DETAILS_JSON}\n]"

        fun charactersResponse(): List<NetworkCharacterModel> {
            val character = CharacterDetails.characterResponse()
            return arrayListOf(character)
        }
    }

    object CharacterDetails {

        private const val VALUE_ID = 1
        private const val VALUE_NAME = "Rick Sanchez"
        private const val VALUE_STATUS = "Alive"
        private const val VALUE_SPECIES = "Human"
        private const val VALUE_TYPE = ""
        private const val VALUE_GENDER = "Male"
        private const val VALUE_ORIGIN_NAME = "Earth (C-137)"
        private const val VALUE_ORIGIN_URL = "https://rickandmortyapi.com/api/location/1"
        private const val VALUE_LOCATION_NAME = "Earth (Replacement Dimension)"
        private const val VALUE_LOCATION_URL = "https://rickandmortyapi.com/api/location/20"
        private const val VALUE_IMAGE = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        private const val VALUE_EPISODE = "https://rickandmortyapi.com/api/episode/1"
        private const val VALUE_URL = "https://rickandmortyapi.com/api/character/1"
        private const val VALUE_CREATED = "2017-11-04T18:48:46.250Z"

        const val CHARACTER_DETAILS_JSON = "{\n" +
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
                "}"

        fun characterResponse(): NetworkCharacterModel {
            val origin = NetworkOriginModel(
                name = VALUE_ORIGIN_NAME,
                url = VALUE_ORIGIN_URL
            )

            val location = NetworkLocationModel(
                name = VALUE_LOCATION_NAME,
                url = VALUE_LOCATION_URL
            )

            return NetworkCharacterModel(
                id = VALUE_ID,
                name = VALUE_NAME,
                status = VALUE_STATUS,
                species = VALUE_SPECIES,
                type = VALUE_TYPE,
                gender = VALUE_GENDER,
                origin = origin,
                location = location,
                image = VALUE_IMAGE,
                episodes = arrayListOf(VALUE_EPISODE),
                url = VALUE_URL,
                created = VALUE_CREATED
            )
        }
    }
}
