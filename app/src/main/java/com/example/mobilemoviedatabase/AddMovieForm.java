package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilemoviedatabase.models.BusinessEntity;
import com.example.mobilemoviedatabase.models.Genre;
import com.example.mobilemoviedatabase.models.Movie;
import com.example.mobilemoviedatabase.models.MovieBusinessEntity;
import com.example.mobilemoviedatabase.models.MovieGenre;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class AddMovieForm extends BaseActivity {
    private MovieService movieService;
    private Movie movie;
    private ArrayList<Genre> genres;
    private ArrayList<BusinessEntity> businessEntities;
    private ArrayList<MovieGenre> movieGenres;
    private ArrayList<MovieBusinessEntity> movieBusinessEntities;
    private EditText inputMovieName;
    private EditText inputMovieDescription;
    private Button formButton;
    private boolean editMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_form);
        movieService = new MovieService(getApplicationContext());



        movieGenres = new ArrayList<>();
        movieBusinessEntities = new ArrayList<>();
        inputMovieName = findViewById(R.id.editTextName);
        inputMovieDescription = findViewById(R.id.editTextDescription);
        formButton = findViewById(R.id.button);
        genres = movieService.GetAllGenres();
        MultiSpinner multiSpinnerGenres = findViewById(R.id.genre_list);
        movie = (Movie) getIntent().getSerializableExtra("MOVIE");
        ArrayList<Integer> selectedIndicesGenre = new ArrayList<>();
        ArrayList<Integer> selectedIndicesBusinessEntity = new ArrayList<>();

        if (movie == null){
            this.editMode = false;
            movie = new Movie();
        }else{
            inputMovieName.setText(movie.getTitle());
            inputMovieDescription.setText(movie.getDescription());
            ArrayList<MovieGenre> movieGenres = movieService.GetMovieGenresByMovieId(movie.getId());
            for (int i = 0; i < genres.size(); i++ ){
                Genre currentGenre = genres.get(i);
                for (int j = 0; j < movieGenres.size(); j++ ){
                    if (currentGenre.getId() == movieGenres.get(j).getGenreId()){
                        selectedIndicesGenre.add(i);
                        break;
                    }
                }

            }


        }

        formButton.setText(editMode ? getString(R.string.btn_edit): getString(R.string.add_movie));

        ArrayList<String> arraySpinner = new ArrayList<String>(genres.size());

        for (int i = 0; i < genres.size(); i++){
            arraySpinner.add(genres.get(i).getName());
        }

        multiSpinnerGenres.setItems(arraySpinner, getString(R.string.for_all), selected -> {
            for (int i = 0; i < selected.length; i++){
                if (selected[i]){
                    MovieGenre movieGenre = new MovieGenre();
                    movieGenre.setGenreId(genres.get(i).getId());
                    movieGenres.add(movieGenre);
                }
                else{
                    int genreId = genres.get(i).getId();
                    MovieGenre toRemove = null;
                    boolean deleted = false;

                    for (int j = 0; j < movieGenres.size(); j++) {
                        toRemove = movieGenres.get(j);
                        if (genreId == toRemove.getGenreId()){
                            movieService.DeleteMovieGenre(toRemove);
                            deleted = true;
                            break;
                        }
                    }

                    if (toRemove != null && deleted){
                        movieGenres.remove(toRemove);
                    }
                }
            }
        });

        multiSpinnerGenres.setSelected(selectedIndicesGenre);

        businessEntities = movieService.GetAllBusinessEntities();

        if (this.editMode){
            ArrayList<MovieBusinessEntity> movieBusinessEntities = movieService.GetMovieBusinessEntitiesByMovieId(movie.getId());
            for (int i = 0; i < businessEntities.size(); i++ ){
                BusinessEntity currentBusinessEntity = businessEntities.get(i);
                for (int j = 0; j < movieBusinessEntities.size(); j++ ){
                    if (currentBusinessEntity.getId() == movieBusinessEntities.get(j).getBusinessEntityId()){
                        selectedIndicesBusinessEntity.add(i);
                        break;
                    }
                }

            }
        }

        arraySpinner = new ArrayList<>(businessEntities.size());

        for (int i = 0; i < businessEntities.size(); i++){
            arraySpinner.add(businessEntities.get(i).getName());
        }

        MultiSpinner multiSpinnerBusinessEntities = findViewById(R.id.movie_business_entity_list);
        multiSpinnerBusinessEntities.setItems(arraySpinner, getString(R.string.for_all), selected -> {
            for (int i = 0; i < selected.length; i++){
                    if (selected[i]){
                        MovieBusinessEntity movieBusinessEntity = new MovieBusinessEntity();
                        movieBusinessEntity.setBusinessEntityId(businessEntities.get(i).getId());
                        movieBusinessEntities.add(movieBusinessEntity);
                    }
                    else{
                        int businessEntityId = businessEntities.get(i).getId();
                        MovieBusinessEntity toRemove = null;
                        boolean deleted = false;

                        for (int j = 0; j < movieBusinessEntities.size(); j++) {
                            toRemove = movieBusinessEntities.get(j);
                            if (businessEntityId == toRemove.getBusinessEntityId()){
                                movieService.DeleteMovieBusinessEntity(toRemove);
                                deleted = true;
                                break;
                            }
                        }

                        if (toRemove != null && deleted){
                            movieBusinessEntities.remove(toRemove);
                        }
                    }
            }
        });

        multiSpinnerBusinessEntities.setSelected(selectedIndicesBusinessEntity);

    }



    public void addMovie(View view) {
        if (isValid()){
            movie.setTitle(inputMovieName.getText().toString());
            movie.setDescription(inputMovieDescription.getText().toString());
            int movieId = 0;
            if(this.editMode){
                movieService.UpdateMovie(movie);
                movieId = movie.getId();
            }
            else{
                movieId = (int)movieService.InsertMovie(movie);
                movie.setId(movieId);
            }

            for(int i = 0; i < movieGenres.size(); i++){
                MovieGenre movieGenre = movieGenres.get(i);
                movieGenre.setMovieId(movieId);
                movieService.InsertMovieGenre(movieGenre);
            }

            for(int i = 0; i < movieBusinessEntities.size(); i++){
                MovieBusinessEntity movieBusinessEntity = movieBusinessEntities.get(i);
                movieBusinessEntity.setMovieId(movieId);
                movieService.InsertMovieBusinessEntity(movieBusinessEntity);
            }

            if (this.editMode){
                Intent openMovieInformationActivity = new Intent(this, DisplayMovieInformation.class);
                openMovieInformationActivity.putExtra("movie_id", Integer.toString(movie.getId()));
                startActivity(openMovieInformationActivity);
            }else {
                Intent openMovieListActivity = new Intent(this, MovieList.class);
                openMovieListActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMovieListActivity, 0);
            }

        }
    }

    private boolean isValid(){
        boolean isValid = true;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_LONG;

        if (inputMovieName.getText().toString() == null || inputMovieName.getText().toString().trim().isEmpty()){
            text = "Movie name is required!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }
        if (inputMovieDescription.getText().toString() == null || inputMovieDescription.getText().toString().trim().isEmpty()){
            text = "Movie description is required!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputMovieName.getText().toString().trim().length() > 100){
            text = "Movie name should be at most 100 characters!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputMovieDescription.getText().toString().trim().length() > 500){
            text = "Movie description should be at most 500 characters!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        return isValid;
    }
}