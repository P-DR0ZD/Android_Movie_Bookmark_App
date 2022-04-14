package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.moviesClickListener, NetworkingService.NetworkingListener, View.OnClickListener {

    TextView emptyMsg;
    Button watchParty;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Movie> movies = new ArrayList<Movie>();
    NetworkingService networkingManager;
    JsonService jsonManager;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkingManager = ((MyApp)getApplication()).getNetworkingService();
        jsonManager = ((MyApp)getApplication()).getJsonService();

        networkingManager.listener = this;

        emptyMsg = findViewById(R.id.emptyMsg);
        watchParty = findViewById(R.id.watchPartyBtn);
        watchParty.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(this, movies);

        recyclerView.setAdapter(adapter);
        setTitle("Search a movie");
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem search = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) search.getActionView();
        String searchFor = searchView.getQuery().toString();
        if (!searchFor.isEmpty())
        {
            searchView.setIconified(false);
            searchView.setQuery(searchFor,false);
        }

        searchView.setQueryHint("Search a Movie");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                networkingManager.searchMovie(query);

                recyclerView.setVisibility(View.VISIBLE);
                emptyMsg.setVisibility(View.INVISIBLE);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() >= 3) {
                    networkingManager.searchMovie(s);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyMsg.setVisibility(View.INVISIBLE);
                }
                else {
                    movies = new ArrayList<>(0);
                    adapter.movieList = movies;
                    adapter.notifyDataSetChanged();

                }
                return false;
            }
        });
        return true;
    }



    @Override
    public void movieClicked(String movie) {
        Intent intent = new Intent(this, MovieDescription.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void dataListener(String jsonString) {
        movies = jsonManager.getMoviesFromJSON(jsonString);

        adapter = new MovieAdapter(this, movies);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void imageListener(Bitmap image) {

    }

    @Override
    public void onClick(View view) {

    }
}