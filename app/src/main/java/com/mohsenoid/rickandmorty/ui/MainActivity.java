package com.mohsenoid.rickandmorty.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.ui.base.BaseActivity;
import com.mohsenoid.rickandmorty.ui.episode.list.EpisodeListFragment;

public class MainActivity extends BaseActivity {

    static String TAG = MainActivity.class.getSimpleName();
    static String TAG_EPISODE_LIST_FRAGMENT = "episodeListFragment";

    private EpisodeListFragment episodeListFragment;

    @Override
    public void injectDependencies(@Nullable Bundle savedInstanceState, DependenciesProvider dependenciesProvider) {
        if (null == savedInstanceState) {
            episodeListFragment = dependenciesProvider.getEpisodeListFragment();
        } else {
            episodeListFragment = (EpisodeListFragment) getSupportFragmentManager().findFragmentByTag(TAG_EPISODE_LIST_FRAGMENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState) {
            attachFragments();
        }

        Log.i(TAG, "MainActivity created");
    }

    private void attachFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, episodeListFragment, TAG_EPISODE_LIST_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
