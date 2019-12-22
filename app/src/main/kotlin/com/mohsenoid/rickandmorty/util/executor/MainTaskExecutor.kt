package com.mohsenoid.rickandmorty.util.executor

import android.os.Handler

class MainTaskExecutor : TaskExecutor {

    private val handler = Handler()

    override fun execute(runnable: () -> Unit) {
        handler.post(runnable)
    }
}
