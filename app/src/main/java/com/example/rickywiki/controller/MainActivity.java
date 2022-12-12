package com.example.rickywiki.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rickywiki.R;
import com.example.rickywiki.adapter.RecyclerAdapter;
import com.example.rickywiki.io.HttpConnectRick;
import com.example.rickywiki.model.Personaje;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//En la actividad principal creamos una instancia de nuestro adaptador( al que le tenemos que pasar los datos) y un layoutmanager
//y se los añadiremos a nuestro recyclerView
public class MainActivity extends AppCompatActivity {

    RecyclerView recView;
    RecyclerAdapter recAdapter;
    public static ArrayList<Personaje> personajes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recView = findViewById(R.id.recView);
        recAdapter = new RecyclerAdapter(personajes);

        //Cuando pulsamos un registro del recyclerView cambiaremos a la actividad detalle pasando como extra la posicion del personaje
        // que hemos pulsado
        recAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                System.out.println(recView.getChildAdapterPosition(view));
                i.putExtra("POS", recView.getChildAdapterPosition(view));
                startActivity(i);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recView.setAdapter(recAdapter);
        recView.setLayoutManager(layoutManager);

    }


    @Override
    protected void onResume(){
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }
    //Iniciamos la actividad para los ajustes
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.settings:
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
        }
        return true;
    }


}