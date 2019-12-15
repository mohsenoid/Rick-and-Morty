package com.mohsenoid.rickandmorty.ui.util;

import com.mohsenoid.rickandmorty.data.service.network.NetworkHelper;
import com.mohsenoid.rickandmorty.executor.TaskExecutor;
import com.mohsenoid.rickandmorty.test.TestTaskExecutor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ImageDownloaderTest {

    @Mock
    NetworkHelper networkHelper;

    private TaskExecutor testTaskExecutor = new TestTaskExecutor();
    private String cacheDirectoryPath = "";
    private ImageDownloader imageDownloader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        imageDownloader = ImageDownloaderImpl.getInstance(networkHelper, cacheDirectoryPath, testTaskExecutor, testTaskExecutor);
    }

    @After
    public void tearDown() {
        ImageDownloaderImpl.instance = null;
    }

    @Test
    public void testExtractFileName() {
        // GIVEN
        String input = "https://rickandmortyapi.com/api/character/avatar/1.jpeg";
        String expected = "1.jpeg";

        // WHEN
        String actual = imageDownloader.extractFileName(input);

        // THAN
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractNullFileName() {
        assertNull(imageDownloader.extractFileName(null));
    }
}
