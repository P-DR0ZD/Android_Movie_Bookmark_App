package com.example.assignment4;

import android.app.Application;

public class MyApp extends Application {

    private NetworkingService networkingService = new NetworkingService();

    private JsonService jsonService = new JsonService();

    public  NetworkingService getNetworkingService(){
        return networkingService;
    }

    public  JsonService getJsonService(){
        return jsonService;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
