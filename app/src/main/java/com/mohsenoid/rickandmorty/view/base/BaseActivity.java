package com.mohsenoid.rickandmorty.view.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mohsenoid.rickandmorty.RickAndMortyApplication;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DependenciesProvider dependenciesProvider = ((RickAndMortyApplication) getApplicationContext()).getDependenciesProvider();

        injectDependencies(savedInstanceState, dependenciesProvider);
        super.onCreate(savedInstanceState);
    }

    protected abstract void injectDependencies(@Nullable Bundle savedInstanceState, DependenciesProvider dependenciesProvider);
}
