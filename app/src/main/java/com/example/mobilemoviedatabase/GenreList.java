package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobilemoviedatabase.models.Genre;

import java.util.ArrayList;

public class GenreList extends BaseActivity {
    private ListView listview;
    private MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
        movieService = new MovieService(getApplicationContext());

        listview = findViewById(R.id.listview);
        final ArrayList<Genre> list = movieService.GetAllGenres();

        final StableArrayAdapterMovieGenre adapter = new StableArrayAdapterMovieGenre(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        Button buttonAddGenre = (Button) findViewById(R.id.button_add_genre_id);
        buttonAddGenre.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddGenreForm.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        final ArrayList<Genre> list = movieService.GetAllGenres();
        final StableArrayAdapterMovieGenre adapter = new StableArrayAdapterMovieGenre(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }
}