package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MenuActivityPeritaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_peritaje);
    }

    public void nuevoPeritaje(View view) {
        Intent i = new Intent(MenuActivityPeritaje.this, PeritajeActivity.class);
        startActivity(i);
    }

    public void reportesBtn(View view) {
    }

    public void logoutBtn(View view) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("s_ini", Boolean.FALSE);
        editor.commit();

        Intent i = new Intent(MenuActivityPeritaje.this, MainActivity.class);
        startActivity(i);
    }
}