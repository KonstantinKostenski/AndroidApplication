package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobilemoviedatabase.models.Movie;

public class DisplayMovieInformation extends BaseActivity {
    private MovieService movieService;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieService = new MovieService(getApplicationContext());
        setContentView(R.layout.activity_display_movie_information);
        Intent intent = getIntent();
        int movieId = Integer.parseInt(intent.getStringExtra("movie_id"));
        movie = movieService.GetMovieById(movieId);

        TextView textView = findViewById(R.id.text_view_movie_name);
        textView.setText(movie.getTitle());

        textView = findViewById(R.id.text_view_movie_description);
        textView.setText(movie.getDescription());
    }

    public void editMovie(View view){
        Intent intent = new Intent(view.getContext(), AddMovieForm.class);
        intent.putExtra("MOVIE", movie);
        startActivity(intent);
    }
}