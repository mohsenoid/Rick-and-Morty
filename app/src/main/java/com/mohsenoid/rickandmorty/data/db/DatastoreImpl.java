package com.mohsenoid.rickandmorty.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.VisibleForTesting;

import com.mohsenoid.rickandmorty.data.Serializer;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.model.LocationModel;
import com.mohsenoid.rickandmorty.model.OriginModel;

import java.util.ArrayList;
import java.util.List;

public class DatastoreImpl extends SQLiteOpenHelper implements Datastore {

    @VisibleForTesting
    public static DatastoreImpl instance;

    private SQLiteDatabase db;

    private DatastoreImpl(Context context) {
        super(context, DatastoreConstants.DATABASE_NAME, null, DatastoreConstants.DATABASE_VERSION);
        db = getWritableDatabase();
    }

    public static DatastoreImpl getInstance(Context context) {
        if (instance == null)
            instance = new DatastoreImpl(context);

        return instance;
    }

    @Override
    synchronized public void onCreate(SQLiteDatabase db) {
        createEpisodeTable(db);
        createCharacterTable(db);
    }

    synchronized private void createEpisodeTable(SQLiteDatabase db) {
        String sql = String
                .format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                                "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                        DatastoreConstants.Episode.TABLE_NAME,
                        DatastoreConstants.Episode.ID,
                        DatastoreConstants.Episode.NAME,
                        DatastoreConstants.Episode.AIR_DATE,
                        DatastoreConstants.Episode.EPISODE,
                        DatastoreConstants.Episode.CHARACTERS,
                        DatastoreConstants.Episode.URL,
                        DatastoreConstants.Episode.CREATED);
        db.execSQL(sql);
    }

    synchronized private void createCharacterTable(SQLiteDatabase db) {
        String sql = String
                .format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                                "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                        DatastoreConstants.Character.TABLE_NAME,
                        DatastoreConstants.Character.ID,
                        DatastoreConstants.Character.NAME,
                        DatastoreConstants.Character.STATUS,
                        DatastoreConstants.Character.SPECIES,
                        DatastoreConstants.Character.TYPE,
                        DatastoreConstants.Character.GENDER,
                        DatastoreConstants.Character.ORIGIN_NAME,
                        DatastoreConstants.Character.ORIGIN_URL,
                        DatastoreConstants.Character.LOCATION_NAME,
                        DatastoreConstants.Character.LOCATION_URL,
                        DatastoreConstants.Character.IMAGE,
                        DatastoreConstants.Character.EPISODE,
                        DatastoreConstants.Character.URL,
                        DatastoreConstants.Character.CREATED);
        db.execSQL(sql);
    }

    @Override
    synchronized public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropEpisodeTable(db);
        dropCharacterTable(db);

        onCreate(db);
    }

    synchronized private void dropEpisodeTable(SQLiteDatabase db) {
        String sql = String.format("DROP TABLE IF EXISTS %s", DatastoreConstants.Episode.TABLE_NAME);
        db.execSQL(sql);
    }

    synchronized private void dropCharacterTable(SQLiteDatabase db) {
        String sql = String.format("DROP TABLE IF EXISTS %s", DatastoreConstants.Character.TABLE_NAME);
        db.execSQL(sql);
    }

    @Override
    synchronized public void insertEpisode(EpisodeModel episode) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatastoreConstants.Episode.ID, episode.getId());
        contentValues.put(DatastoreConstants.Episode.NAME, episode.getName());
        contentValues.put(DatastoreConstants.Episode.AIR_DATE, episode.getAirDate());
        contentValues.put(DatastoreConstants.Episode.EPISODE, episode.getEpisode());
        contentValues.put(DatastoreConstants.Episode.CHARACTERS, episode.getSerializedCharacters());
        contentValues.put(DatastoreConstants.Episode.URL, episode.getUrl());
        contentValues.put(DatastoreConstants.Episode.CREATED, episode.getCreated());

        Cursor selectEpisodeCursor = getSelectEpisodeCursor(episode.getId());
        if (selectEpisodeCursor.moveToFirst()) {
            String whereClause = DatastoreConstants.Episode.ID + "=?";
            String[] args = {selectEpisodeCursor.getInt(selectEpisodeCursor.getColumnIndexOrThrow(DatastoreConstants.Episode.ID)) + ""};
            db.update(DatastoreConstants.Episode.TABLE_NAME, contentValues, whereClause, args);
        } else {
            db.insert(DatastoreConstants.Episode.TABLE_NAME, DatastoreConstants.Episode.ID, contentValues);
        }
    }

    synchronized private Cursor getSelectEpisodeCursor(int episodeId) {
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = %s",
                DatastoreConstants.Episode.TABLE_NAME,
                DatastoreConstants.Episode.ID,
                episodeId);
        return db.rawQuery(sql, null);
    }

    @Override
    synchronized public List<EpisodeModel> queryAllEpisodes(int page) {
        List<EpisodeModel> episodes = new ArrayList<>();

        String sql = String.format("SELECT * FROM %s LIMIT %s OFFSET %s", DatastoreConstants.Episode.TABLE_NAME, DatastoreConstants.PAGE_SIZE, calculatePageOffset(page));
        try (Cursor cursor = db.rawQuery(sql, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatastoreConstants.Episode.ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Episode.NAME));
                String airDate = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Episode.AIR_DATE));
                String episode = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Episode.EPISODE));
                String characters = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Episode.CHARACTERS));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Episode.URL));
                String created = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Episode.CREATED));

                EpisodeModel newEpisode = new EpisodeModel(id, name, airDate, episode, characters, url, created);
                episodes.add(newEpisode);
            }
        }

        return episodes;
    }

    @Override
    synchronized public void insertCharacter(CharacterModel character) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatastoreConstants.Character.ID, character.getId());
        contentValues.put(DatastoreConstants.Character.NAME, character.getName());
        contentValues.put(DatastoreConstants.Character.STATUS, character.getStatus());
        contentValues.put(DatastoreConstants.Character.SPECIES, character.getSpecies());
        contentValues.put(DatastoreConstants.Character.TYPE, character.getType());
        contentValues.put(DatastoreConstants.Character.GENDER, character.getGender());
        contentValues.put(DatastoreConstants.Character.ORIGIN_NAME, character.getOrigin().getName());
        contentValues.put(DatastoreConstants.Character.ORIGIN_URL, character.getOrigin().getUrl());
        contentValues.put(DatastoreConstants.Character.LOCATION_NAME, character.getLocation().getName());
        contentValues.put(DatastoreConstants.Character.LOCATION_URL, character.getLocation().getUrl());
        contentValues.put(DatastoreConstants.Character.IMAGE, character.getImage());
        contentValues.put(DatastoreConstants.Character.EPISODE, character.getSerializedEpisodes());
        contentValues.put(DatastoreConstants.Character.URL, character.getUrl());
        contentValues.put(DatastoreConstants.Character.CREATED, character.getCreated());

        Cursor selectEpisodeCursor = getSelectCharacterCursor(character.getId());
        if (selectEpisodeCursor.moveToFirst()) {
            String whereClause = DatastoreConstants.Character.ID + "=?";
            String[] args = {selectEpisodeCursor.getInt(selectEpisodeCursor.getColumnIndexOrThrow(DatastoreConstants.Episode.ID)) + ""};
            db.update(DatastoreConstants.Character.TABLE_NAME, contentValues, whereClause, args);
        } else {
            db.insert(DatastoreConstants.Character.TABLE_NAME, DatastoreConstants.Character.ID, contentValues);
        }
    }

    synchronized private Cursor getSelectCharacterCursor(int characterId) {
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = %s",
                DatastoreConstants.Character.TABLE_NAME,
                DatastoreConstants.Character.ID,
                characterId);
        return db.rawQuery(sql, null);
    }

    @Override
    synchronized public List<CharacterModel> queryAllCharacters(List<Integer> characterIds) {
        if (characterIds == null) return null;

        List<CharacterModel> characters = new ArrayList<>();
        if (characterIds.size() == 0) return characters;

        String sql = String.format("SELECT * FROM %s WHERE %s IN (%s)", DatastoreConstants.Character.TABLE_NAME, DatastoreConstants.Character.ID, Serializer.serializeIntegerList(characterIds));
        try (Cursor cursor = db.rawQuery(sql, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.NAME));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.STATUS));
                String species = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.SPECIES));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.TYPE));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.GENDER));
                String originName = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.ORIGIN_NAME));
                String originUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.ORIGIN_URL));
                String locationName = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.LOCATION_NAME));
                String locationUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.LOCATION_URL));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.IMAGE));
                String episode = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.EPISODE));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.URL));
                String created = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.CREATED));

                OriginModel characterOrigin = new OriginModel(originName, originUrl);
                LocationModel characterLocation = new LocationModel(locationName, locationUrl);
                CharacterModel newCharacter = new CharacterModel(id, name, status, species, type, gender, characterOrigin, characterLocation, image, episode, url, created);
                characters.add(newCharacter);
            }
        }

        return characters;
    }

    private int calculatePageOffset(int page) {
        return (page - 1) * DatastoreConstants.PAGE_SIZE;
    }

    @Override
    synchronized public CharacterModel queryCharacter(int characterId) {
        CharacterModel character = null;

        try (Cursor cursor = getSelectCharacterCursor(characterId)) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.NAME));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.STATUS));
                String species = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.SPECIES));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.TYPE));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.GENDER));
                String originName = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.ORIGIN_NAME));
                String originUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.ORIGIN_URL));
                String locationName = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.LOCATION_NAME));
                String locationUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.LOCATION_URL));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.IMAGE));
                String episode = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.EPISODE));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.URL));
                String created = cursor.getString(cursor.getColumnIndexOrThrow(DatastoreConstants.Character.CREATED));

                OriginModel characterOrigin = new OriginModel(originName, originUrl);
                LocationModel characterLocation = new LocationModel(locationName, locationUrl);
                character = new CharacterModel(id, name, status, species, type, gender, characterOrigin, characterLocation, image, episode, url, created);
            }
        }

        return character;
    }
}
