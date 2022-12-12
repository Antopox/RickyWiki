package com.example.rickywiki.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.example.rickywiki.R;
import com.example.rickywiki.io.HttpConnectRick;
import com.example.rickywiki.model.Personaje;
import com.example.rickywiki.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginRegisterActivity extends AppCompatActivity {

    private TextInputEditText txtUser;
    private TextInputEditText txtPassword;
    private Button btLogin;
    private Button btRegister;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        loadPreferences();

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPass);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        btRegister.setBackgroundColor(Color.GREEN);
        btLogin.setBackgroundColor(Color.GREEN);
        new taskConnections().execute("GET", "/character");
        btLogin.setOnClickListener(new View.OnClickListener() {
            //Cuando se pulse el boton de login se comprobara que los campos no esten vacios
            //Que el usuario exista en la base de datos y que la contraseña sea la misma que hay en la bd y ya permite la entrada
            @Override
            public void onClick(View view) {
                String textPas = String.valueOf(txtPassword.getText());
                String textUs = String.valueOf(txtUser.getText());

                if (textPas.equals("") || textUs.equals("")){
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }
                else{
                    List<Usuario> aux = Usuario.find(Usuario.class, "user = ?", textUs);
                    if(aux.isEmpty()){
                        Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if (textPas.equals(aux.get(0).getPassw())) {
                            Intent i = new Intent(LoginRegisterActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        //Cuando pulsamos el boton de registro se comprueba que los campos no esten vacios, que el nombre de usuario no exista en la base de datos
        // y ya se registra en la misma y permite el acceso
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textPas = String.valueOf(txtPassword.getText());
                String textUs = String.valueOf(txtUser.getText());
                if (textPas.equals("") || textUs.equals("")){
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }
                else{
                    List<Usuario> aux = Usuario.find(Usuario.class, "user = ?", textUs);
                    if (aux.isEmpty()) {
                        Usuario nuevo = new Usuario(textUs, textPas);
                        nuevo.save();
                        Intent i = new Intent(LoginRegisterActivity.this, MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Nombre de usuario no disponible", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("nombre_usuario", "");
        if (username != ""){
            Toast.makeText(getApplicationContext(), "Hola " + username + "!", Toast.LENGTH_LONG);
        }
    }

    private class taskConnections extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            switch (strings[0]){
                case "GET":
                    result = HttpConnectRick.getRequest(strings[1]);
                    break;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (s != null) {
                    Log.d("D", "Datos: " + s);

                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    Personaje aux = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject character = jsonArray.getJSONObject(i);
                        aux = new Personaje(character.getInt("id"
                        ), character.getString("name"), character.getString("status"),
                                character.getString("species"), character.getString("type"),
                                character.getString("gender"), character.getJSONObject("origin").getString("name"),
                                character.getJSONObject("location").getString("name"), character.getString("image"));

                        MainActivity.personajes.add(aux);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
