package com.mohsenoid.rickandmorty.data.db;

import android.app.Application;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;
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
public class DatastoreTest {

    private Datastore datastore;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        datastore = DatastoreImpl.getInstance(application);
    }

    @After
    public void tearDown() {
        DatastoreImpl.instance = null;
    }

    @Test
    public void testInsertEpisode() {
        // GIVEN
        EpisodeModel episode = EpisodeDataFactory.makeEpisode();

        // WHEN
        datastore.insertEpisode(episode);

        // THEN
        List<EpisodeModel> result = datastore.queryAllEpisodes(1);
        assertTrue(result.contains(episode));
    }

    @Test
    public void testUpdateEpisode() {
        // GIVEN
        int episodeId = DataFactory.randomInt();

        EpisodeModel oldEpisode = EpisodeDataFactory.makeEpisode();
        oldEpisode.setId(episodeId);

        EpisodeModel updatedEpisode = EpisodeDataFactory.makeEpisode();
        updatedEpisode.setId(episodeId);

        // WHEN
        datastore.insertEpisode(oldEpisode);
        datastore.insertEpisode(updatedEpisode);

        // THEN
        List<EpisodeModel> result = datastore.queryAllEpisodes(1);
        assertFalse(result.contains(oldEpisode));
        assertTrue(result.contains(updatedEpisode));
    }

    @Test
    public void testInsertCharacter() {
        // GIVEN
        CharacterModel character = CharacterDataFactory.makeCharacter();

        // WHEN
        datastore.insertCharacter(character);

        // THEN
        CharacterModel result = datastore.queryCharacter(character.getId());
        assertEquals(result, character);
    }

    @Test
    public void testUpdateCharacter() {
        // GIVEN
        Integer characterId = DataFactory.randomInt();

        CharacterModel oldCharacter = CharacterDataFactory.makeCharacter();
        oldCharacter.setId(characterId);

        CharacterModel updatedCharacter = CharacterDataFactory.makeCharacter();
        updatedCharacter.setId(characterId);

        // WHEN
        datastore.insertCharacter(oldCharacter);
        datastore.insertCharacter(updatedCharacter);

        // THEN
        CharacterModel result = datastore.queryCharacter(characterId);
        assertNotEquals(result, oldCharacter);
        assertEquals(result, updatedCharacter);
    }

    @Test
    public void testQueryCharacter() {
        // GIVEN
        int characterId = DataFactory.randomInt();

        CharacterModel expected = CharacterDataFactory.makeCharacter();
        expected.setId(characterId);

        // WHEN
        datastore.insertCharacter(expected);

        // THEN
        CharacterModel actual = datastore.queryCharacter(characterId);
        assertEquals(expected, actual);
    }

    @Test
    public void testQueryAllCharacters() {
        // GIVEN
        CharacterModel expected = CharacterDataFactory.makeCharacter();
        List<Integer> ids = Collections.singletonList(expected.getId());

        // WHEN
        datastore.insertCharacter(expected);

        // THEN
        List<CharacterModel> result = datastore.queryAllCharacters(ids);
        assertTrue(result.contains(expected));
    }

    @Test
    public void testAliveCharacterNotKilledByUserIsAlive() {
        // GIVEN
        CharacterModel character = CharacterDataFactory.makeCharacter();
        character.setStatus(CharacterModel.ALIVE);

        // WHEN
        datastore.insertCharacter(character);

        // THEN
        CharacterModel result = datastore.queryCharacter(character.getId());
        assertTrue(result.isAlive());
    }

    @Test
    public void testUserCanKillCharacter() throws NoOfflineDataException {
        // GIVEN
        CharacterModel character = CharacterDataFactory.makeCharacter();
        character.setStatus(CharacterModel.ALIVE);

        // WHEN
        datastore.insertCharacter(character);
        datastore.killCharacter(character.getId());

        // THEN
        CharacterModel result = datastore.queryCharacter(character.getId());
        assertFalse(result.isAlive());
    }

    @Test
    public void testCharacterKilledByUserKeepsDeadAfterInsert() throws NoOfflineDataException {
        // GIVEN
        CharacterModel character = CharacterDataFactory.makeCharacter();
        character.setStatus(CharacterModel.ALIVE);

        // WHEN
        datastore.insertCharacter(character);
        datastore.killCharacter(character.getId());
        datastore.insertCharacter(character);

        // THEN
        CharacterModel result = datastore.queryCharacter(character.getId());
        assertFalse(result.isAlive());
    }
}