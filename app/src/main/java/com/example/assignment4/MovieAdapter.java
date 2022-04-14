package com.example.assignment4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.TasksViewHolder>{

    interface moviesClickListener {
        public void movieClicked(String movie);
    }

    moviesClickListener listener;
    private Context cntx;
    public List<Movie> movieList;

    public MovieAdapter(Context cntx, List<Movie> movieList) {
        this.cntx = cntx;
        this.movieList = movieList;
        this.listener = (MainActivity) cntx;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cntx).inflate(R.layout.search_movies, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Movie t = movieList.get(position);

        holder.movieTitle.setText(t.getTitle());
        holder.description.setText(t.getDescription());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder
        implements
            View.OnClickListener
        {
            TextView movieTitle;
            TextView description;
            //ImageView poster;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            description = itemView.findViewById(R.id.description);
            //poster = itemView.findViewById(R.id.posterSearch);
            itemView.setOnClickListener(this);
        }
            @Override
            public void onClick(View view) {
                listener.movieClicked(movieList.get(getAdapterPosition()).getId());
            }
    }

}
