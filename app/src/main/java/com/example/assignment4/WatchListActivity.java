package com.example.assignment4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WatchListActivity extends AppCompatActivity implements NetworkingService.NetworkingListener
        , DatabaseManager.DatabaseListener, WatchListAdapter.moviesClickListener {

    TextView loadMsg;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Movie> movies = new ArrayList<Movie>();
    DatabaseManager dbManager;
    WatchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_list);

        dbManager = ((MyApp)getApplication()).dbManager;
        dbManager.getDb(this);
        dbManager.listener = this;

        this.movies = ((MyApp)getApplication()).movies;

        loadMsg = findViewById(R.id.loadWatchListMsg);

        recyclerView = findViewById(R.id.watchList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WatchListAdapter(this, movies);

        if (movies.size() > 0) {
            loadMsg.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            loadMsg.setText("Currently no Saved Movies");
        }

        recyclerView.setAdapter(adapter);
        setTitle("Watch List");
    }

    @Override
    public void onListReady(List<Movie> list) {
        ((MyApp)getApplication()).movies = (ArrayList<Movie>) list;

        adapter = new WatchListAdapter(this, ((MyApp)getApplication()).movies);
        recyclerView.setAdapter(adapter);

        if (movies.size() > 0) {
            loadMsg.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            loadMsg.setText("Currently no Saved Movies");
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddDone() {

    }

    @Override
    public void onDeleteDone() {
        loadMsg.setText("Loading Watch List");
        recyclerView.setVisibility(View.INVISIBLE);

        dbManager.getAllMovies();
    }

    @Override
    public void movieClicked(Movie movie) {
        AlertDialog alert = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_watchlist, null);

        TextView t = v.findViewById(R.id.dialogMsg);

        t.setText("Currently Selected movie is " + movie.getTitle());

        Button remove = v.findViewById(R.id.removeBtn);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.deleteMovie(movie);
                loadMsg.setVisibility(View.VISIBLE);
                loadMsg.setText("Deleting Movie");
                recyclerView.setVisibility(View.INVISIBLE);
                alert.dismiss();
            }
        });

        Button cancel = v.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });

        Button viewMovie = v.findViewById(R.id.viewMovieBtn);
        viewMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMovie(movie);
            }
        });
        alert.setView(v);
        alert.show();

    }

    private void viewMovie(Movie movie) {
        Intent intent = new Intent(this,WatchPartyActivity.class);
        intent.putExtra("movie", movie.getImdbID());
        startActivity(intent);
    }

    @Override
    public void dataListener(String jsonString) {

    }

    @Override
    public void imageListener(Bitmap image) {

    }
}
