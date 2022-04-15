package com.example.assignment4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDataBase extends RoomDatabase{
    public abstract MovieDAO movieDAO();
}

