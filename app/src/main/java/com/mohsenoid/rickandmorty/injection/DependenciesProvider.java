package com.mohsenoid.rickandmorty.injection;

import android.content.Context;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.config.ConfigProviderImpl;
import com.mohsenoid.rickandmorty.data.Repository;
import com.mohsenoid.rickandmorty.data.RepositoryImpl;
import com.mohsenoid.rickandmorty.data.db.Datastore;
import com.mohsenoid.rickandmorty.data.db.DatastoreImpl;
import com.mohsenoid.rickandmorty.data.service.ApiClient;
import com.mohsenoid.rickandmorty.data.service.ApiClientImpl;
import com.mohsenoid.rickandmorty.data.service.ApiConstants;
import com.mohsenoid.rickandmorty.data.service.network.NetworkHelper;
import com.mohsenoid.rickandmorty.data.service.network.NetworkHelperImpl;
import com.mohsenoid.rickandmorty.executor.IoTaskExecutor;
import com.mohsenoid.rickandmorty.executor.MainTaskExecutor;
import com.mohsenoid.rickandmorty.executor.TaskExecutor;
import com.mohsenoid.rickandmorty.ui.character.details.CharacterDetailsContract;
import com.mohsenoid.rickandmorty.ui.character.details.CharacterDetailsFragment;
import com.mohsenoid.rickandmorty.ui.character.details.CharacterDetailsPresenter;
import com.mohsenoid.rickandmorty.ui.character.list.CharacterListContract;
import com.mohsenoid.rickandmorty.ui.character.list.CharacterListFragment;
import com.mohsenoid.rickandmorty.ui.character.list.CharacterListPresenter;
import com.mohsenoid.rickandmorty.ui.character.list.adapter.CharacterListAdapter;
import com.mohsenoid.rickandmorty.ui.episode.list.EpisodeListContract;
import com.mohsenoid.rickandmorty.ui.episode.list.EpisodeListFragment;
import com.mohsenoid.rickandmorty.ui.episode.list.EpisodeListPresenter;
import com.mohsenoid.rickandmorty.ui.episode.list.adapter.EpisodeListAdapter;

import java.util.List;

public class DependenciesProvider {

    private Context context;

    public DependenciesProvider(Context context) {
        this.context = context;
    }

    private Datastore getDatastore() {
        return new DatastoreImpl(context);
    }

    private NetworkHelper getNetworkHelper() {
        return new NetworkHelperImpl(ApiConstants.BASE_URL);
    }

    private ApiClient getApiClient() {
        NetworkHelper networkHelper = getNetworkHelper();
        return new ApiClientImpl(networkHelper);
    }

    private TaskExecutor getIoTaskExecutor() {
        return new IoTaskExecutor();
    }

    private TaskExecutor getMainTaskExecutor() {
        return new MainTaskExecutor();
    }

    private ConfigProvider getConfigProvider() {
        return new ConfigProviderImpl(context);
    }

    private Repository getRepository() {
        Datastore datastore = getDatastore();
        ApiClient apiClient = getApiClient();
        TaskExecutor ioTaskExecutor = getIoTaskExecutor();
        TaskExecutor mainTaskExecutor = getMainTaskExecutor();
        ConfigProvider configProvider = getConfigProvider();

        return new RepositoryImpl(datastore, apiClient, ioTaskExecutor, mainTaskExecutor, configProvider);
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
        return new CharacterListAdapter(listener);
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
