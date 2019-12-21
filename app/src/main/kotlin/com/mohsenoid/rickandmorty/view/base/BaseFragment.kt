package com.mohsenoid.rickandmorty.view.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.mohsenoid.rickandmorty.RickAndMortyApplication
import com.mohsenoid.rickandmorty.injection.DependenciesProvider

abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        val dependenciesProvider =
            (context.applicationContext as RickAndMortyApplication).dependenciesProvider
        injectDependencies(dependenciesProvider)
        super.onAttach(context)
    }

    protected abstract fun injectDependencies(dependenciesProvider: DependenciesProvider)
}
