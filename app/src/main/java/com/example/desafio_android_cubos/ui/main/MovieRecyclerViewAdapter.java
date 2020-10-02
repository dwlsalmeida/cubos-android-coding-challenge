package com.example.desafio_android_cubos.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desafio_android_cubos.Movie;
import com.example.desafio_android_cubos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter implements Filterable {
    private List<Movie> mMovies;
    private List<Movie> mAllMovies;
    private OnMovieClickedListener onMovieClickedListener;
    private Filter filter;

    @Override
    public Filter getFilter() {
        return filter;
    }

    public Movie getCurrentMovie(int position) {
        return mMovies.get(position);
    }

    public MovieRecyclerViewAdapter(List<Movie> movies, OnMovieClickedListener onMovieClickedListener) {
        mMovies = new ArrayList<>(movies);
        mAllMovies = new ArrayList<>(movies);
        this.onMovieClickedListener = onMovieClickedListener;
        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Movie> filteredMovies = new ArrayList<>();

                if (charSequence == null) {
                    filteredMovies.addAll(mMovies);
                } else {
                    for (Movie m : mAllMovies) {
                        if (m.getTitle().
                                toLowerCase().
                                contains(charSequence.toString().toLowerCase())) {
                            filteredMovies.add(m);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredMovies;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mMovies.clear();
                mMovies.addAll((Collection<? extends Movie>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.row_movie, parent, false);

        return new MovieViewHolder(v, onMovieClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder vh = (MovieViewHolder) holder;
        Movie m = mMovies.get(position);
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(Movie.BASE_URL_IMG+m.getPosterPath()).into(vh.mMoviePoster);
        vh.mMovieTitle.setText(m.getTitle());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mMoviePoster;
        public TextView mMovieTitle;
        private OnMovieClickedListener onMovieClickedListener;

        public MovieViewHolder(@NonNull View itemView, OnMovieClickedListener onMovieClickedListener) {
            super(itemView);
            this.onMovieClickedListener = onMovieClickedListener;
            itemView.setOnClickListener(this);
            mMoviePoster = itemView.findViewById(R.id.movie_poster);
            mMovieTitle = itemView.findViewById(R.id.movie_title);
        }

        @Override
        public void onClick(View view) {
            Movie m = getCurrentMovie(getAdapterPosition());
            onMovieClickedListener.onMovieClicked(m);
        }
    }

    public interface OnMovieClickedListener {
        void onMovieClicked(Movie m);
    }
}
