package com.mohsenoid.rickandmorty.data.db

import org.koin.dsl.module

internal val databaseModule =
    module {
        single {
            DatabaseProvider.getDatabase(context = get())
        }

        factory {
            val db: Database = get()
            db.characterDao()
        }

        factory {
            val db: Database = get()
            db.episodeDao()
        }
    }
