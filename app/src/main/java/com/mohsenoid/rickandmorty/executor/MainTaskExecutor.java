package com.mohsenoid.rickandmorty.executor;

import android.os.Handler;
import android.os.Looper;

public class MainTaskExecutor implements TaskExecutor {

    private static MainTaskExecutor instance;

    private Handler handler = new Handler(Looper.getMainLooper());

    private MainTaskExecutor() { /* this will prevent making a new object of this type from outside */ }

    public static MainTaskExecutor getInstance() {
        if (instance == null)
            instance = new MainTaskExecutor();

        return instance;
    }

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
