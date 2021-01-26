package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Sender.SenderPeritaje;
import com.aa.controldeatencionpsicolgica.Sender.SenderReporte;

public class PeritajeActivity extends AppCompatActivity {
    Button btnCloseReporte, btnGuardarDatosConsulta;
    EditText editTextPaciente,editTextMotivo, editTextConsulta;


    String urlAddress= Global.ip + "addPeritaje.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peritaje);

        editTextPaciente = (EditText) findViewById(R.id.editTextPacienteP);
        editTextMotivo = (EditText) findViewById(R.id.editTextMotivoP);
        editTextConsulta = (EditText) findViewById(R.id.editTextConsultaP);

        btnCloseReporte = findViewById(R.id.btnCloseReporteP);
        btnGuardarDatosConsulta = findViewById(R.id.btnGuardarDatosConsultaP);


        //Toast.makeText(ReporteCita.this,nombre + "",Toast.LENGTH_LONG).show();
        btnCloseReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PeritajeActivity.this, MenuActivityPeritaje.class);
                startActivity(i);
                finish();
            }
        });

        btnGuardarDatosConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SenderPeritaje s = new SenderPeritaje(PeritajeActivity.this, urlAddress, Global.us, editTextPaciente, editTextMotivo, editTextConsulta);
                s.execute();
            }
        });
    }
}