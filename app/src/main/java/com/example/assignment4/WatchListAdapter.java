package com.example.assignment4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WatchListAdapter extends
        RecyclerView.Adapter<WatchListAdapter.TasksViewHolder>{

    interface moviesClickListener {
        public void movieClicked(Movie movie);
    }

    moviesClickListener listener;
    private Context cntx;
    public List<Movie> movieList;

    public WatchListAdapter(Context cntx, List<Movie> movieList) {
        this.cntx = cntx;
        this.movieList = movieList;
        this.listener = (WatchListActivity) cntx;
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

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }
            @Override
            public void onClick(View view) {
                listener.movieClicked(movieList.get(getAdapterPosition()).getMovie());
            }
    }

}
