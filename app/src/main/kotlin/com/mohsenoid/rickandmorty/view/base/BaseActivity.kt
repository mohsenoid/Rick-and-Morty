package com.mohsenoid.rickandmorty.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector, CoroutineScope {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var job: Job
    override lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        job = SupervisorJob()
        coroutineContext = dispatcherProvider.mainDispatcher + job

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
