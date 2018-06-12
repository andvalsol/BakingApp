package com.example.luthiers.bakingapp.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    
    private static AppExecutors sInstance;
    private static final Object LOCK = new Object();
    private final Executor mMainThread;
    private final Executor mNetworkIO;
    private final Executor mDiskIO;
    
    //Create a constructor initializing the executors
    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        mMainThread = mainThread;
        mNetworkIO = networkIO;
        mDiskIO = diskIO;
    }
    
    //Set the class as a Singleton
    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3), //Create a pool of 3 threads
                        new MainThreadExecutor());
            }
        }
        
        return sInstance;
    }
    
    //Add getters for each Executor
    public Executor getDiskIO() {
        return mDiskIO;
    }
    
    public Executor getMainThread() {
        return mMainThread;
    }
    
    public Executor getNetworkIO() {
        return mNetworkIO;
    }
    
    
    private static class MainThreadExecutor implements Executor {
        
        private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    
    
        @Override
        public void execute(@NonNull Runnable command) {
            mMainThreadHandler.post(command);
        }
    }
    
}