package com.mohsenoid.rickandmorty.injection

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mohsenoid.rickandmorty.BuildConfig
import com.mohsenoid.rickandmorty.data.RepositoryImpl
import com.mohsenoid.rickandmorty.data.db.Db
import com.mohsenoid.rickandmorty.data.db.DbImpl
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.mapper.*
import com.mohsenoid.rickandmorty.data.network.NetworkClient
import com.mohsenoid.rickandmorty.data.network.NetworkConstants
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.mohsenoid.rickandmorty.util.config.ConfigProviderImpl
import com.mohsenoid.rickandmorty.util.dispatcher.AppDispatcherProvider
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsContract
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsFragment
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsPresenter
import com.mohsenoid.rickandmorty.view.character.list.CharacterListContract
import com.mohsenoid.rickandmorty.view.character.list.CharacterListFragment
import com.mohsenoid.rickandmorty.view.character.list.CharacterListPresenter
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListContract
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListFragment
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListPresenter
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class DependenciesProvider(private val context: Application) {

    private val datastore: Db by lazy {
        DbImpl(context)
    }

    private val baseUrl: HttpUrl by lazy {
        NetworkConstants.BASE_URL.toHttpUrlOrNull()
            ?: throw UnknownHostException("Invalid host: " + NetworkConstants.BASE_URL)
    }

    private val gson: Gson by lazy {
        GsonBuilder().serializeNulls().create()
    }

    private val converterFactory: Converter.Factory by lazy {
        GsonConverterFactory.create(gson)
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(loggingInterceptor)
        }
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    private val networkClient: NetworkClient by lazy {
        retrofit.create(NetworkClient::class.java)
    }

    val dispatcherProvider: DispatcherProvider by lazy {
        AppDispatcherProvider()
    }

    private val configProvider: ConfigProvider by lazy {
        ConfigProviderImpl(context)
    }

    private val episodeDbMapper: Mapper<NetworkEpisodeModel, DbEpisodeModel> by lazy {
        EpisodeDbMapper()
    }

    private val episodeEntityMapper: Mapper<DbEpisodeModel, EpisodeEntity> by lazy {
        EpisodeEntityMapper()
    }

    private val originDbMapper: Mapper<NetworkOriginModel, DbOriginModel> by lazy {
        OriginDbMapper()
    }

    private val originEntityMapper: Mapper<DbOriginModel, OriginEntity> by lazy {
        OriginEntityMapper()
    }

    private val locationDbMapper: Mapper<NetworkLocationModel, DbLocationModel> by lazy {
        LocationDbMapper()
    }

    private val locationEntityMapper: Mapper<DbLocationModel, LocationEntity> by lazy {
        LocationEntityMapper()
    }

    private val characterDbMapper: Mapper<NetworkCharacterModel, DbCharacterModel> by lazy {
        CharacterDbMapper(originDbMapper, locationDbMapper)
    }

    private val characterEntityMapper: Mapper<DbCharacterModel, CharacterEntity> by lazy {
        CharacterEntityMapper(originEntityMapper, locationEntityMapper)
    }

    private val repository: Repository by lazy {
        RepositoryImpl(
            datastore,
            networkClient,
            dispatcherProvider,
            configProvider,
            episodeDbMapper,
            episodeEntityMapper,
            characterDbMapper,
            characterEntityMapper
        )
    }

    fun getEpisodeListFragment(): EpisodeListFragment {
        return EpisodeListFragment.newInstance()
    }

    fun getEpisodesListFragmentPresenter(): EpisodeListContract.Presenter {
        return EpisodeListPresenter(repository, configProvider)
    }

    fun getEpisodesListAdapter(listener: EpisodeListAdapter.ClickListener): EpisodeListAdapter {
        return EpisodeListAdapter(listener)
    }

    fun getCharacterListFragment(characterIds: List<Int>): CharacterListFragment {
        return CharacterListFragment.newInstance(characterIds)
    }

    fun getCharacterListFragmentPresenter(): CharacterListContract.Presenter {
        return CharacterListPresenter(repository, configProvider)
    }

    fun getCharacterListAdapter(listener: CharacterListAdapter.ClickListener): CharacterListAdapter {
        return CharacterListAdapter(dispatcherProvider, listener)
    }

    fun getCharacterDetailsFragment(characterId: Int): CharacterDetailsFragment {
        return CharacterDetailsFragment.newInstance(characterId)
    }

    fun getCharacterDetailsFragmentPresenter(): CharacterDetailsContract.Presenter {
        return CharacterDetailsPresenter(repository, configProvider)
    }
}
