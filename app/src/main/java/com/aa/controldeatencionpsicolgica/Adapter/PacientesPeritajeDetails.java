package com.aa.controldeatencionpsicolgica.Adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.AgendaDetailsActivity;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.R;

import java.util.ArrayList;

public class PacientesPeritajeDetails extends AppCompatActivity {

    private TextView nombre, apellidos, telefono, direccion;
    private Button btnEliminar, btnTransferir, btnParentesco;

    private int id_paciente;
    private String URL = Global.ip + "deletePaciente.php";
    private Intent intent;
    ArrayList<Paciente> pacienteList;
    Paciente_peritaje paciente = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes_peritaje_details);

        nombre = findViewById(R.id.lblNombreP);
        apellidos = findViewById(R.id.lblApellidosP);


        Bundle objeto = getIntent().getExtras();


        if(objeto != null){
            paciente = (Paciente_peritaje) objeto.getSerializable("pacienteData");
            id_paciente = paciente.getId_pacp();
            nombre.setText(paciente.getNombres());
            apellidos.setText(paciente.getAp() + " " + paciente.getAm());
        } else
            Toast.makeText(PacientesPeritajeDetails.this, "El objeto es nulo", Toast.LENGTH_SHORT).show();
    }
}