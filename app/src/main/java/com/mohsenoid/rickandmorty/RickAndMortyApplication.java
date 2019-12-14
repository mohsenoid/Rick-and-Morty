package com.mohsenoid.rickandmorty;

import android.app.Application;

import com.mohsenoid.rickandmorty.injection.DependenciesProvider;

public class RickAndMortyApplication extends Application {

    DependenciesProvider dependenciesProvider;

    public DependenciesProvider getDependenciesProvider() {
        return dependenciesProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dependenciesProvider = new DependenciesProvider(this);
    }
}
