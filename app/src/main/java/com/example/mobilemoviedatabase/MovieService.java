package com.example.mobilemoviedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobilemoviedatabase.models.BusinessEntity;
import com.example.mobilemoviedatabase.models.Genre;
import com.example.mobilemoviedatabase.models.Movie;
import com.example.mobilemoviedatabase.models.MovieBusinessEntity;
import com.example.mobilemoviedatabase.models.MovieGenre;

import java.util.ArrayList;

public class MovieService {
    private SqlLiteMovieDatabaseHelper dbHelper;
    private String MOVIE_TABLE = "MOVIE";
    private String GENRE_TABLE = "GENRE";
    private String BUSINESS_ENTITY_TABLE = "BUSINESS_ENTITY";
    public MovieService(Context context) {
        dbHelper = new SqlLiteMovieDatabaseHelper(context);
    }

    public Movie GetMovieById(int id){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query="SELECT * FROM MOVIE where id=?";
        Cursor data =db.rawQuery(query,new String[] {String.valueOf(id)});

        if(data != null)
        {
            Movie movie = new Movie();

            if (data.moveToFirst()) {
                movie.setId(Integer.parseInt(data.getString(0))); // Id
                movie.setTitle(data.getString(1)); // Name
                movie.setDescription(data.getString(2)); // Description
            }
            data.close();
            db.close();

            return movie;
        }

        return null;
    }

    public long InsertMovie(Movie movie){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("TITLE", movie.getTitle());
        values.put("DESCRIPTION", movie.getDescription());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("MOVIE", null, values);
        return newRowId;
    }

    public long UpdateMovie(Movie movie){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("TITLE", movie.getTitle());
        values.put("DESCRIPTION", movie.getDescription());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.update("MOVIE", values,  "ID = ?", new String[]{Integer.toString(movie.getId())});
        return newRowId;
    }

    public void DeleteMovie(int id){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("MOVIE_BUSINESS_ENTITY", "MOVIE_ID=" + id, null);
        db.delete("MOVIE_GENRE", "MOVIE_ID=" + id, null);
        db.delete("MOVIE", "ID=" + id, null);
        // Create a new map of values, where column names are the keys
    }

    public Genre GetGenreById(int id){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query="SELECT * FROM GENRE where id=?";
        Cursor data =db.rawQuery(query,new String[] {String.valueOf(id)});

        if(data != null)
        {
            Genre genre = new Genre();

            if (data.moveToFirst()) {
                genre.setId(Integer.parseInt(data.getString(0))); // Id
                genre.setName(data.getString(1)); // Name
                genre.setDescription(data.getString(2)); // Description
            }
            data.close();
            db.close();

            return genre;
        }

        return null;
    }

    public long InsertGenre(Genre genre){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("NAME", genre.getName());
        values.put("DESCRIPTION", genre.getDescription());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("GENRE", null, values);
        return newRowId;
    }

    public void DeleteGenre(int id){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("MOVIE_GENRE", "GENRE_ID=" + id, null);
        db.delete("GENRE", "ID=" + id, null);
        // Create a new map of values, where column names are the keys
    }

    public BusinessEntity GetBusinessEntityById(int id){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM BUSINESS_ENTITY where id=?";
        Cursor data = db.rawQuery(query,new String[] {String.valueOf(id)});

        if(data != null)
        {
            BusinessEntity businessEntity = new BusinessEntity();

            if (data.moveToFirst()) {
                businessEntity.setId(Integer.parseInt(data.getString(0))); // Id
                businessEntity.setName(data.getString(1)); // Name
                businessEntity.setDescription(data.getString(2)); // Description
            }
            data.close();
            db.close();

            return businessEntity;
        }

        return null;
    }

    public long InsertBusinessEntity(BusinessEntity businessEntity){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("NAME", businessEntity.getName());
        values.put("DESCRIPTION", businessEntity.getDescription());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("BUSINESS_ENTITY", null, values);
        return newRowId;
    }

    public void DeleteBusinessEntity(int id){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("MOVIE_BUSINESS_ENTITY", "BUSINESS_ENTITY_ID=" + id, null);
        db.delete("BUSINESS_ENTITY", "ID=" + id, null);
        // Create a new map of values, where column names are the keys
    }

    public long InsertMovieGenre(MovieGenre movieGenre){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("MOVIE_ID", movieGenre.getMovieId());
        values.put("GENRE_ID", movieGenre.getGenreId());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("MOVIE_GENRE", null, values);
        return newRowId;
    }

    public long DeleteMovieGenre(MovieGenre movieGenre){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.delete("MOVIE_GENRE", "GENRE_ID=" + movieGenre.getGenreId(), null);
        return newRowId;
    }

    public ArrayList<MovieGenre> GetMovieGenresByMovieId(int movieId){
        ArrayList<MovieGenre> list = new ArrayList<MovieGenre>();

        String selectQuery = "SELECT  * FROM MOVIE_GENRE WHERE MOVIE_ID = " + movieId;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        MovieGenre movieGenre = new MovieGenre();
                        //only one column
                        movieGenre.setMovieId(Integer.parseInt(cursor.getString(0)));
                        movieGenre.setGenreId(Integer.parseInt(cursor.getString(1)));
                        //you could add additional columns here..

                        list.add(movieGenre);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

    public ArrayList<MovieBusinessEntity> GetMovieBusinessEntitiesByMovieId(int movieId){
        ArrayList<MovieBusinessEntity> list = new ArrayList<MovieBusinessEntity>();

        String selectQuery = "SELECT  * FROM MOVIE_BUSINESS_ENTITY WHERE MOVIE_ID = " + movieId;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        MovieBusinessEntity movieBusinessEntity = new MovieBusinessEntity();
                        //only one column
                        movieBusinessEntity.setMovieId(Integer.parseInt(cursor.getString(0)));
                        movieBusinessEntity.setBusinessEntityId(Integer.parseInt(cursor.getString(1)));
                        //you could add additional columns here..

                        list.add(movieBusinessEntity);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

    public long InsertMovieBusinessEntity(MovieBusinessEntity movieBusinessEntity){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("MOVIE_ID", movieBusinessEntity.getMovieId());
        values.put("BUSINESS_ENTITY_ID", movieBusinessEntity.getBusinessEntityId());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("MOVIE_BUSINESS_ENTITY", null, values);
        return newRowId;
    }

    public long DeleteMovieBusinessEntity(MovieBusinessEntity movieBusinessEntity){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.delete("MOVIE_BUSINESS_ENTITY", "BUSINESS_ENTITY_ID=" + movieBusinessEntity.getBusinessEntityId(), null);
        return newRowId;
    }

    public ArrayList<BusinessEntity> GetAllBusinessEntities(){
        ArrayList<BusinessEntity> list = new ArrayList<BusinessEntity>();

        String selectQuery = "SELECT  * FROM " + BUSINESS_ENTITY_TABLE;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        BusinessEntity businessEntity = new BusinessEntity();
                        //only one column
                        businessEntity.setId(Integer.parseInt(cursor.getString(0)));
                        businessEntity.setName(cursor.getString(1));
                        businessEntity.setDescription(cursor.getString(2));
                        //you could add additional columns here..

                        list.add(businessEntity);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

    public ArrayList<Movie> GetAllMovies(){
        ArrayList<Movie> list = new ArrayList<Movie>();

        String selectQuery = "SELECT  * FROM " + MOVIE_TABLE;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Movie movie = new Movie();
                        //only one column
                        movie.setId(Integer.parseInt(cursor.getString(0)));
                        movie.setTitle(cursor.getString(1));
                        movie.setDescription(cursor.getString(2));
                        //you could add additional columns here..

                        list.add(movie);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

    public ArrayList<Genre> GetAllGenres(){
        ArrayList<Genre> list = new ArrayList<Genre>();

        String selectQuery = "SELECT  * FROM " + GENRE_TABLE;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Genre genre = new Genre();
                        //only one column
                        genre.setId(Integer.parseInt(cursor.getString(0)));
                        genre.setName(cursor.getString(1));
                        genre.setDescription(cursor.getString(2));
                        //you could add additional columns here..

                        list.add(genre);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }
}
