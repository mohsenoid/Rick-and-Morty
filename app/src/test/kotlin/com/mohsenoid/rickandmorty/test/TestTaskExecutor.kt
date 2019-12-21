package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.util.executor.TaskExecutor

class TestTaskExecutor : TaskExecutor {

    override fun execute(runnable: () -> Unit) {
        runnable()
    }
}
