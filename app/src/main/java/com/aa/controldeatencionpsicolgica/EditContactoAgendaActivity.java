package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditContactoAgendaActivity extends AppCompatActivity {

    private TextView nombres;
    private EditText telefono, estado;
    private EditText muncipio, domicilio, sexo;
    private EditText fecNac, estCiv, escolaridad, ocupacion;
    private Button btnActualizar;

    private String URL = "http://192.168.1.68/dif/updatePacientes.php";

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
        btnActualizar = findViewById(R.id.btnEditarContacto);

        Bundle objeto = getIntent().getExtras();
        Paciente paciente = null;

        if(objeto != null){
            paciente = (Paciente) objeto.getSerializable("pacienteData1");

            Toast.makeText(EditContactoAgendaActivity.this, "El ID es: " + paciente.getId(), Toast.LENGTH_SHORT).show();

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

        }

        Paciente finalPaciente = paciente;

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EditContactoAgendaActivity.this, "Datos actualizados!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditContactoAgendaActivity.this, AgendaActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditContactoAgendaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<>();

                        parametros.put("id", Integer.toString(finalPaciente.getId()));
                        parametros.put("telefono", telefono.getText().toString());
                        parametros.put("estado", estado.getText().toString());
                        parametros.put("municipio", muncipio.getText().toString());
                        parametros.put("domicilio", domicilio.getText().toString());
                        parametros.put("sexo", sexo.getText().toString());
                        parametros.put("fecNac", fecNac.getText().toString());
                        parametros.put("estCiv", estCiv.getText().toString());
                        parametros.put("escolaridad", escolaridad.getText().toString());
                        parametros.put("ocupacion", ocupacion.getText().toString());

                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(EditContactoAgendaActivity.this);
                requestQueue.add(stringRequest);
            }
        });

    }
}