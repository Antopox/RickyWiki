package com.example.rickywiki.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rickywiki.R;
import com.example.rickywiki.model.Personaje;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgChar;
    private TextView txtName;
    private TextView txtStatus;
    private TextView txtSpecies;
    private TextView txtType;
    private TextView txtGender;
    private TextView txtOrigin;
    private TextView txtLocation;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Con la posicion del personaje que hemos pulsado lo sacamos de nuestro arraylist y cambiamos
        //nuestros textView e imageView dependiendo de la seleccion
        Intent i = getIntent();
        Personaje character = MainActivity.personajes.get(i.getIntExtra("POS", 0));

        imgChar = findViewById(R.id.characterDetailImage);
        txtLocation = findViewById(R.id.txtLocationDetail);
        txtOrigin = findViewById(R.id.txtOriginDetail);
        txtSpecies = findViewById(R.id.txtSpeciesDetail);
        txtGender = findViewById(R.id.txtGenderDetail);
        txtName = findViewById(R.id.txtNameDetail);
        txtStatus = findViewById(R.id.txtStatusDetail);
        txtType = findViewById(R.id.txtTypeDetail);

        Glide.with(getApplicationContext()).load(character.getImage()).into(imgChar);
        txtName.setText(character.getName());
        txtType.setText(txtType.getText() + character.getType());
        txtStatus.setText(txtStatus.getText() + character.getStatus());
        txtOrigin.setText(txtOrigin.getText() + character.getOrigin());
        txtLocation.setText(txtLocation.getText() + character.getLocation());
        txtGender.setText(txtGender.getText() + character.getGender());
        txtSpecies.setText(txtSpecies.getText() + character.getSpecies());

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
