package com.mohsenoid.rickandmorty.data.service;

import com.mohsenoid.rickandmorty.data.service.network.NetworkHelper;
import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.test.ApiResponseFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ApiClientTest {

    private ApiClient apiClient;

    @Mock
    private NetworkHelper helper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        apiClient = new ApiClientImpl(helper);
    }

    void stubNetworkRequestData(String response) throws IOException {
        when(helper.requestData(eq(ApiConstants.EPISODE_ENDPOINT), any()))
                .thenReturn(response);
    }

    @Test
    public void testGetEpisodes() throws IOException {
        // GIVEN
        stubNetworkRequestData(ApiResponseFactory.EPISODES_JSON);
        List<EpisodeModel> expected = ApiResponseFactory.episodesResponse();

        // WHEN
        List<EpisodeModel> actual = apiClient.getEpisodes(1);

        // THEN
        assertEquals(expected, actual);
    }
}