package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Bienvenido");
    }

    public void agendaBtn(View v){
        Intent i = new Intent(MenuActivity.this, AgendaActivity.class);
        startActivity(i);
    }

    public void citasBtn(View v){
        Intent i = new Intent(MenuActivity.this, CitasActivity.class);
        startActivity(i);
    }

}