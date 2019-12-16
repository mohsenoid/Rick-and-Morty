package com.mohsenoid.rickandmorty.test;

import com.mohsenoid.rickandmorty.util.executor.TaskExecutor;

public class TestTaskExecutor implements TaskExecutor {

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
