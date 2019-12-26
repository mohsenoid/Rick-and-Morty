package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import com.mohsenoid.rickandmorty.data.network.dto.NetworkInfoModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import retrofit2.Response

object NetworkResponseFactory {

    object Episode {
        private const val VALUE_COUNT = 1
        private const val VALUE_PAGE = 1
        private const val VALUE_NEXT = ""
        private const val VALUE_PREV = ""

        private const val VALUE_ID = 1
        private const val VALUE_NAME = "Pilot"
        private const val VALUE_AIR_DATE = "December 2, 2013"
        private const val VALUE_EPISODE = "S01E01"
        private const val VALUE_CHARACTER = "https://rickandmortyapi.com/api/character/1"
        private const val VALUE_URL = "https://rickandmortyapi.com/api/episode/1"
        private const val VALUE_CREATED = "2017-11-10T12:56:33.798Z"

        const val EPISODES_JSON = """{
  "info": {
    "count": $VALUE_COUNT,
    "pages": $VALUE_PAGE,
    "next": $VALUE_NEXT,
    "prev": $VALUE_PREV
  },
  "results": [
    {
      "id": $VALUE_ID,
      "name": $VALUE_NAME,
      "air_date": $VALUE_AIR_DATE,
      "episode": $VALUE_EPISODE,
      "characters": [
        $VALUE_CHARACTER
      ],
      "url": $VALUE_URL,
      "created": $VALUE_CREATED
    }
  ]
}"""

        fun episodesResponse(): Response<NetworkEpisodesResponse> {
            val info = NetworkInfoModel(
                count = VALUE_COUNT,
                pages = VALUE_PAGE,
                next = VALUE_NEXT,
                prev = VALUE_PREV
            )

            val episode = NetworkEpisodeModel(
                id = VALUE_ID,
                name = VALUE_NAME,
                airDate = VALUE_AIR_DATE,
                episode = VALUE_EPISODE,
                characters = arrayListOf(VALUE_CHARACTER),
                url = VALUE_URL,
                created = VALUE_CREATED
            )

            return Response.success(
                NetworkEpisodesResponse(
                    info = info,
                    results = arrayListOf(episode)
                )
            )
        }
    }

    object Characters {

        const val CHARACTERS_JSON = "[\n  ${CharacterDetails.CHARACTER_DETAILS_JSON}\n]"

        fun charactersResponse(): Response<List<NetworkCharacterModel>> {
            val character = CharacterDetails.character()
            return Response.success(arrayListOf(character))
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

        const val CHARACTER_DETAILS_JSON = """{
  "id": $VALUE_ID,
  "name": "$VALUE_NAME",
  "status": "$VALUE_STATUS",
  "species": "$VALUE_SPECIES",
  "type": "$VALUE_TYPE",
  "gender": "$VALUE_GENDER",
  "origin": {
    "name": "$VALUE_ORIGIN_NAME",
    "url": "$VALUE_ORIGIN_URL"
  },
  "location": {
    "name": "$VALUE_LOCATION_NAME",
    "url": "$VALUE_LOCATION_URL"
  },
  "image": "$VALUE_IMAGE",
  "episode": [
    "$VALUE_EPISODE"
  ],
  "url": "$VALUE_URL",
  "created": "$VALUE_CREATED"
}"""

        fun character(): NetworkCharacterModel {
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

        fun characterResponse(): Response<NetworkCharacterModel> {
            return Response.success(character())
        }
    }
}
