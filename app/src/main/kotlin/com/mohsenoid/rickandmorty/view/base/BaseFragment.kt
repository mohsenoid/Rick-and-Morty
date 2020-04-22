package com.mohsenoid.rickandmorty.view.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    private val dispatcherProvider: DispatcherProvider by inject()

    private lateinit var job: Job
    override lateinit var coroutineContext: CoroutineContext

    override fun onAttach(context: Context) {
        job = SupervisorJob()
        coroutineContext = dispatcherProvider.mainDispatcher + job

        super.onAttach(context)
    }
}
