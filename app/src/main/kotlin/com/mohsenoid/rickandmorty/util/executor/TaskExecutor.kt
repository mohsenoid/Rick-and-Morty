package com.mohsenoid.rickandmorty.util.executor

interface TaskExecutor {

    fun execute(runnable: () -> Unit)
}
