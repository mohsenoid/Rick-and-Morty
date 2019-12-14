package com.mohsenoid.rickandmorty.ui.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mohsenoid.rickandmorty.RickAndMortyApplication;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        DependenciesProvider dependenciesProvider = ((RickAndMortyApplication) context.getApplicationContext()).getDependenciesProvider();

        injectDependencies(dependenciesProvider);
        super.onAttach(context);
    }

    public abstract void injectDependencies(DependenciesProvider dependenciesProvider);
}
