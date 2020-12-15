package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Bienvenido");
        cargarSp();
        Toast.makeText(MenuActivity.this, nombre + "", Toast.LENGTH_LONG).show();
    }

    public void cargarSp() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        nombre = preferences.getString("user", "Error");
    }

    public void agendaBtn(View v){
        Intent i = new Intent(MenuActivity.this, AgendaActivity.class);
        startActivity(i);
    }

    public void citasBtn(View v){
        Intent i = new Intent(MenuActivity.this, CitasActivity.class);
        startActivity(i);
    }

    public void logoutBtn(View view) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("s_ini", Boolean.FALSE);
        editor.commit();

        Intent i = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(i);
    }

}