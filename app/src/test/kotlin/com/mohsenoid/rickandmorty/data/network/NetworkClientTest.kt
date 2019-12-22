package com.mohsenoid.rickandmorty.data.network

import com.mohsenoid.rickandmorty.data.Serializer.serializeIntegerList
import com.mohsenoid.rickandmorty.test.NetworkResponseFactory
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NetworkClientTest {

    @Mock
    private lateinit var helper: NetworkHelper

    private lateinit var networkClient: NetworkClient

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkClient = NetworkClientImpl(helper)
    }

    @Test
    fun `test getEpisodes`() {
        // GIVEN
        stubNetworkRequestData(
            episodeEndpoint = NetworkConstants.EPISODE_ENDPOINT,
            response = NetworkResponseFactory.Episode.EPISODES_JSON
        )
        val expected = NetworkResponseFactory.Episode.episodesResponse()

        // WHEN
        val actual = networkClient.getEpisodes(page = 1)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test getCharactersByIds`() {
        // GIVEN
        val characterIds = listOf(1)
        val serializedCharacterIds = serializeIntegerList(characterIds)
        stubNetworkRequestData(
            episodeEndpoint = "${NetworkConstants.CHARACTER_ENDPOINT}[$serializedCharacterIds]",
            response = NetworkResponseFactory.Characters.CHARACTERS_JSON
        )
        val expected = NetworkResponseFactory.Characters.charactersResponse()

        // WHEN
        val actual = networkClient.getCharactersByIds(characterIds)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test getCharacterDetails`() {
        // GIVEN
        val characterId = 1
        stubNetworkRequestData(
            episodeEndpoint = NetworkConstants.CHARACTER_ENDPOINT + characterId,
            response = NetworkResponseFactory.CharacterDetails.CHARACTER_DETAILS_JSON
        )
        val expected = NetworkResponseFactory.CharacterDetails.characterResponse()

        // WHEN
        val actual = networkClient.getCharacterDetails(characterId)

        // THEN
        assertEquals(expected, actual)
    }

    private fun stubNetworkRequestData(episodeEndpoint: String, response: String) {
        whenever(
            helper.requestData(
                endpoint = eq(episodeEndpoint),
                params = anyOrNull()
            )
        )
            .thenReturn(response)
    }
}
