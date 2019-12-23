package com.mohsenoid.rickandmorty.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mohsenoid.rickandmorty.data.Serializer
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import java.util.ArrayList

class DbImpl(context: Context) :
    SQLiteOpenHelper(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION), Db {

    private val db: SQLiteDatabase = writableDatabase

    @Synchronized
    override fun onCreate(db: SQLiteDatabase) {
        createEpisodeTable(db)
        createCharacterTable(db)
    }

    @Synchronized
    private fun createEpisodeTable(db: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS ${DbConstants.Episode.TABLE_NAME} " +
            "(" +
            "${DbConstants.Episode.ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "${DbConstants.Episode.NAME} TEXT, " +
            "${DbConstants.Episode.AIR_DATE} TEXT, " +
            "${DbConstants.Episode.EPISODE} TEXT, " +
            "${DbConstants.Episode.CHARACTER_IDS} TEXT, " +
            "${DbConstants.Episode.URL} TEXT, " +
            "${DbConstants.Episode.CREATED} TEXT" +
            ")"
        db.execSQL(sql)
    }

    @Synchronized
    private fun createCharacterTable(db: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS ${DbConstants.Character.TABLE_NAME} " +
            "(" +
            "${DbConstants.Character.ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "${DbConstants.Character.NAME} TEXT, " +
            "${DbConstants.Character.STATUS} TEXT, " +
            "${DbConstants.Character.IS_STATUS_ALIVE} INTEGER, " +
            "${DbConstants.Character.SPECIES} TEXT, " +
            "${DbConstants.Character.TYPE} TEXT, " +
            "${DbConstants.Character.GENDER} TEXT, " +
            "${DbConstants.Character.ORIGIN_NAME} TEXT, " +
            "${DbConstants.Character.ORIGIN_URL} TEXT, " +
            "${DbConstants.Character.LOCATION_NAME} TEXT, " +
            "${DbConstants.Character.LOCATION_URL} TEXT, " +
            "${DbConstants.Character.IMAGE} TEXT, " +
            "${DbConstants.Character.EPISODE} TEXT, " +
            "${DbConstants.Character.URL} TEXT, " +
            "${DbConstants.Character.CREATED} TEXT, " +
            "${DbConstants.Character.KILLED_BY_USER} INTEGER DEFAULT 0" +
            ")"
        db.execSQL(sql)
    }

    @Synchronized
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropTable(db, DbConstants.Episode.TABLE_NAME)
        dropTable(db, DbConstants.Character.TABLE_NAME)
        onCreate(db)
    }

    @Synchronized
    private fun dropTable(db: SQLiteDatabase, tableName: String) {
        val sql = "DROP TABLE IF EXISTS $tableName"
        db.execSQL(sql)
    }

    @Synchronized
    override fun insertEpisode(episode: DbEpisodeModel) {
        val contentValues = ContentValues()

        contentValues.put(DbConstants.Episode.ID, episode.id)
        contentValues.put(DbConstants.Episode.NAME, episode.name)
        contentValues.put(DbConstants.Episode.AIR_DATE, episode.airDate)
        contentValues.put(DbConstants.Episode.EPISODE, episode.episode)
        contentValues.put(DbConstants.Episode.CHARACTER_IDS, episode.serializedCharacterIds)
        contentValues.put(DbConstants.Episode.URL, episode.url)
        contentValues.put(DbConstants.Episode.CREATED, episode.created)

        val selectEpisodeCursor = getSelectEpisodeCursor(episode.id)
        if (selectEpisodeCursor.moveToFirst()) {
            val whereClause = "${DbConstants.Episode.ID}=?"
            val args = arrayOf(
                selectEpisodeCursor.getInt(
                    selectEpisodeCursor.getColumnIndexOrThrow(DbConstants.Episode.ID)
                ).toString() + ""
            )
            db.update(DbConstants.Episode.TABLE_NAME, contentValues, whereClause, args)
        } else {
            db.insert(DbConstants.Episode.TABLE_NAME, DbConstants.Episode.ID, contentValues)
        }
    }

    @Synchronized
    private fun getSelectEpisodeCursor(episodeId: Int): Cursor {
        val sql = "SELECT * FROM ${DbConstants.Episode.TABLE_NAME} " +
            "WHERE ${DbConstants.Episode.ID} = $episodeId"
        return db.rawQuery(sql, null)
    }

    @Synchronized
    override fun queryAllEpisodes(page: Int): List<DbEpisodeModel> {
        val episodes = ArrayList<DbEpisodeModel>()

        val sql = "SELECT * FROM ${DbConstants.Episode.TABLE_NAME} " +
            "LIMIT ${DbConstants.PAGE_SIZE} " +
            "OFFSET ${calculatePageOffset(page)}"

        db.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Episode.ID))
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.NAME))
                val airDate =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.AIR_DATE))
                val episode =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.EPISODE))
                val characterIds =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.CHARACTER_IDS))
                val url =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.URL))
                val created =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Episode.CREATED))

                val newEpisode = DbEpisodeModel(
                    id = id,
                    name = name,
                    airDate = airDate,
                    episode = episode,
                    serializedCharacterIds = characterIds,
                    url = url,
                    created = created
                )

                episodes.add(newEpisode)
            }
        }

        return episodes
    }

    @Synchronized
    override fun insertCharacter(character: DbCharacterModel) {
        val contentValues = ContentValues()

        contentValues.put(DbConstants.Character.ID, character.id)
        contentValues.put(DbConstants.Character.NAME, character.name)
        contentValues.put(DbConstants.Character.STATUS, character.status)
        contentValues.put(DbConstants.Character.IS_STATUS_ALIVE, character.statusAlive)
        contentValues.put(DbConstants.Character.SPECIES, character.species)
        contentValues.put(DbConstants.Character.TYPE, character.type)
        contentValues.put(DbConstants.Character.GENDER, character.gender)
        contentValues.put(DbConstants.Character.ORIGIN_NAME, character.origin.name)
        contentValues.put(DbConstants.Character.ORIGIN_URL, character.origin.url)
        contentValues.put(DbConstants.Character.LOCATION_NAME, character.location.name)
        contentValues.put(DbConstants.Character.LOCATION_URL, character.location.url)
        contentValues.put(DbConstants.Character.IMAGE, character.image)
        contentValues.put(DbConstants.Character.EPISODE, character.serializedEpisodes)
        contentValues.put(DbConstants.Character.URL, character.url)
        contentValues.put(DbConstants.Character.CREATED, character.created)

        val selectEpisodeCursor = getSelectCharacterCursor(character.id)
        if (selectEpisodeCursor.moveToFirst()) {
            val whereClause = "${DbConstants.Character.ID}=?"
            val args = arrayOf(
                selectEpisodeCursor.getInt(
                    selectEpisodeCursor.getColumnIndexOrThrow(DbConstants.Episode.ID)
                ).toString() + ""
            )
            db.update(DbConstants.Character.TABLE_NAME, contentValues, whereClause, args)
        } else {
            db.insert(DbConstants.Character.TABLE_NAME, DbConstants.Character.ID, contentValues)
        }
    }

    @Synchronized
    private fun getSelectCharacterCursor(characterId: Int): Cursor {
        val sql = "SELECT * FROM ${DbConstants.Character.TABLE_NAME} " +
            "WHERE ${DbConstants.Character.ID} = $characterId"
        return db.rawQuery(sql, null)
    }

    @Synchronized
    override fun queryCharactersByIds(characterIds: List<Int>): List<DbCharacterModel> {
        val characters = ArrayList<DbCharacterModel>()

        if (characterIds.isEmpty()) return characters

        val serializedCharacterIds = Serializer.serializeIntegerList(characterIds)
        val sql = "SELECT * FROM ${DbConstants.Character.TABLE_NAME} " +
            "WHERE ${DbConstants.Character.ID} IN ($serializedCharacterIds)"
        db.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val newCharacter = getCursorCharacterDetails(cursor)
                characters.add(newCharacter)
            }
        }
        return characters
    }

    private fun calculatePageOffset(page: Int): Int {
        return (page - 1) * DbConstants.PAGE_SIZE
    }

    @Synchronized
    override fun queryCharacter(characterId: Int): DbCharacterModel? {
        var character: DbCharacterModel? = null

        getSelectCharacterCursor(characterId).use { cursor ->
            if (cursor.moveToFirst()) {
                character = getCursorCharacterDetails(cursor)
            }
        }
        return character
    }

    private fun getCursorCharacterDetails(cursor: Cursor): DbCharacterModel {
        val id =
            cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Character.ID))
        val name =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.NAME))
        val status =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.STATUS))
        val isStatusAlive =
            cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Character.IS_STATUS_ALIVE)) == 1
        val species =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.SPECIES))
        val type =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.TYPE))
        val gender =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.GENDER))
        val originName =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.ORIGIN_NAME))
        val originUrl =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.ORIGIN_URL))
        val locationName =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.LOCATION_NAME))
        val locationUrl =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.LOCATION_URL))
        val image =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.IMAGE))
        val episode =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.EPISODE))
        val url =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.URL))
        val created =
            cursor.getString(cursor.getColumnIndexOrThrow(DbConstants.Character.CREATED))
        val isKilledByUser =
            cursor.getInt(cursor.getColumnIndexOrThrow(DbConstants.Character.KILLED_BY_USER)) == 1
        val characterOrigin =
            DbOriginModel(originName, originUrl)
        val characterLocation =
            DbLocationModel(locationName, locationUrl)

        return DbCharacterModel(
            id = id,
            name = name,
            status = status,
            statusAlive = isStatusAlive,
            species = species,
            type = type,
            gender = gender,
            origin = characterOrigin,
            location = characterLocation,
            image = image,
            serializedEpisodes = episode,
            url = url,
            created = created,
            killedByUser = isKilledByUser
        )
    }

    override fun killCharacter(characterId: Int) {
        val selectEpisodeCursor = getSelectCharacterCursor(characterId)

        if (selectEpisodeCursor.moveToFirst()) {
            val contentValues = ContentValues()
            contentValues.put(DbConstants.Character.KILLED_BY_USER, 1)
            val whereClause = "${DbConstants.Character.ID}=?"
            val args = arrayOf(
                selectEpisodeCursor.getInt(
                    selectEpisodeCursor.getColumnIndexOrThrow(DbConstants.Episode.ID)
                ).toString()
            )

            db.update(DbConstants.Character.TABLE_NAME, contentValues, whereClause, args)
        } else {
            throw NoOfflineDataException()
        }
    }
}
