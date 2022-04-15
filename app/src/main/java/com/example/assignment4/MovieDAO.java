package com.example.assignment4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void addNewMovieToDB(Movie newMovie);

    @Delete
    void deleteMovie(Movie toDeleteMovie);

    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

}
