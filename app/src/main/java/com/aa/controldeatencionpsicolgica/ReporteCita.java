package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Variable;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Sender.SenderReg;
import com.aa.controldeatencionpsicolgica.Sender.SenderReporte;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReporteCita extends AppCompatActivity {

    Button btnCloseReporte, btnGuardarDatosConsulta;
    EditText editTextPaciente,editTextMotivo, editTextConsulta;
    String pa, t_us;
    int paciente, cita, usuario;

    String urlAddress= Variable.ip + "addReporte.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_cita);
        editTextPaciente = (EditText) findViewById(R.id.editTextPaciente);
        editTextMotivo = (EditText) findViewById(R.id.editTextMotivo);
        editTextConsulta = (EditText) findViewById(R.id.editTextConsulta);

        btnCloseReporte = findViewById(R.id.btnCloseReporte);
        btnGuardarDatosConsulta = findViewById(R.id.btnGuardarDatosConsulta);
        cargarSP();
        paciente = getIntent().getIntExtra("paciente", 0);
        queryPaciente(paciente);

        //Toast.makeText(ReporteCita.this,nombre + "",Toast.LENGTH_LONG).show();
        btnCloseReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReporteCita.this, CitasActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnGuardarDatosConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cita = getIntent().getIntExtra("cita", 0);
                SenderReporte s = new SenderReporte(ReporteCita.this, urlAddress, usuario, cita, paciente, t_us, editTextMotivo, editTextConsulta);
                s.execute();
            }
        });
    }

    private void queryPaciente(int paciente){
        StringRequest stringRequest = new StringRequest(Variable.ip + "getPaciente.php?id_paciente="+ paciente, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    pa = p.getNombre() + " " + p.getAp() + " " + p.getAm();
                    editTextPaciente.setText(pa);
                }
                //Toast.makeText(ReporteCita.this,"Funcion activada" + pa,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                //Toast.makeText(ReporteCita.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void cargarSP() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        usuario = preferences.getInt("id", 0);
        t_us = preferences.getString("t_us", "a");
    }
}