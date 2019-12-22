package com.mohsenoid.rickandmorty.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohsenoid.rickandmorty.RickAndMortyApplication
import com.mohsenoid.rickandmorty.injection.DependenciesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        val dependenciesProvider =
            (applicationContext as RickAndMortyApplication).dependenciesProvider

        job = SupervisorJob()
        coroutineContext = dependenciesProvider.dispatcherProvider.mainDispatcher + job

        injectDependencies(savedInstanceState, dependenciesProvider)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun injectDependencies(
        savedInstanceState: Bundle?,
        dependenciesProvider: DependenciesProvider
    )

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
