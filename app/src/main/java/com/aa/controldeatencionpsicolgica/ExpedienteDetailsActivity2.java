package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.aa.controldeatencionpsicolgica.Model.Expediente;

public class ExpedienteDetailsActivity2 extends AppCompatActivity {

    private TextView lblMotivo, lblNotas, lblTipoConsulta, fecha, lblTramiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente_details2);

        lblNotas = findViewById(R.id.lblNotas);
        lblMotivo = findViewById(R.id.lblMotivo);
        lblTipoConsulta = findViewById(R.id.lblTipoConsulta);
        fecha = findViewById(R.id.lblFecha);
        lblTramiento = findViewById(R.id.lblTratamiento);

        Bundle objeto = getIntent().getExtras();
        Expediente expediente = null;

        if(objeto != null){
            expediente = (Expediente) objeto.getSerializable("expedienteData");
            lblTipoConsulta.setText(expediente.getTipo_consulta());
            lblNotas.setText(expediente.getNotas_sesion());
            lblMotivo.setText(expediente.getMotivo_atencion());
            fecha.setText(expediente.getFecha());

            if(!expediente.getTratamiento().isEmpty())
                lblTramiento.setText(expediente.getTratamiento());

        }

    }
}