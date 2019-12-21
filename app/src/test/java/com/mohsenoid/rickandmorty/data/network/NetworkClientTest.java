package com.mohsenoid.rickandmorty.data.network;

import com.mohsenoid.rickandmorty.data.Serializer;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel;
import com.mohsenoid.rickandmorty.test.NetworkResponseFactory;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class NetworkClientTest {

    private NetworkClient networkClient;

    @Mock
    private NetworkHelper helper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        networkClient = NetworkClientImpl.getInstance(helper);
    }

    @After
    public void tearDown() {
        NetworkClientImpl.instance = null;
    }

    @Test
    public void testGetEpisodes() throws IOException, JSONException {
        // GIVEN
        stubNetworkRequestData(NetworkConstants.EPISODE_ENDPOINT, NetworkResponseFactory.Episode.EPISODES_JSON);
        List<NetworkEpisodeModel> expected = NetworkResponseFactory.Episode.episodesResponse();

        // WHEN
        List<NetworkEpisodeModel> actual = networkClient.getEpisodes(1);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCharacters() throws IOException, JSONException {
        // GIVEN
        List<Integer> characterIds = Collections.singletonList(1);

        String characterEndpoint = NetworkConstants.CHARACTER_ENDPOINT + "[" + Serializer.serializeIntegerList(characterIds) + "]";
        stubNetworkRequestData(characterEndpoint, NetworkResponseFactory.Characters.CHARACTERS_JSON);

        List<NetworkCharacterModel> expected = NetworkResponseFactory.Characters.charactersResponse();

        // WHEN
        List<NetworkCharacterModel> actual = networkClient.getCharactersByIds(characterIds);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCharacterDetails() throws IOException, JSONException {
        // GIVEN
        int characterId = 1;
        stubNetworkRequestData(NetworkConstants.CHARACTER_ENDPOINT + characterId, NetworkResponseFactory.CharacterDetails.CHARACTER_DETAILS_JSON);
        NetworkCharacterModel expected = NetworkResponseFactory.CharacterDetails.characterResponse();

        // WHEN
        NetworkCharacterModel actual = networkClient.getCharacterDetails(characterId);

        // THEN
        assertEquals(expected, actual);
    }

    private void stubNetworkRequestData(String episodeEndpoint, String response) throws IOException {
        when(helper.requestData(eq(episodeEndpoint), any()))
                .thenReturn(response);
    }
}