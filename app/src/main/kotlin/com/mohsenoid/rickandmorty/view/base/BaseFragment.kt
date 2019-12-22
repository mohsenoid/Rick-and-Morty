package com.mohsenoid.rickandmorty.view.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.mohsenoid.rickandmorty.RickAndMortyApplication
import com.mohsenoid.rickandmorty.injection.DependenciesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job
    override lateinit var coroutineContext: CoroutineContext

    override fun onAttach(context: Context) {
        val dependenciesProvider =
            (context.applicationContext as RickAndMortyApplication).dependenciesProvider

        job = SupervisorJob()
        coroutineContext = dependenciesProvider.dispatcherProvider.mainDispatcher + job

        injectDependencies(dependenciesProvider)
        super.onAttach(context)
    }

    protected abstract fun injectDependencies(dependenciesProvider: DependenciesProvider)

    override fun onDetach() {
        super.onDetach()
        job.cancel()
    }
}
