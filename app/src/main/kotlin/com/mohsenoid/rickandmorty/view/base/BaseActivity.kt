package com.mohsenoid.rickandmorty.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    private val dispatcherProvider: DispatcherProvider by inject()

    private lateinit var job: Job
    override lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        job = SupervisorJob()
        coroutineContext = dispatcherProvider.mainDispatcher + job

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
