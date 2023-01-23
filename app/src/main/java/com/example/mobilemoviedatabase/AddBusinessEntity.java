package com.example.mobilemoviedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilemoviedatabase.models.BusinessEntity;
import com.example.mobilemoviedatabase.models.Genre;

public class AddBusinessEntity extends BaseActivity {
    private MovieService movieService;
    private BusinessEntity businessEntity;
    private EditText inputBusinessEntityName;
    private EditText inputBusinessEntityDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business_entity);

        movieService = new MovieService(getApplicationContext());
        businessEntity = new BusinessEntity();
        inputBusinessEntityName = (EditText)findViewById(R.id.editTextName);
        inputBusinessEntityDescription = (EditText)findViewById(R.id.editTextDescription);
    }

    public void addBusinessEntity(View view) {
        if (isValid()){
            businessEntity.setName(inputBusinessEntityName.getText().toString());
            businessEntity.setDescription(inputBusinessEntityDescription.getText().toString());
            movieService.InsertBusinessEntity(businessEntity);
            Intent intent = new Intent(view.getContext(), BusinessEntityList.class);
            startActivity(intent);
        }
    }

    private boolean isValid(){
        boolean isValid = true;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_LONG;

        if (inputBusinessEntityName.getText().toString() == null || inputBusinessEntityName.getText().toString().trim().isEmpty()){
            text = "Business entity name is required!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputBusinessEntityName.getText().toString().trim().length() > 100){
            text = "Business entity name should be at most 100 characters!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputBusinessEntityDescription.getText().toString() == null || inputBusinessEntityDescription.getText().toString().trim().isEmpty()){
            text = "Business entity description is required!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        if (inputBusinessEntityDescription.getText().toString().trim().length() > 500){
            text = "Business entity description should be at most 500 characters!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            isValid = false;
        }

        return isValid;
    }
}