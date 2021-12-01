package com.mohsenoid.rickandmorty.view.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

fun Lifecycle.launchWhileResumed(block: suspend CoroutineScope.() -> Unit): Job {
    val job = coroutineScope.launchWhenResumed(block)
    addObserver(
        object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                job.cancel()
                removeObserver(this)
            }
        },
    )
    return job
}
