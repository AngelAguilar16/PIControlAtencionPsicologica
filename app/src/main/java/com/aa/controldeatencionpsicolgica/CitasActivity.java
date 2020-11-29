package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CitasActivity extends AppCompatActivity {

    Button btn28, btn29, btn30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        btn28 = findViewById(R.id.btn28);
        btn29 = findViewById(R.id.btn29);
        btn30 = findViewById(R.id.btn30);
    }

    public void seeCitas(View view){
        Intent i = new Intent(CitasActivity.this, CitasDiaActivity.class);
        if(view.getId() == R.id.btn28) i.putExtra("dia", btn28.getText().toString());
        if(view.getId() == R.id.btn29) i.putExtra("dia", btn29.getText().toString());
        if(view.getId() == R.id.btn30) i.putExtra("dia", btn30.getText().toString());
        startActivity(i);
    }
}