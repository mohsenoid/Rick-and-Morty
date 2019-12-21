package com.mohsenoid.rickandmorty.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohsenoid.rickandmorty.RickAndMortyApplication
import com.mohsenoid.rickandmorty.injection.DependenciesProvider

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val dependenciesProvider =
            (applicationContext as RickAndMortyApplication).dependenciesProvider
        injectDependencies(savedInstanceState, dependenciesProvider)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun injectDependencies(
        savedInstanceState: Bundle?,
        dependenciesProvider: DependenciesProvider
    )
}
