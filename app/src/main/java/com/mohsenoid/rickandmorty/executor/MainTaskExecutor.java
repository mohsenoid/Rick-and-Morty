package com.mohsenoid.rickandmorty.executor;

import android.os.Handler;

public class MainTaskExecutor implements TaskExecutor {

    private static MainTaskExecutor instance;

    private final Handler handler = new Handler();

    private MainTaskExecutor() { /* this will prevent making a new object of this type from outside */ }

    public static synchronized MainTaskExecutor getInstance() {
        if (instance == null)
            instance = new MainTaskExecutor();

        return instance;
    }

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
