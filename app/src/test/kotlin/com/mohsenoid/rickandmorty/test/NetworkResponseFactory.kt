package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.api.model.ApiEpisodes
import com.mohsenoid.rickandmorty.data.api.model.ApiInfo
import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import retrofit2.Response

object NetworkResponseFactory {

    object Episode {
        private const val VALUE_COUNT: Int = 1
        private const val VALUE_PAGE: Int = 1
        private const val VALUE_NEXT: String = ""
        private const val VALUE_PREV: String = ""

        private const val VALUE_ID: Int = 1
        private const val VALUE_NAME: String = "Pilot"
        private const val VALUE_AIR_DATE: String = "December 2, 2013"
        private const val VALUE_EPISODE: String = "S01E01"
        private const val VALUE_CHARACTER: String = "https://rickandmortyapi.com/api/character/1"
        private const val VALUE_URL: String = "https://rickandmortyapi.com/api/episode/1"
        private const val VALUE_CREATED: String = "2017-11-10T12:56:33.798Z"

        const val EPISODES_JSON: String = """{
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

        fun episodesResponse(): Response<ApiEpisodes> {
            val info = ApiInfo(
                count = VALUE_COUNT,
                pages = VALUE_PAGE,
                next = VALUE_NEXT,
                prev = VALUE_PREV
            )

            val episode = ApiEpisode(
                id = VALUE_ID,
                name = VALUE_NAME,
                airDate = VALUE_AIR_DATE,
                episode = VALUE_EPISODE,
                characters = arrayListOf(VALUE_CHARACTER),
                url = VALUE_URL,
                created = VALUE_CREATED
            )

            return Response.success(
                ApiEpisodes(
                    info = info,
                    results = arrayListOf(episode)
                )
            )
        }
    }

    object Characters {

        const val CHARACTERS_JSON: String = "[\n  ${CharacterDetails.CHARACTER_DETAILS_JSON}\n]"

        fun charactersResponse(): Response<List<ApiCharacter>> {
            val character: ApiCharacter = CharacterDetails.character()
            return Response.success(arrayListOf(character))
        }
    }

    object CharacterDetails {

        private const val VALUE_ID: Int = 1
        private const val VALUE_NAME: String = "Rick Sanchez"
        private const val VALUE_STATUS: String = "Alive"
        private const val VALUE_SPECIES: String = "Human"
        private const val VALUE_TYPE: String = ""
        private const val VALUE_GENDER: String = "Male"
        private const val VALUE_ORIGIN_NAME: String = "Earth (C-137)"
        private const val VALUE_ORIGIN_URL: String = "https://rickandmortyapi.com/api/location/1"
        private const val VALUE_LOCATION_NAME: String = "Earth (Replacement Dimension)"
        private const val VALUE_LOCATION_URL: String = "https://rickandmortyapi.com/api/location/20"
        private const val VALUE_IMAGE: String =
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        private const val VALUE_EPISODE: String = "https://rickandmortyapi.com/api/episode/1"
        private const val VALUE_URL: String = "https://rickandmortyapi.com/api/character/1"
        private const val VALUE_CREATED: String = "2017-11-04T18:48:46.250Z"

        const val CHARACTER_DETAILS_JSON: String = """{
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

        fun character(): ApiCharacter {
            val origin = ApiOrigin(
                name = VALUE_ORIGIN_NAME,
                url = VALUE_ORIGIN_URL
            )

            val location = ApiLocation(
                name = VALUE_LOCATION_NAME,
                url = VALUE_LOCATION_URL
            )

            return ApiCharacter(
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

        fun characterResponse(): Response<ApiCharacter> {
            return Response.success(character())
        }
    }
}
