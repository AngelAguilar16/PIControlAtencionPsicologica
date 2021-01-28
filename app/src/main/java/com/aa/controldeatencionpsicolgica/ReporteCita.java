package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Sender.SenderReporte;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReporteCita extends AppCompatActivity {

    Button btnCloseReporte, btnGuardarDatosConsulta;
    EditText editTextPaciente,editTextMotivo, editTextConsulta;
    String pa, t_us;
    int paciente, cita, usuario;

    String urlAddress= Global.ip + "addReporte.php";

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
                Intent i = new Intent(ReporteCita.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnGuardarDatosConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdateCitas();

                cita = getIntent().getIntExtra("cita", 0);
                SenderReporte s = new SenderReporte(ReporteCita.this, urlAddress, usuario, cita, paciente, t_us, editTextMotivo, editTextConsulta);
                s.execute();

            }
        });
    }

    private void setUpdateCitas(){
        StringRequest request = new StringRequest(Request.Method.POST, Global.ip + "updateCitas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ReporteCita.this, "Cita Actualizada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReporteCita.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", Integer.toString(cita));
                parametros.put("asistio", "1");
                parametros.put("visible", "0");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReporteCita.this);
        requestQueue.add(request);
    }

    private void queryPaciente(int paciente){
        StringRequest stringRequest = new StringRequest(Global.ip + "getPaciente.php?id_paciente="+ paciente, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"), pacObj.getInt("caso"));
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