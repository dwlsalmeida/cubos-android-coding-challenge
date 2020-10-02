package com.example.desafio_android_cubos.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafio_android_cubos.Movie;
import com.example.desafio_android_cubos.MovieActivity;
import com.example.desafio_android_cubos.MovieResult;
import com.example.desafio_android_cubos.R;
import com.example.desafio_android_cubos.RetrofitMovie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment
        implements SearchView.OnQueryTextListener, Callback<MovieResult>,
        MovieRecyclerViewAdapter.OnMovieClickedListener {

    private static final String ARG_SECTION_NUMBER = "section_number";


    private Retrofit mRetrofit = null;
    private RetrofitMovie mRetrofitMovieApiHandle = null;
    private List<Movie> mMovies;
    private RecyclerView mRecyclerview = null;
    private String[] mGenreIDs = {Movie.GENRE_ID_ACTION,
            Movie.GENRE_ID_DRAMA, Movie.GENRE_ID_FANTASY, Movie.GENRE_ID_FICTION};
    private int mIndex = 1;
    private MovieRecyclerViewAdapter mMovieRecyclerViewAdapter = null;

    public String getGenreID() {
        return mGenreIDs[mIndex - 1];
    }

    @Override
    public void onMovieClicked(Movie m) {
        Intent i = new Intent(getActivity(), MovieActivity.class);
        i.putExtra(Movie.SERIALIZABLE_KEY, m);
        this.startActivity(i);
    }

    @Override
    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
        if (response.code() != 200) {
            Toast.makeText(getActivity(),
                    getString(R.string.api_error) + ": " + response.message(), Toast.LENGTH_LONG).
                    show();
            return;
        }

        MovieResult result = response.body();
        mMovies = result.getMovies();
        setupRecyclerView();
    }

    @Override
    public void onFailure(Call<MovieResult> call, Throwable t) {
        Toast.makeText(getActivity(),
                R.string.api_error + ": " + t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void setupRetrofit() {
        mRetrofit = new Retrofit.Builder().
        baseUrl(Movie.BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).build();

        mRetrofitMovieApiHandle = mRetrofit.create(RetrofitMovie.class);
    }

    private void setupRecyclerView() {
        mMovieRecyclerViewAdapter = new MovieRecyclerViewAdapter(mMovies, this);
        mRecyclerview.setAdapter(mMovieRecyclerViewAdapter);
    }

    public static MovieFragment newInstance(int index) {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             mIndex = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerview = root.findViewById(R.id.movie_recycler_view);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setupRetrofit();
        Call<MovieResult> call = mRetrofitMovieApiHandle.getMovies(getGenreID());
        call.enqueue(this);
        return root;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(mMovieRecyclerViewAdapter != null) {
            mMovieRecyclerViewAdapter.getFilter().filter(s);
        }
        return false;
    }
}