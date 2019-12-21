package com.mohsenoid.rickandmorty.data.db;

import android.app.Application;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel;
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel;
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;
import com.mohsenoid.rickandmorty.data.mapper.CharacterMapper;
import com.mohsenoid.rickandmorty.test.CharacterDataFactory;
import com.mohsenoid.rickandmorty.test.DataFactory;
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class DbTest {

    private Db db;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        db = DbImpl.getInstance(application);
    }

    @After
    public void tearDown() {
        DbImpl.instance = null;
    }

    @Test
    public void testInsertEpisode() {
        // GIVEN
        DbEpisodeModel episode = EpisodeDataFactory.Db.makeDbEpisodeModel();

        // WHEN
        db.insertEpisode(episode);

        // THEN
        List<DbEpisodeModel> result = db.queryAllEpisodes(1);
        assertTrue(result.contains(episode));
    }

    @Test
    public void testUpdateEpisode() {
        // GIVEN
        int episodeId = DataFactory.randomInt();
        DbEpisodeModel oldEpisode = EpisodeDataFactory.Db.makeDbEpisodeModelWithId(episodeId);
        DbEpisodeModel updatedEpisode = EpisodeDataFactory.Db.makeDbEpisodeModelWithId(episodeId);

        // WHEN
        db.insertEpisode(oldEpisode);
        db.insertEpisode(updatedEpisode);

        // THEN
        List<DbEpisodeModel> result = db.queryAllEpisodes(1);
        assertFalse(result.contains(oldEpisode));
        assertTrue(result.contains(updatedEpisode));
    }

    @Test
    public void testInsertCharacter() {
        // GIVEN
        DbCharacterModel character = CharacterDataFactory.Db.makeDbCharacterModel();

        // WHEN
        db.insertCharacter(character);

        // THEN
        DbCharacterModel result = db.queryCharacter(character.getId());
        assertEquals(result, character);
    }

    @Test
    public void testUpdateCharacter() {
        // GIVEN
        int characterId = DataFactory.randomInt();
        DbCharacterModel oldCharacter = CharacterDataFactory.Db.makeDbCharacterModelWithId(characterId);
        DbCharacterModel updatedCharacter = CharacterDataFactory.Db.makeDbCharacterModelWithId(characterId);

        // WHEN
        db.insertCharacter(oldCharacter);
        db.insertCharacter(updatedCharacter);

        // THEN
        DbCharacterModel result = db.queryCharacter(characterId);
        assertNotEquals(result, oldCharacter);
        assertEquals(result, updatedCharacter);
    }

    @Test
    public void testQueryCharacter() {
        // GIVEN
        int characterId = DataFactory.randomInt();
        DbCharacterModel expected = CharacterDataFactory.Db.makeDbCharacterModelWithId(characterId);

        // WHEN
        db.insertCharacter(expected);

        // THEN
        DbCharacterModel actual = db.queryCharacter(characterId);
        assertEquals(expected, actual);
    }

    @Test
    public void testQueryAllCharacters() {
        // GIVEN
        DbCharacterModel expected = CharacterDataFactory.Db.makeDbCharacterModel();
        List<Integer> ids = Collections.singletonList(expected.getId());

        // WHEN
        db.insertCharacter(expected);

        // THEN
        List<DbCharacterModel> result = db.queryCharactersByIds(ids);
        assertTrue(result.contains(expected));
    }

    @Test
    public void testAliveCharacterNotKilledByUserIsAlive() {
        // GIVEN
        int characterId = DataFactory.randomInt();
        DbCharacterModel character = CharacterDataFactory.Db.makeDbCharacterModelWithIdAndStatus(characterId, CharacterMapper.CharacterDbMapper.ALIVE, true, false);

        // WHEN
        db.insertCharacter(character);

        // THEN
        DbCharacterModel result = db.queryCharacter(character.getId());
        assertEquals(result.getStatus(), CharacterMapper.CharacterDbMapper.ALIVE);
        assertTrue(result.getStatusAlive());
        assertFalse(result.getKilledByUser());
    }

    @Test
    public void testUserCanKillCharacter() throws NoOfflineDataException {
        // GIVEN
        int characterId = DataFactory.randomInt();
        DbCharacterModel character = CharacterDataFactory.Db.makeDbCharacterModelWithIdAndStatus(characterId, CharacterMapper.CharacterDbMapper.ALIVE, true, false);

        // WHEN
        db.insertCharacter(character);
        db.killCharacter(character.getId());

        // THEN
        DbCharacterModel result = db.queryCharacter(character.getId());
        assertEquals(result.getStatus(), CharacterMapper.CharacterDbMapper.ALIVE);
        assertTrue(result.getStatusAlive());
        assertTrue(result.getKilledByUser());
    }

    @Test
    public void testCharacterKilledByUserKeepsDeadAfterInsert() throws NoOfflineDataException {
        // GIVEN
        int characterId = DataFactory.randomInt();
        DbCharacterModel character = CharacterDataFactory.Db.makeDbCharacterModelWithIdAndStatus(characterId, CharacterMapper.CharacterDbMapper.ALIVE, true, false);

        // WHEN
        db.insertCharacter(character);
        db.killCharacter(character.getId());
        db.insertCharacter(character);

        // THEN
        DbCharacterModel result = db.queryCharacter(character.getId());
        assertEquals(result.getStatus(), CharacterMapper.CharacterDbMapper.ALIVE);
        assertTrue(result.getStatusAlive());
        assertTrue(result.getKilledByUser());
    }
}