package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.data.db.Db
import com.mohsenoid.rickandmorty.data.db.DbCharacterDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataDbModule = module {

    single { Db.create(context = androidContext()) }

    single { get<Db>().episodeDao }

    single<DbCharacterDao> { get<Db>().characterDao }
}
