package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Citas_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CitasActivity extends AppCompatActivity {

    ListView lvCitas;
    List<Cita> citasList;
    ArrayList<Paciente> pacienteList;
    int us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        FloatingActionButton add = findViewById(R.id.add);
        lvCitas = (ListView) findViewById(R.id.lvCitas);

        citasList = new ArrayList<>();
        pacienteList = new ArrayList<>();
        cargarSP();
        getUsuarios();
        add.setOnClickListener(view -> {
            Intent i = new Intent(CitasActivity.this, AddNewCitaActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("arraylist",(Serializable)pacienteList);
            i.putExtra("pacienteList", args);
            startActivity(i);
        });

        showList();

        lvCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CitasActivity.this, ReporteCita.class);
                Cita cita = citasList.get(position);
                i.putExtra("paciente",cita.getPaciente());
                i.putExtra("cita", cita.getId());
                startActivity(i);
            }
        });
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.68/dif/listCitas.php?usuario=" + us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("citasList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Cita c = new Cita(pacObj.getInt("id_cita"), pacObj.getString("fecha"), pacObj.getString("hora"), pacObj.getInt("paciente"), pacObj.getInt("usuario"), pacObj.getInt("asistio"));
                    citasList.add(c);
                }
                Citas_Adapter adapter = new Citas_Adapter(citasList, getApplicationContext());
                lvCitas.setAdapter(adapter);
                //Toast.makeText(CitasActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(CitasActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void cargarSP() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        us = preferences.getInt("id", 0);

    }

    private void getUsuarios(){
        StringRequest stringRequest = new StringRequest("http://192.168.1.68/dif/listPacientes.php?usuario="+us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    //Paciente p = new Paciente(pacObj.getString("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    //Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombre"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    pacienteList.add(p);
                }
                //Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}