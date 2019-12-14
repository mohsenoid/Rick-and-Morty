package com.mohsenoid.rickandmorty.executor;

import java.util.concurrent.Executors;

public class IoTaskExecutor implements TaskExecutor {

    private final int THREADS_COUNT = 5;

    private java.util.concurrent.Executor executor = Executors.newFixedThreadPool(THREADS_COUNT);

    @Override
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
