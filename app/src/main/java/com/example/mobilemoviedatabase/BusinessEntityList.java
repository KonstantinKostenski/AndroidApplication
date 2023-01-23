package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobilemoviedatabase.models.BusinessEntity;
import com.example.mobilemoviedatabase.models.Genre;
import com.example.mobilemoviedatabase.models.Movie;

import java.util.ArrayList;

public class BusinessEntityList extends BaseActivity {
    private MovieService movieService;
    private ArrayList<BusinessEntity> list;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_entity_list);

        listview = (ListView) findViewById(R.id.listview);

        movieService = new MovieService(getApplicationContext());
        list = movieService.GetAllBusinessEntities();;

        final StableArrayAdapterBusinessEntity adapter = new StableArrayAdapterBusinessEntity(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        Button buttonAddBusinessEntity = (Button) findViewById(R.id.button_add_business_entity_id);
        buttonAddBusinessEntity.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddBusinessEntity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        list = movieService.GetAllBusinessEntities();;

        final StableArrayAdapterBusinessEntity adapter = new StableArrayAdapterBusinessEntity(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }
}