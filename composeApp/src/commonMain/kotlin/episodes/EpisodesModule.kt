package episodes

import episodes.data.EpisodesRemoteDataSource
import episodes.data.EpisodesRepositoryImpl
import episodes.domain.EpisodesRepository
import org.koin.dsl.module

val episodesModule = module {
    single { EpisodesRemoteDataSource() }

    single<EpisodesRepository> {
        EpisodesRepositoryImpl(
            remote = get(),
        )
    }
}
