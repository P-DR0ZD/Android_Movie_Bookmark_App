package com.example.assignment4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieDescription extends AppCompatActivity implements NetworkingService.NetworkingListener, View.OnClickListener
        , DatabaseManager.DatabaseListener{

    NetworkingService networkingManager;
    JsonService jsonManager;
    DatabaseManager dbManager;
    ArrayList<Movie> movies = new ArrayList<Movie>();
    Movie data;
    boolean isDuplicate = false;

    TextView movieTitle;
    TextView descriptionDetail;
    TextView contentRating;
    TextView imdbRating;
    TextView summaryText;
    TextView runtime;
    TextView genre;
    TextView load;
    TextView PlotHeader;

    ImageView poster;

    Button save;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String id = getIntent().getStringExtra("movie");

        networkingManager = ((MyApp)getApplication()).getNetworkingService();
        jsonManager = ((MyApp)getApplication()).getJsonService();
        this.movies = ((MyApp)getApplication()).movies;
        networkingManager.listener = this;

        networkingManager.movieDetail(id);

        dbManager = ((MyApp)getApplication()).dbManager;
        dbManager.getDb(this);

        movieTitle = findViewById(R.id.movieTitleDetail);
        descriptionDetail = findViewById(R.id.descriptionDetail);
        contentRating = findViewById(R.id.contentRating);
        imdbRating = findViewById(R.id.imbdRating);
        summaryText = findViewById(R.id.plotText);
        runtime = findViewById(R.id.runtime);
        genre = findViewById(R.id.genre);
        PlotHeader = findViewById(R.id.PlotHeader);

        load = findViewById(R.id.loadMsg);

        poster = findViewById(R.id.posterDetail);

        save = findViewById(R.id.save);
        save.setOnClickListener(this);

        remove = findViewById(R.id.remove);
        remove.setOnClickListener(this);
    }

    @Override
    public void dataListener(String jsonString) {
        data = jsonManager.getMovieDetailsFromJSON(jsonString);

        movieTitle.setText(data.getTitle());
        movieTitle.setVisibility(View.VISIBLE);
        descriptionDetail.setText(data.getDescription());
        descriptionDetail.setVisibility(View.VISIBLE);
        contentRating.setText(data.getContentRating());
        contentRating.setVisibility(View.VISIBLE);
        imdbRating.setText(data.getImdbRating() + "/ 10");
        imdbRating.setVisibility(View.VISIBLE);
        summaryText.setText(data.getPlot());
        summaryText.setVisibility(View.VISIBLE);
        runtime.setText(data.getRunTime());
        runtime.setVisibility(View.VISIBLE);
        genre.setText("Genres: " + data.getGenre());
        genre.setVisibility(View.VISIBLE);

        PlotHeader.setVisibility(View.VISIBLE);
        networkingManager.getImageData(data.getPoster());
    }

    @Override
    public void imageListener(Bitmap image) {
        poster.setImageBitmap(image);
        poster.setVisibility(View.VISIBLE);

        for (int i = 0; i < movies.size(); i++) {
            Movie tmp = movies.get(i);
            if (data.getImdbID().equals(tmp.getImdbID())) {
                isDuplicate = true;
            }
        }

        if (!isDuplicate) {
            save.setVisibility(View.VISIBLE);
        }
        else
        {
            save.setVisibility(View.GONE);
            remove.setVisibility(View.VISIBLE);
        }
        load.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == save.getId()) {
            save.setVisibility(View.GONE);
            load.setVisibility(View.VISIBLE);
            load.setText("Saving Film To Watch List");
            dbManager.listener = this;
            dbManager.saveNewMovie(data);
        }
        else if (id == remove.getId())
        {
            remove.setVisibility(View.GONE);
            load.setVisibility(View.VISIBLE);
            load.setText("Removing Film From Watch List");
            dbManager.listener = this;
            dbManager.deleteMovie(data);
        }
    }

    @Override
    public void onListReady(List<Movie> list) {
        ((MyApp)getApplication()).movies = (ArrayList<Movie>) list;
        load.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAddDone() {
        ((MyApp)getApplication()).movies.add(data);
        load.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDeleteDone() {
        dbManager.getAllMovies();
    }
}
