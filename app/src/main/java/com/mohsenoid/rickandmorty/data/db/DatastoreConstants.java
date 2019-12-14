package com.mohsenoid.rickandmorty.data.db;

class DatastoreConstants {

    static final String DATABASE_NAME = "rickandmorty.db";
    static final int DATABASE_VERSION = 1;

    private DatastoreConstants() { /* this will prevent making a new object of this type */}

    class Episode {
        static final String TABLE_NAME = "episodes";
        static final String ID = "_id";
        static final String NAME = "name";
        static final String AIR_DATE = "air_date";
        static final String EPISODE = "episode";
        static final String CHARACTERS = "characters";
        static final String URL = "url";
        static final String CREATED = "created";
    }
}
