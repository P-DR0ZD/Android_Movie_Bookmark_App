package com.example.assignment4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    public ArrayList<Movie> getMoviesFromJSON(String json)
    {
        ArrayList<Movie> arrayList = new ArrayList<>(0);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieArrayObj = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setId(movieArrayObj.getString("id"));
                movie.setTitle(movieArrayObj.getString("title"));
                movie.setDescription(movieArrayObj.getString("type"));
                movie.setPoster(movieArrayObj.getString("image"));
                arrayList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public Movie getMovieDetailsFromJSON(String json)
    {
        Movie movie = new Movie();
        try {
            JSONObject jsonObject = new JSONObject(json);

            movie.setId(jsonObject.getString("id"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setDescription(jsonObject.getString("type"));
            movie.setPoster(jsonObject.getString("image"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
