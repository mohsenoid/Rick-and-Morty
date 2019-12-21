package com.mohsenoid.rickandmorty.data.db;

class DbConstants {

    static final String DATABASE_NAME = "rickandmorty.db";
    static final int DATABASE_VERSION = 2;

    static final int PAGE_SIZE = 20;

    private DbConstants() { /* this will prevent making a new object of this type */ }

    class Episode {
        static final String TABLE_NAME = "episodes";
        static final String ID = "_id";
        static final String NAME = "name";
        static final String AIR_DATE = "air_date";
        static final String EPISODE = "episode";
        static final String CHARACTER_IDS = "character_ids";
        static final String URL = "url";
        static final String CREATED = "created";
    }

    class Character {
        static final String TABLE_NAME = "characters";
        static final String ID = "_id";
        static final String NAME = "name";
        static final String STATUS = "status";
        static final String IS_STATUS_ALIVE = "is_status_alive";
        static final String SPECIES = "species";
        static final String TYPE = "type";
        static final String GENDER = "gender";
        static final String ORIGIN_NAME = "origin_name";
        static final String ORIGIN_URL = "origin_url";
        static final String LOCATION_NAME = "location_name";
        static final String LOCATION_URL = "location_url";
        static final String IMAGE = "image";
        static final String EPISODE = "episode";
        static final String URL = "url";
        static final String CREATED = "created";
        static final String KILLED_BY_USER = "killed_by_user";
    }
}
