package com.aa.controldeatencionpsicolgica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

public class AgendaDetailsActivity extends AppCompatActivity {

    private TextView nombre, apellidos, telefono, direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_details);

        nombre = findViewById(R.id.lblNombre);
        apellidos = findViewById(R.id.lblApellidos);
        telefono = findViewById(R.id.lblTelefono);
        direccion = findViewById(R.id.lblDomicilio);

        Bundle objeto = getIntent().getExtras();
        Paciente paciente = null;

        if(objeto != null){
            paciente = (Paciente) objeto.getSerializable("pacienteData");
            nombre.setText(paciente.getNombre());
            apellidos.setText(paciente.getAp() + " " + paciente.getAm());
            telefono.setText(paciente.getTelefono());
            direccion.setText(paciente.getDomicilio());
        } else
            Toast.makeText(AgendaDetailsActivity.this, "El objeto es nulo", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(AgendaDetailsActivity.this, EditContactoAgendaActivity.class);
            startActivity(i);
        });
    }
}