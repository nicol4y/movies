package com.example.movies_nicolay_nacaro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.example.movies_nicolay_nacaro.adapters.MovieAdapter;
import com.example.movies_nicolay_nacaro.databinding.ActivityMainBinding;
import com.example.movies_nicolay_nacaro.databinding.NowPlayingBinding;
import com.example.movies_nicolay_nacaro.fragments.NowPlaying;
import com.example.movies_nicolay_nacaro.models.MovieResponse;
import com.example.movies_nicolay_nacaro.models.Movies;
import com.example.movies_nicolay_nacaro.network.API;
import com.example.movies_nicolay_nacaro.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NowPlayingBinding playingBinding;
    private API api;
    private MovieAdapter adapter;
    private List<Movies> moviesList = new ArrayList<Movies>();
    public static ArrayList<Movies> realMovies = new ArrayList<Movies>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
playingBinding = NowPlayingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = binding.bottomNavView;

        NavigationUI.setupWithNavController(bottomNav, navController);
        this.api = RetrofitClient.getInstance().getAPI();
        Call<MovieResponse> requested = api.getAllMovies();
        requested.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() == false) {
                    Log.d("x", "Error from API with response code: " + response.code());

                }
                MovieResponse obj = response.body();
                moviesList = obj.getResults();

for(Movies m : moviesList)Log.d("mama",m.getName()) ;

realMovies.addAll(moviesList);
NowPlaying.adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }
}