package com.example.movieshow;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    //Patterns

    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if (instance==null){
            instance= new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService NetworkIO= Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getNetworkIO() {
        return NetworkIO;
    }


}
