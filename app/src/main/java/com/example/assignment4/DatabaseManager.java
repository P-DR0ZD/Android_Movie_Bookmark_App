package com.example.assignment4;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Delete;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {

    interface DatabaseListener {
        void onListReady (List<Movie> list);
        void onAddDone ();
        void onDeleteDone();
    }

    DatabaseListener listener;
    static MovieDataBase db;
    ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

    private static void buildDBInstance(Context cntx)
    {
        db = Room.databaseBuilder(cntx,
                MovieDataBase.class, "movie_db").build();
    }

    public MovieDataBase getDb(Context cntx) {
        if (db == null)
            buildDBInstance(cntx);
        return db;
    }

    public void saveNewMovie(Movie movie)
    {
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.movieDAO().addNewMovieToDB(movie);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onAddDone();
                    }
                });
            }
        });
    }

    public void getAllMovies(){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> list = db.movieDAO().getAll();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onListReady(list);
                    }
                });

            }
        });
    }

    public void deleteMovie(Movie toDelete)
    {
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.movieDAO().deleteMovie(toDelete);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDeleteDone();
                    }
                });

            }
        });
    }

}
