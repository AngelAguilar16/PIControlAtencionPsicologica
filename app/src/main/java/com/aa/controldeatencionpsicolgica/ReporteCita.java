package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReporteCita extends AppCompatActivity {

    Button btnCloseReporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_cita);

        btnCloseReporte = findViewById(R.id.btnCloseReporte);

        btnCloseReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReporteCita.this, CitasActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}