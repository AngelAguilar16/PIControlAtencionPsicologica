package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CitasDiaActivity extends AppCompatActivity {

    String dia;
    TextView tvDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_dia);

        dia = getIntent().getStringExtra("dia");

        tvDia = findViewById(R.id.tvDia);

        if(dia.equals("28")) tvDia.setText("28 de Noviembre");
        if(dia.equals("29")) tvDia.setText("29 de Noviembre");
        if(dia.equals("30")) tvDia.setText("30 de Noviembre");
    }
}