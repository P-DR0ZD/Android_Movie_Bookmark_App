package com.example.assignment4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDescription extends AppCompatActivity implements NetworkingService.NetworkingListener, View.OnClickListener {

    NetworkingService networkingManager;
    JsonService jsonManager;
    Movie movie;

    TextView movieTitle;
    TextView descriptionDetail;
    TextView contentRating;
    TextView imdbRating;
    TextView summaryText;
    TextView runtime;
    TextView genre;
    TextView load;

    ImageView poster;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String id = getIntent().getStringExtra("movie");

        networkingManager = ((MyApp)getApplication()).getNetworkingService();
        jsonManager = ((MyApp)getApplication()).getJsonService();
        networkingManager.listener = this;

        networkingManager.movieDetail(id);

        movieTitle = findViewById(R.id.movieTitleDetail);
        descriptionDetail = findViewById(R.id.descriptionDetail);
        contentRating = findViewById(R.id.contentRating);
        imdbRating = findViewById(R.id.imbdRating);
        summaryText = findViewById(R.id.plotText);
        runtime = findViewById(R.id.runtime);
        genre = findViewById(R.id.genre);

        load = findViewById(R.id.loadMsg);

        poster = findViewById(R.id.posterDetail);

        save = findViewById(R.id.save);
        save.setOnClickListener(this);
    }

    @Override
    public void dataListener(String jsonString) {
        Movie data = jsonManager.getMovieDetailsFromJSON(jsonString);

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

        networkingManager.getImageData(data.getPoster());
    }

    @Override
    public void imageListener(Bitmap image) {
        poster.setImageBitmap(image);
        poster.setVisibility(View.VISIBLE);

        save.setVisibility(View.VISIBLE);
        load.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {

    }
}
