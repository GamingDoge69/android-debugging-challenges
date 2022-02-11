package com.codepath.debuggingchallenges.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.debuggingchallenges.R;
import com.codepath.debuggingchallenges.adapters.MoviesAdapter;
import com.codepath.debuggingchallenges.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MoviesActivity extends AppCompatActivity {

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    RecyclerView rvMovies;
    MoviesAdapter adapter;
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        rvMovies = findViewById(R.id.rvMovies);

        movies = new ArrayList<>();

        // Create the adapter to convert the array to views
        adapter = new MoviesAdapter(movies);

        // Attach the adapter to a ListView
        rvMovies.setAdapter(adapter);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layoutManager = (LinearLayoutManager) rvMovies.getLayoutManager();
        assert layoutManager != null;
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvMovies.getContext(),
                layoutManager.getOrientation());
        rvMovies.addItemDecoration(dividerItemDecoration);

        fetchMovies();
    }


    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=%s";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(url, API_KEY), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON response) {
                try {
                    JSONArray moviesJson = response.jsonObject.getJSONArray("results");
                    List<Movie> movieFromJson = Movie.fromJSONArray(moviesJson);
                    movies.addAll(movieFromJson);
                    adapter.notifyItemRangeInserted(0, movieFromJson.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(MoviesActivity.class.getSimpleName(), "Error retrieving movies: ", throwable);
            }
        });
    }
}
