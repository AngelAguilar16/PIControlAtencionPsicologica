package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsCitaActivity extends AppCompatActivity {

    private TextView textViewFecha, textViewHora;
    private Button btnAddPacientes, btnSesiones, btnBorrar;

    private ListView lvPacientes;
    private List<Paciente> pacienteList;

    private String fecha, hora;
    private int paciente, cita, id_global, id_paciente = 0, usuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cita);

        textViewFecha = findViewById(R.id.textViewFecha);
        textViewHora = findViewById(R.id.textViewHora);
        btnAddPacientes = findViewById(R.id.btnAddPacienteCita);
        lvPacientes = findViewById(R.id.lvPacientesCitasP);
        btnSesiones = findViewById(R.id.btnSesion);
        btnBorrar = findViewById(R.id.btndeleteCita);

        fecha = getIntent().getStringExtra("fecha");
        hora = getIntent().getStringExtra("hora");
        cita = getIntent().getIntExtra("cita", 0);
        usuario = getIntent().getIntExtra("usuario", 0);


        textViewHora.setText(hora);
        textViewFecha.setText(fecha);

        pacienteList = new ArrayList<>();
        showList();

        btnAddPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectPacienteActivity.class);

                intent.putExtra("id_cita", cita);
                startActivity(intent);
            }
        });

        btnSesiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReporteCita.class);

                // Datos que enviamos a ReporteCita
                intent.putExtra("listaPacientes", (Serializable) pacienteList); // Paciente o pacientes que toman la cita
                intent.putExtra("paciente", paciente);
                intent.putExtra("cita", cita); // id de cita
                intent.putExtra("usuario", usuario); //Quien atiende

                startActivity(intent);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Global.ip + "deleteCita.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailsCitaActivity.this, "Cita Eliminada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuMaterial.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailsCitaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<>();
                        parametros.put("id_cita", Integer.toString(cita));
                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(DetailsCitaActivity.this);
                requestQueue.add(stringRequest);
            }
        });

    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesCita.php?id_cita="+ cita, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("citasList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_cita"), pacObj.getString("nombres"),
                                                pacObj.getString("ap"), pacObj.getString("am"));
                    pacienteList.add(p);
                }
                Pacientes_Adapter adapter = new Pacientes_Adapter(pacienteList, getApplicationContext());
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}