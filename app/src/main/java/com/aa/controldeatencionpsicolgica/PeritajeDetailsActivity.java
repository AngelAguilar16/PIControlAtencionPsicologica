package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PeritajeDetailsActivity extends AppCompatActivity {

    TextView nombre, motivo, notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peritaje_details);

        nombre = findViewById(R.id.lblNombre);
        motivo = findViewById(R.id.lblMotivo);
        notas = findViewById(R.id.lblnotas);

        Bundle objeto = getIntent().getExtras();
        Peritaje peritaje = null;

        if(objeto != null){
            peritaje = (Peritaje) objeto.getSerializable("peritajeData");

            nombre.setText(peritaje.getPaciente());
            motivo.setText(peritaje.getMotivo_atencion());
            notas.setText(peritaje.getNotas_sesion());

        } else
            Toast.makeText(PeritajeDetailsActivity.this, "El objeto es nulo", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = findViewById(R.id.fab);
        Peritaje finalPeritaje = peritaje;
        fab.setOnClickListener(v -> {
            Intent i = new Intent(PeritajeDetailsActivity.this, EditPeritajeActivity.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("peritaje", finalPeritaje);
            i.putExtras(bundle);
            startActivity(i);
        });
    }
}