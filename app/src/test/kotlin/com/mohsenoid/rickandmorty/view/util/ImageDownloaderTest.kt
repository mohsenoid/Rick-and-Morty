package com.mohsenoid.rickandmorty.view.util

import com.mohsenoid.rickandmorty.data.network.NetworkHelper
import com.mohsenoid.rickandmorty.test.TestTaskExecutor
import com.mohsenoid.rickandmorty.util.executor.TaskExecutor
import com.mohsenoid.rickandmorty.util.image.ImageDownloader
import com.mohsenoid.rickandmorty.util.image.ImageDownloaderImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ImageDownloaderTest {

    @Mock
    lateinit var networkHelper: NetworkHelper

    private val testTaskExecutor: TaskExecutor = TestTaskExecutor()
    private val cacheDirectoryPath = ""

    private lateinit var imageDownloader: ImageDownloader

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        imageDownloader = ImageDownloaderImpl(
            networkHelper = networkHelper,
            cacheDirectoryPath = cacheDirectoryPath,
            ioTaskExecutor = testTaskExecutor,
            mainTaskExecutor = testTaskExecutor
        )
    }

    @Test
    fun `test extractFileName`() {
        // GIVEN
        val input = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        val expected = "1.jpeg"

        // WHEN
        val actual = imageDownloader.extractFileName(input)

        // THAN
        assertEquals(expected, actual)
    }
}
