package com.mohsenoid.rickandmorty.executor;

import android.os.Handler;
import android.os.Looper;

public class MainTaskExecutor implements TaskExecutor {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
