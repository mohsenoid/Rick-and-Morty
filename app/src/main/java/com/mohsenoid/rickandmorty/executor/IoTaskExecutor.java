package com.mohsenoid.rickandmorty.executor;

import java.util.concurrent.Executors;

public class IoTaskExecutor implements TaskExecutor {

    private static IoTaskExecutor instance;

    private final int THREADS_COUNT = 5;
    private java.util.concurrent.Executor executor = Executors.newFixedThreadPool(THREADS_COUNT);

    private IoTaskExecutor() { /* this will prevent making a new object of this type from outside */ }

    public static IoTaskExecutor getInstance() {
        if (instance == null)
            instance = new IoTaskExecutor();

        return instance;
    }

    @Override
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
