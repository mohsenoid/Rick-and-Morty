package com.mohsenoid.rickandmorty.injection;

import android.app.Application;

import com.mohsenoid.rickandmorty.data.RepositoryImpl;
import com.mohsenoid.rickandmorty.data.db.Db;
import com.mohsenoid.rickandmorty.data.db.DbImpl;
import com.mohsenoid.rickandmorty.data.mapper.CharacterMapper;
import com.mohsenoid.rickandmorty.data.mapper.EpisodeMapper;
import com.mohsenoid.rickandmorty.data.mapper.LocationMapper;
import com.mohsenoid.rickandmorty.data.mapper.OriginMapper;
import com.mohsenoid.rickandmorty.data.network.NetworkClient;
import com.mohsenoid.rickandmorty.data.network.NetworkClientImpl;
import com.mohsenoid.rickandmorty.data.network.NetworkConstants;
import com.mohsenoid.rickandmorty.data.network.NetworkHelper;
import com.mohsenoid.rickandmorty.data.network.NetworkHelperImpl;
import com.mohsenoid.rickandmorty.domain.Repository;
import com.mohsenoid.rickandmorty.util.config.ConfigProvider;
import com.mohsenoid.rickandmorty.util.config.ConfigProviderImpl;
import com.mohsenoid.rickandmorty.util.executor.IoTaskExecutor;
import com.mohsenoid.rickandmorty.util.executor.MainTaskExecutor;
import com.mohsenoid.rickandmorty.util.executor.TaskExecutor;
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsContract;
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsFragment;
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsPresenter;
import com.mohsenoid.rickandmorty.view.character.list.CharacterListContract;
import com.mohsenoid.rickandmorty.view.character.list.CharacterListFragment;
import com.mohsenoid.rickandmorty.view.character.list.CharacterListPresenter;
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter;
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListContract;
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListFragment;
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListPresenter;
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter;
import com.mohsenoid.rickandmorty.view.util.ImageDownloader;
import com.mohsenoid.rickandmorty.view.util.ImageDownloaderImpl;

import java.util.List;

public class DependenciesProvider {

    private final Application context;

    public DependenciesProvider(Application context) {
        this.context = context;
    }

    private Db getDatastore() {
        return DbImpl.getInstance(context);
    }

    private NetworkHelper getNetworkHelper() {
        return NetworkHelperImpl.getInstance(NetworkConstants.BASE_URL);
    }

    private NetworkClient getApiClient() {
        NetworkHelper networkHelper = getNetworkHelper();
        return NetworkClientImpl.getInstance(networkHelper);
    }

    private TaskExecutor getIoTaskExecutor() {
        return IoTaskExecutor.getInstance();
    }

    private TaskExecutor getMainTaskExecutor() {
        return MainTaskExecutor.getInstance();
    }

    private ConfigProvider getConfigProvider() {
        return ConfigProviderImpl.getInstance(context);
    }

    private EpisodeMapper getEpisodeMapper() {
        return EpisodeMapper.getInstance();
    }

    private OriginMapper getOriginMapper() {
        return OriginMapper.getInstance();
    }

    private LocationMapper getLocationMapper() {
        return LocationMapper.getInstance();
    }

    private CharacterMapper getCharacterMapper() {
        OriginMapper originMapper = getOriginMapper();
        LocationMapper locationMapper = getLocationMapper();

        return CharacterMapper.getInstance(originMapper, locationMapper);
    }

    private Repository getRepository() {
        Db db = getDatastore();
        NetworkClient networkClient = getApiClient();
        TaskExecutor ioTaskExecutor = getIoTaskExecutor();
        TaskExecutor mainTaskExecutor = getMainTaskExecutor();
        ConfigProvider configProvider = getConfigProvider();
        EpisodeMapper episodeMapper = getEpisodeMapper();
        CharacterMapper characterMapper = getCharacterMapper();

        return RepositoryImpl.getInstance(db, networkClient, ioTaskExecutor, mainTaskExecutor, configProvider, episodeMapper, characterMapper);
    }

    private String getCacheDirectoryPath() {
        return context.getCacheDir().getAbsolutePath();
    }

    public ImageDownloader getImageDownloader() {
        NetworkHelper networkHelper = getNetworkHelper();
        String cacheDirectoryPath = getCacheDirectoryPath();
        TaskExecutor ioTaskExecutor = getIoTaskExecutor();
        TaskExecutor mainTaskExecutor = getMainTaskExecutor();

        return ImageDownloaderImpl.getInstance(networkHelper, cacheDirectoryPath, ioTaskExecutor, mainTaskExecutor);
    }

    public EpisodeListFragment getEpisodeListFragment() {
        return EpisodeListFragment.newInstance();
    }

    public EpisodeListContract.Presenter getEpisodesListFragmentPresenter() {
        Repository repository = getRepository();
        ConfigProvider configProvider = getConfigProvider();

        return new EpisodeListPresenter(repository, configProvider);
    }

    public EpisodeListAdapter getEpisodesListAdapter(EpisodeListAdapter.ClickListener listener) {
        return new EpisodeListAdapter(listener);
    }

    public CharacterListFragment getCharacterListFragment(List<Integer> characterIds) {
        return CharacterListFragment.newInstance(characterIds);
    }

    public CharacterListContract.Presenter getCharacterListFragmentPresenter() {
        Repository repository = getRepository();
        ConfigProvider configProvider = getConfigProvider();

        return new CharacterListPresenter(repository, configProvider);
    }

    public CharacterListAdapter getCharacterListAdapter(CharacterListAdapter.ClickListener listener) {
        ImageDownloader imageDownloader = getImageDownloader();
        return new CharacterListAdapter(imageDownloader, listener);
    }

    public CharacterDetailsFragment getCharacterDetailsFragment(int characterId) {
        return CharacterDetailsFragment.newInstance(characterId);
    }

    public CharacterDetailsContract.Presenter getCharacterDetailsFragmentPresenter() {
        Repository repository = getRepository();
        ConfigProvider configProvider = getConfigProvider();

        return new CharacterDetailsPresenter(repository, configProvider);
    }
}
