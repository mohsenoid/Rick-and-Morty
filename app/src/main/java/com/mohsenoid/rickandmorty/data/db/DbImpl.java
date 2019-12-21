package com.mohsenoid.rickandmorty.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.VisibleForTesting;

import com.mohsenoid.rickandmorty.data.Serializer;
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel;
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel;
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel;
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel;
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DbImpl extends SQLiteOpenHelper implements Db {

    @VisibleForTesting
    public static DbImpl instance;

    private final SQLiteDatabase db;

    private DbImpl(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);
        db = getWritableDatabase();
    }

    public static synchronized DbImpl getInstance(Context context) {
        if (instance == null)
            instance = new DbImpl(context);

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
                        DbConstants.Episode.TABLE_NAME,
                        DbConstants.Episode.ID,
                        DbConstants.Episode.NAME,
                        DbConstants.Episode.AIR_DATE,
                        DbConstants.Episode.EPISODE,
                        DbConstants.Episode.CHARACTER_IDS,
                        DbConstants.Episode.URL,
                        DbConstants.Episode.CREATED);
        db.execSQL(sql);
    }

    synchronized private void createCharacterTable(SQLiteDatabase db) {
        String sql = String
                .format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                                "%s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER DEFAULT 0)",
                        DbConstants.Character.TABLE_NAME,
                        DbConstants.Character.ID,
                        DbConstants.Character.NAME,
                        DbConstants.Character.STATUS,
                        DbConstants.Character.IS_STATUS_ALIVE,
                        DbConstants.Character.SPECIES,
                        DbConstants.Character.TYPE,
                        DbConstants.Character.GENDER,
                        DbConstants.Character.ORIGIN_NAME,
                        DbConstants.Character.ORIGIN_URL,
                        DbConstants.Character.LOCATION_NAME,
                        DbConstants.Character.LOCATION_URL,
                        DbConstants.Character.IMAGE,
                        DbConstants.Character.EPISODE,
                        DbConstants.Character.URL,
                        DbConstants.Character.CREATED,
                        DbConstants.Character.KILLED_BY_USER);
        db.execSQL(sql);
    }

    @Override
    synchronized public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropEpisodeTable(db);
        dropCharacterTable(db);

        onCreate(db);
    }

    synchronized private void dropEpisodeTable(SQLiteDatabase db) {
        String sql = String.format("DROP TABLE IF EXISTS %s", DbConstants.Episode.TABLE_NAME);
        db.execSQL(sql);
    }

    synchronized private void dropCharacterTable(SQLiteDatabase db) {
        String sql = String.format("DROP TABLE IF EXISTS %s", DbConstants.Character.TABLE_NAME);
        db.execSQL(sql);
    }

    @Override
    synchronized public void insertEpisode(DbEpisodeModel episode) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.Episode.ID, episode.getId());
        contentValues.put(DbConstants.Episode.NAME, episode.getName());
        contentValues.put(DbConstants.Episode.AIR_DATE, episode.getAirDate());
        contentValues.put(DbConstants.Episode.EPISODE, episode.getEpisode());
        contentValues.put(DbConstants.Episode.CHARACTER_IDS, episode.getSerializedCharacterIds());
        contentValues.put(DbConstants.Episode.URL, episode.getUrl());
        contentValues.put(DbConstants.Episode.CREATED, episode.getCreated());

        Cursor selectEpisodeCursor = getSelectEpisodeCursor(episode.getId());
        if (selectEpisodeCursor.moveToFirst()) {
            String whereClause = DbConstants.Episode.ID + "=?";
            String[] args = {selectEpisodeCursor.getInt(selectEpisodeCursor.getColumnIndexOrThrow(DbConstants.Episode.ID)) + ""};
            db.update(DbConstants.Episode.TABLE_NAME, contentValues, whereClause, args);
        } else {
            db.insert(DbConstants.Episode.TABLE_NAME, DbConstants.Episode.ID, contentValues);
        }
    }

    synchronized private Cursor getSelectEpisodeCursor(int episodeId) {
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = %s",
                DbConstants.Episode.TABLE_NAME,
                DbConstants.Episode.ID,
                episodeId);
        return db.rawQuery(sql, null);
    }

    @Override
    synchronized public List<DbEpisodeModel> queryAllEpisodes(int page) {
        List<DbEpisodeModel> episodes = new ArrayList<>();

        String sql = String.format("SELECT * FROM %s LIMIT %s OFFSET %s", DbConstants.Episode.TABLE_NAME, DbConstants.PAGE_SIZE, calculatePageOffset(page));
        try (Cursor cursor = db.rawQuery(sql, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Episode.ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.NAME));
                String airDate = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.AIR_DATE));
                String episode = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.EPISODE));
                String characterIds = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.CHARACTER_IDS));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.URL));
                String created = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.CREATED));

                DbEpisodeModel newEpisode = new DbEpisodeModel(id, name, airDate, episode, characterIds, url, created);
                episodes.add(newEpisode);
            }
        }

        return episodes;
    }

    @Override
    synchronized public void insertCharacter(DbCharacterModel character) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.Character.ID, character.getId());
        contentValues.put(DbConstants.Character.NAME, character.getName());
        contentValues.put(DbConstants.Character.STATUS, character.getStatus());
        contentValues.put(DbConstants.Character.IS_STATUS_ALIVE, character.getStatusAlive());
        contentValues.put(DbConstants.Character.SPECIES, character.getSpecies());
        contentValues.put(DbConstants.Character.TYPE, character.getType());
        contentValues.put(DbConstants.Character.GENDER, character.getGender());
        contentValues.put(DbConstants.Character.ORIGIN_NAME, character.getOrigin().getName());
        contentValues.put(DbConstants.Character.ORIGIN_URL, character.getOrigin().getUrl());
        contentValues.put(DbConstants.Character.LOCATION_NAME, character.getLocation().getName());
        contentValues.put(DbConstants.Character.LOCATION_URL, character.getLocation().getUrl());
        contentValues.put(DbConstants.Character.IMAGE, character.getImage());
        contentValues.put(DbConstants.Character.EPISODE, character.getSerializedEpisodes());
        contentValues.put(DbConstants.Character.URL, character.getUrl());
        contentValues.put(DbConstants.Character.CREATED, character.getCreated());

        Cursor selectEpisodeCursor = getSelectCharacterCursor(character.getId());
        if (selectEpisodeCursor.moveToFirst()) {
            String whereClause = DbConstants.Character.ID + "=?";
            String[] args = {selectEpisodeCursor.getInt(selectEpisodeCursor.getColumnIndexOrThrow(DbConstants.Episode.ID)) + ""};
            db.update(DbConstants.Character.TABLE_NAME, contentValues, whereClause, args);
        } else {
            db.insert(DbConstants.Character.TABLE_NAME, DbConstants.Character.ID, contentValues);
        }
    }

    synchronized private Cursor getSelectCharacterCursor(int characterId) {
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = %s",
                DbConstants.Character.TABLE_NAME,
                DbConstants.Character.ID,
                characterId);
        return db.rawQuery(sql, null);
    }

    @Override
    synchronized public List<DbCharacterModel> queryCharactersByIds(List<Integer> characterIds) {
        if (characterIds == null) return null;

        List<DbCharacterModel> characters = new ArrayList<>();
        if (characterIds.size() == 0) return characters;

        String sql = String.format("SELECT * FROM %s WHERE %s IN (%s)", DbConstants.Character.TABLE_NAME, DbConstants.Character.ID, Serializer.serializeIntegerList(characterIds));
        try (Cursor cursor = db.rawQuery(sql, null)) {
            while (cursor.moveToNext()) {
                DbCharacterModel newCharacter = getCursorCharacterDetails(cursor);
                characters.add(newCharacter);
            }
        }

        return characters;
    }

    private int calculatePageOffset(int page) {
        return (page - 1) * DbConstants.PAGE_SIZE;
    }

    @Override
    synchronized public DbCharacterModel queryCharacter(int characterId) {
        DbCharacterModel character = null;

        try (Cursor cursor = getSelectCharacterCursor(characterId)) {
            if (cursor.moveToFirst()) {
                character = getCursorCharacterDetails(cursor);
            }
        }

        return character;
    }

    @NotNull
    private DbCharacterModel getCursorCharacterDetails(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Character.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.NAME));
        String status = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.STATUS));
        boolean isStatusAlive = cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Character.IS_STATUS_ALIVE)) == 1;
        String species = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.SPECIES));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.TYPE));
        String gender = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.GENDER));
        String originName = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.ORIGIN_NAME));
        String originUrl = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.ORIGIN_URL));
        String locationName = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.LOCATION_NAME));
        String locationUrl = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.LOCATION_URL));
        String image = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.IMAGE));
        String episode = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.EPISODE));
        String url = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.URL));
        String created = cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.CREATED));
        boolean isKilledByUser = cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Character.KILLED_BY_USER)) == 1;

        DbOriginModel characterOrigin = new DbOriginModel(originName, originUrl);
        DbLocationModel characterLocation = new DbLocationModel(locationName, locationUrl);

        return new DbCharacterModel(id, name, status, isStatusAlive, species, type, gender, characterOrigin, characterLocation, image, episode, url, created, isKilledByUser);
    }

    @Override
    public void killCharacter(int characterId) throws NoOfflineDataException {
        Cursor selectEpisodeCursor = getSelectCharacterCursor(characterId);
        if (selectEpisodeCursor.moveToFirst()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbConstants.Character.KILLED_BY_USER, 1);

            String whereClause = DbConstants.Character.ID + "=?";
            String[] args = {selectEpisodeCursor.getInt(selectEpisodeCursor.getColumnIndexOrThrow(DbConstants.Episode.ID)) + ""};
            db.update(DbConstants.Character.TABLE_NAME, contentValues, whereClause, args);
        } else {
            throw new NoOfflineDataException();
        }
    }
}
