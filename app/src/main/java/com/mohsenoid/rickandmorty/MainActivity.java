package com.mohsenoid.rickandmorty;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.config.ConfigProviderImpl;
import com.mohsenoid.rickandmorty.data.DataCallback;
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
import com.mohsenoid.rickandmorty.executor.TaskExecutor;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String TAG = MainActivity.class.getSimpleName();

    Datastore datastore;
    NetworkHelper networkHelper;
    ApiClient apiClient;
    TaskExecutor ioTaskExecutor;
    ConfigProvider configProvider;

    Repository repository;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datastore = new DatastoreImpl(this);
        networkHelper = new NetworkHelperImpl(ApiConstants.BASE_URL);
        apiClient = new ApiClientImpl(networkHelper);
        ioTaskExecutor = new IoTaskExecutor();
        configProvider = new ConfigProviderImpl(this);

        repository = new RepositoryImpl(datastore, apiClient, ioTaskExecutor, configProvider);

        query();
    }

    private void query() {
        DataCallback<List<EpisodeModel>> callback = new DataCallback<List<EpisodeModel>>() {
            @Override
            public void onSuccess(List<EpisodeModel> episodes) {
                Log.d(TAG, episodes.toString());
                page++;

                query();
            }

            @Override
            public void onError(Exception exception) {
                Log.e(TAG, exception.getMessage());
            }
        };
        repository.queryEpisodes(page, callback);
    }
}
