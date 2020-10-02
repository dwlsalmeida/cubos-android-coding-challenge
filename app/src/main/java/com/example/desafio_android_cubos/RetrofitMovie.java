package com.example.desafio_android_cubos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitMovie {
    @GET("movie?api_key="+Movie.API_KEY+"&language=pt-BR")
    Call<MovieResult> getMovies(@Query("with_genres") String genreID);
}
