package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobilemoviedatabase.models.BusinessEntity;
import com.example.mobilemoviedatabase.models.Genre;

public class DisplayBusinessEntityActivity extends AppCompatActivity {
    private MovieService movieService;
    private BusinessEntity businessEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_business_entity);

        movieService = new MovieService(getApplicationContext());
        Intent intent = getIntent();
        int businessActivityId = Integer.parseInt(intent.getStringExtra("business_activity_id"));
        businessEntity = movieService.GetBusinessEntityById(businessActivityId);

        TextView textView = findViewById(R.id.text_view_business_entity_name);
        textView.setText(businessEntity.getName());

        textView = findViewById(R.id.text_view_business_entity_description);
        textView.setText(businessEntity.getDescription());
    }
}