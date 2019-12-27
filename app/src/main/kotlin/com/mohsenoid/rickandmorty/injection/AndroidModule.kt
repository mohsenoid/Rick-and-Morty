package com.mohsenoid.rickandmorty.injection

import android.app.Application
import android.content.Context
import com.mohsenoid.rickandmorty.injection.qualifier.ApplicationContext
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
class AndroidModule(val context: Application) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Application {
        return context
    }

    @Provides
    @Singleton
    @Named(QualifiersNames.CACHE_DIRECTORY)
    fun provideCacheDirectory(@ApplicationContext context: Context): File {
        return context.cacheDir
    }
}
