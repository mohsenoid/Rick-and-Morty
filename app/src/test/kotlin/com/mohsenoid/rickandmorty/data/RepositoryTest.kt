package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.api.ApiRickAndMorty
import com.mohsenoid.rickandmorty.data.db.DbRickAndMorty
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.util.StatusProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before

open class RepositoryTest {

    @MockK
    internal lateinit var db: DbRickAndMorty

    @MockK
    internal lateinit var api: ApiRickAndMorty

    @MockK
    internal lateinit var statusProvider: StatusProvider

    lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = RepositoryImpl(
            db = db,
            api = api,
            statusProvider = statusProvider,
        )
    }

    fun stubStatusProviderIsOnline(isOnline: Boolean) {
        every { statusProvider.isOnline() } returns isOnline
    }
}
