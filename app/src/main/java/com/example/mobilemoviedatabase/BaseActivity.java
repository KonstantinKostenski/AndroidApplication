package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_database_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.movie_list:
                startActivity(new Intent(this, MovieList.class));
                return true;
            case R.id.genre_list:
                startActivity(new Intent(this, GenreList.class));
                return true;
            case R.id.business_entity_list:
                startActivity(new Intent(this, BusinessEntityList.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}