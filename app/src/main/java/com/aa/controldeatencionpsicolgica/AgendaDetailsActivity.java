package com.aa.controldeatencionpsicolgica;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgendaDetailsActivity extends AppCompatActivity {

    private TextView nombre, apellidos, telefono, direccion;
    private Button btnEliminar, btnTransferir, btnParentesco;

    private int id_paciente;
    private String URL = Global.ip + "deletePaciente.php";
    private Intent intent;
    ArrayList<Paciente> pacienteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_details);

        nombre = findViewById(R.id.lblNombre);
        apellidos = findViewById(R.id.lblApellidos);
        telefono = findViewById(R.id.lblTelefono);
        direccion = findViewById(R.id.lblDomicilio);
        btnEliminar = findViewById(R.id.btnEliminarPaciente);
        btnTransferir = findViewById(R.id.btnTrasferir);
        btnParentesco = findViewById(R.id.btnParentesco);

        FloatingActionButton fab = findViewById(R.id.fab);

        Bundle objeto = getIntent().getExtras();
        Paciente paciente = null;

        if(objeto != null){
            paciente = (Paciente) objeto.getSerializable("pacienteData");
            id_paciente = paciente.getId();
            nombre.setText(paciente.getNombre());
            apellidos.setText(paciente.getAp() + " " + paciente.getAm());
            telefono.setText(paciente.getTelefono());
            direccion.setText(paciente.getDomicilio());
        } else
            Toast.makeText(AgendaDetailsActivity.this, "El objeto es nulo", Toast.LENGTH_SHORT).show();

        Paciente finalPaciente = paciente;

        fab.setOnClickListener(v -> {
            Intent i = new Intent(AgendaDetailsActivity.this, EditContactoAgendaActivity.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("pacienteData1", finalPaciente);
            i.putExtras(bundle);
            startActivity(i);
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AgendaDetailsActivity.this, "Paciente Eliminado", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), AgendaActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgendaDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<>();
                        parametros.put("id_user", Integer.toString(id_paciente));
                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(AgendaDetailsActivity.this);
                requestQueue.add(stringRequest);
            }

        }); // Fin del setOnClickListener

        btnTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AgendaDetailsActivity.this, UsuariosTransActivtiy.class);
                intent.putExtra("id_paciente", id_paciente);
                startActivity(intent);
            }
        });


        btnParentesco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgendaDetailsActivity.this, activity_parientes.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pacienteData1", finalPaciente);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


    }
}