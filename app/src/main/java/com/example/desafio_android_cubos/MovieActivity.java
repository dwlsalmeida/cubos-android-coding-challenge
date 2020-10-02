package com.example.desafio_android_cubos;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {
    private ImageView mPoster;
    private TextView mOverview;

    private void loadLayout() {
        Intent i = getIntent();
        Movie m = (Movie) i.getSerializableExtra(Movie.SERIALIZABLE_KEY);
        getSupportActionBar().setTitle(m.getTitle());
        Picasso.get().load(Movie.BASE_URL_IMG + m.getPosterPath()).into(mPoster);
        mOverview.setText(m.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPoster = findViewById(R.id.movie_activity_poster);
        mOverview = findViewById(R.id.movie_activity_overview);

        loadLayout();

    }
}