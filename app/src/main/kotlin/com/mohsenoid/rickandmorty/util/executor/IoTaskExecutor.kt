package com.mohsenoid.rickandmorty.util.executor

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class IoTaskExecutor : TaskExecutor {

    private val executor: Executor = Executors.newFixedThreadPool(THREADS_COUNT)

    override fun execute(runnable: () -> Unit) {
        executor.execute(runnable)
    }

    companion object {
        private const val THREADS_COUNT = 5
    }
}
