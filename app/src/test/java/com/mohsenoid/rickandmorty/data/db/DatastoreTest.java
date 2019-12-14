package com.mohsenoid.rickandmorty.data.db;

import android.app.Application;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.test.CharacterDataFactory;
import com.mohsenoid.rickandmorty.test.DataFactory;
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class DatastoreTest {

    private Datastore datastore;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        datastore = new DatastoreImpl(application);
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
        List<CharacterModel> result = datastore.queryAllCharacters(1);
        assertTrue(result.contains(character));
    }

    @Test
    public void testUpdateCharacter() {
        // GIVEN
        int characterId = DataFactory.randomInt();

        CharacterModel oldCharacter = CharacterDataFactory.makeCharacter();
        oldCharacter.setId(characterId);

        CharacterModel updatedCharacter = CharacterDataFactory.makeCharacter();
        updatedCharacter.setId(characterId);

        // WHEN
        datastore.insertCharacter(oldCharacter);
        datastore.insertCharacter(updatedCharacter);

        // THEN
        List<CharacterModel> result = datastore.queryAllCharacters(1);
        assertFalse(result.contains(oldCharacter));
        assertTrue(result.contains(updatedCharacter));
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
}