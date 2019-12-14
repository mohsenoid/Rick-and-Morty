package com.mohsenoid.rickandmorty.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

public class DatastoreImpl extends SQLiteOpenHelper implements Datastore {

    private static SQLiteDatabase db;

    DatastoreImpl(Context context) {
        super(context, DatastoreConstants.DATABASE_NAME, null, DatastoreConstants.DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEpisodeTable(db);
    }

    private void createEpisodeTable(SQLiteDatabase db) {
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropEpisodeTable(db);

        onCreate(db);
    }

    private void dropEpisodeTable(SQLiteDatabase db) {
        String sql = String.format("DROP TABLE IF EXISTS %s", DatastoreConstants.Episode.TABLE_NAME);
        db.execSQL(sql);
    }

    @Override
    public void insertEpisode(EpisodeModel episode) {
        try {
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
                String[] args = {selectEpisodeCursor.getInt(selectEpisodeCursor.getColumnIndex(DatastoreConstants.Episode.ID)) + ""};
                db.update(DatastoreConstants.Episode.TABLE_NAME, contentValues, whereClause, args);
            } else {
                db.insert(DatastoreConstants.Episode.TABLE_NAME, DatastoreConstants.Episode.ID, contentValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Cursor getSelectEpisodeCursor(int episodeId) {
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = %s",
                DatastoreConstants.Episode.TABLE_NAME,
                DatastoreConstants.Episode.ID,
                episodeId);
        return db.rawQuery(sql, null);
    }

    @Override
    public List<EpisodeModel> queryAllEpisodes() {
        List<EpisodeModel> episodes = new ArrayList<>();

        String sql = String.format("SELECT * FROM %s", DatastoreConstants.Episode.TABLE_NAME);
        try (Cursor cursor = db.rawQuery(sql, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatastoreConstants.Episode.ID));
                String name = cursor.getString(cursor.getColumnIndex(DatastoreConstants.Episode.NAME));
                String airDate = cursor.getString(cursor.getColumnIndex(DatastoreConstants.Episode.AIR_DATE));
                String episode = cursor.getString(cursor.getColumnIndex(DatastoreConstants.Episode.EPISODE));
                String characters = cursor.getString(cursor.getColumnIndex(DatastoreConstants.Episode.CHARACTERS));
                String url = cursor.getString(cursor.getColumnIndex(DatastoreConstants.Episode.URL));
                String created = cursor.getString(cursor.getColumnIndex(DatastoreConstants.Episode.CREATED));

                EpisodeModel newEpisode = new EpisodeModel(id, name, airDate, episode, characters, url, created);
                episodes.add(newEpisode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return episodes;
    }
}
