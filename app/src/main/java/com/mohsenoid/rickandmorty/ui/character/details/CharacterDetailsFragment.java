package com.mohsenoid.rickandmorty.ui.character.details;

import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.ui.base.BaseFragment;

public class CharacterDetailsFragment extends BaseFragment implements CharacterDetailsContract.View {

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

        if (isCritical) {
            FragmentActivity activity = getActivity();
            if (activity != null && !activity.isFinishing()) activity.finish();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
