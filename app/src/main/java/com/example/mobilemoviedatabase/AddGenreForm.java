package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilemoviedatabase.models.Genre;
import com.example.mobilemoviedatabase.models.Movie;

public class AddGenreForm extends BaseActivity {
    private MovieService movieService;
    private Genre genre;
    private EditText inputGenreName;
    private EditText inputGenreDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_genre_form);
        movieService = new MovieService(getApplicationContext());
        genre = new Genre();
        inputGenreName = (EditText)findViewById(R.id.editTextName);
        inputGenreDescription = (EditText)findViewById(R.id.editTextDescription);
    }

    public void addGenre(View view) {
        if (isValid()){
            genre.setName(inputGenreName.getText().toString());
            genre.setDescription(inputGenreDescription.getText().toString());
            movieService.InsertGenre(genre);
            Intent intent = new Intent(view.getContext(), GenreList.class);
            startActivity(intent);
        }
    }

    private boolean isValid(){
        boolean isValid = true;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_LONG;

        if (inputGenreName.getText().toString() == null || inputGenreName.getText().toString().trim().isEmpty()){
            text = "Genre name is required!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputGenreName.getText().toString().trim().length() > 100){
            text = "Genre name should be at most 100 characters!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputGenreDescription.getText().toString() == null || inputGenreDescription.getText().toString().trim().isEmpty()){
            text = "Genre description is required!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputGenreDescription.getText().toString().trim().length() > 500){
            text = "Genre description should be at most 500 characters!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        return isValid;
    }
}