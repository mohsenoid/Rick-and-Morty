package com.mohsenoid.rickandmorty.view.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var job: Job
    override lateinit var coroutineContext: CoroutineContext

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)

        job = SupervisorJob()
        coroutineContext = dispatcherProvider.mainDispatcher + job

        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        job.cancel()
    }
}
