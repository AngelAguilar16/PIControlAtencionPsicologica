package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.PacientesP_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPacienteActivityP extends AppCompatActivity {

    private ListView lvPacientes;
    private List<Paciente_peritaje> pacienteList;

    private String fecha, hora;
    private int id_paciente, id_cita;
    private int id_paciente1 = 0;

    private String URL = Global.ip + "addPacienteCitaP.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_paciente_p);

        lvPacientes = findViewById(R.id.lvPacientesCitasP);

        id_cita = getIntent().getIntExtra("id_cita", 0);

        pacienteList = new ArrayList<>();
        showList();

        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente_peritaje paciente = pacienteList.get(position);
                id_paciente1 = paciente.getId_pacp();

                AlertDialog.Builder alerta = new AlertDialog.Builder(SelectPacienteActivityP.this);
                alerta.setMessage("¿Deseas agregar al paciente ").setCancelable(true)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Aqui insertaremos los datos
                                addPacientes();
                                Intent intent = new Intent(getApplicationContext(), CitasPActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Agregar Paciente");
                titulo.show();
            }
        });
    }

    private void showList() {
        StringRequest stringRequest = new StringRequest(Global.ip + "listSelectPacientesP.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"), pacObj.getInt("usuario"), pacObj.getString("fecha_registro"),
                                                                pacObj.getString("nombres"), pacObj.getString("ap"), pacObj.getString("am"), pacObj.getString("sexo"),
                                                                pacObj.getString("fecha_nacimiento"), pacObj.getString("CURP"), pacObj.getInt("caso"));
                    pacienteList.add(p);
                }
                PacientesP_Adapter adapter = new PacientesP_Adapter(pacienteList, getApplicationContext());
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void addPacientes(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Paciente añadido!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectPacienteActivityP.this, CitasPActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectPacienteActivityP.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("id_paciente", Integer.toString(id_paciente1));
                parametros.put("id_cita", Integer.toString(id_cita));

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SelectPacienteActivityP.this);
        requestQueue.add(stringRequest);

    }
}