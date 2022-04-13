package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button searchBtn;
    EditText searchBar;
    TextView emptyMsg;
    RecyclerView movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.editTextSearchBar);

        emptyMsg = findViewById(R.id.emptyMsg);

        searchBtn = findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(this);

        movieList = findViewById(R.id.recycler);
    }

    @Override
    public void onClick(View view) {

    }
}