package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobilemoviedatabase.models.Genre;
import com.example.mobilemoviedatabase.models.Movie;

public class DisplayGenreActivity extends AppCompatActivity {
    private MovieService movieService;
    private Genre genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_genre);

        movieService = new MovieService(getApplicationContext());
        Intent intent = getIntent();
        int genreId = Integer.parseInt(intent.getStringExtra("genre_id"));
        genre = movieService.GetGenreById(genreId);

        TextView textView = findViewById(R.id.text_view_genre_name);
        textView.setText(genre.getName());

        textView = findViewById(R.id.text_view_genre_description);
        textView.setText(genre.getDescription());
    }

    public void editGenre(View view){
        Intent intent = new Intent(view.getContext(), AddMovieForm.class);
        intent.putExtra("GENRE", genre);
        startActivity(intent);
    }
}