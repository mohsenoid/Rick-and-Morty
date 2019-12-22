package com.mohsenoid.rickandmorty.data.db

internal object DbConstants {

    const val DATABASE_NAME = "rickandmorty.db"
    const val DATABASE_VERSION = 2

    const val PAGE_SIZE = 20

    internal object Episode {
        const val TABLE_NAME = "episodes"
        const val ID = "_id"
        const val NAME = "name"
        const val AIR_DATE = "air_date"
        const val EPISODE = "episode"
        const val CHARACTER_IDS = "character_ids"
        const val URL = "url"
        const val CREATED = "created"
    }

    internal object Character {
        const val TABLE_NAME = "characters"
        const val ID = "_id"
        const val NAME = "name"
        const val STATUS = "status"
        const val IS_STATUS_ALIVE = "is_status_alive"
        const val SPECIES = "species"
        const val TYPE = "type"
        const val GENDER = "gender"
        const val ORIGIN_NAME = "origin_name"
        const val ORIGIN_URL = "origin_url"
        const val LOCATION_NAME = "location_name"
        const val LOCATION_URL = "location_url"
        const val IMAGE = "image"
        const val EPISODE = "episode"
        const val URL = "url"
        const val CREATED = "created"
        const val KILLED_BY_USER = "killed_by_user"
    }
}
