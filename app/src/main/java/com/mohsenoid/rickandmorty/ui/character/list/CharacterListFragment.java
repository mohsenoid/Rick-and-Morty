package com.mohsenoid.rickandmorty.ui.character.list;

import android.widget.Toast;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.ui.base.BaseFragment;

import java.util.List;

public class CharacterListFragment extends BaseFragment implements CharacterListContract.View {

    @Override
    public void injectDependencies(DependenciesProvider dependenciesProvider) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage(boolean isCritical) {
        Toast.makeText(getContext(), R.string.offline_app, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onEpisodeCharacterQueryResult(List<CharacterModel> characters) {

    }
}
