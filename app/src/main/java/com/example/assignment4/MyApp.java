package com.example.assignment4;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {

    private NetworkingService networkingService = new NetworkingService();

    private JsonService jsonService = new JsonService();

    public  NetworkingService getNetworkingService(){
        return networkingService;
    }

    public  JsonService getJsonService(){
        return jsonService;
    }

    DatabaseManager dbManager = new DatabaseManager();

    ArrayList<Movie> movies;

    @Override
    public void onCreate() {
        super.onCreate();

        movies = new ArrayList<Movie>();
    }
}
