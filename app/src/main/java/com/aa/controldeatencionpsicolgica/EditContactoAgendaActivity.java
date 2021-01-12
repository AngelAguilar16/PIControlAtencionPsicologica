package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Model.Paciente;

public class EditContactoAgendaActivity extends AppCompatActivity {

    private TextView nombres;
    private EditText telefono, estado;
    private EditText muncipio, domicilio, sexo;
    private EditText fecNac, estCiv, escolaridad, ocupacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacto_agenda);

        nombres = findViewById(R.id.etNombres);
        telefono = findViewById(R.id.etTelefono);
        estado = findViewById(R.id.etEstado);
        muncipio = findViewById(R.id.etMunicipio);
        domicilio = findViewById(R.id.etDomicilio);
        sexo = findViewById(R.id.etSexo);
        fecNac = findViewById(R.id.etFecNac);
        estCiv = findViewById(R.id.etEstCiv);
        escolaridad = findViewById(R.id.etEscolaridad);
        ocupacion = findViewById(R.id.etOcupacion);

        Bundle objeto = getIntent().getExtras();
        Paciente paciente = null;

        if(objeto != null){
            paciente = (Paciente) objeto.getSerializable("pacienteData1");

            nombres.setText(paciente.getNombre() + " "+ paciente.getAp() + " " + paciente.getAm());
            telefono.setText(paciente.getTelefono());
            estado.setText(paciente.getEstado());
            muncipio.setText(paciente.getMunicipio());
            domicilio.setText(paciente.getDomicilio());
            sexo.setText(paciente.getSexo());
            fecNac.setText(paciente.getFecha_nacimiento());
            estCiv.setText(paciente.getEstado_civil());
            escolaridad.setText(paciente.getEscolaridad());
            ocupacion.setText(paciente.getOcupacion());

            Toast.makeText(getApplicationContext(), paciente.getNombre(), Toast.LENGTH_SHORT).show();
        }

    }
}