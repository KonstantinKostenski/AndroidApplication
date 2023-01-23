package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobilemoviedatabase.models.Movie;

import java.util.ArrayList;

public class MovieList extends BaseActivity {
    private MovieService movieService;
    private ArrayList<Movie> list;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        listview = findViewById(R.id.listview);

        movieService = new MovieService(getApplicationContext());
        list = movieService.GetAllMovies();;

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(parent.getContext(), DisplayMovieInformation.class);
            intent.putExtra("movie_id", id);
            startActivity(intent);
        });

        Button buttonAddMovie = findViewById(R.id.button_add_movie_id);
        buttonAddMovie.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddMovieForm.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        list = movieService.GetAllMovies();;

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }
}