package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReporteCita extends AppCompatActivity {

    Button btnCloseReporte;
    EditText editTextPaciente,editTextMotivo, editTextConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_cita);
        editTextPaciente = (EditText) findViewById(R.id.editTextPaciente);
        editTextMotivo = (EditText) findViewById(R.id.editTextMotivo);
        editTextConsulta = (EditText) findViewById(R.id.editTextConsulta);

        btnCloseReporte = findViewById(R.id.btnCloseReporte);

        String nombre = getIntent().getStringExtra("paciente");

        editTextPaciente.setText(nombre);

        //Toast.makeText(ReporteCita.this,nombre + "",Toast.LENGTH_LONG).show();
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