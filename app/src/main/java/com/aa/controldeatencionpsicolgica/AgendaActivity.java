package com.aa.controldeatencionpsicolgica;

import android.content.Intent;
import android.os.Bundle;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgendaActivity extends AppCompatActivity {

    ListView lvPacientes;
    List<Paciente> pacienteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        FloatingActionButton fab = findViewById(R.id.fab);
        lvPacientes = findViewById(R.id.lvPacientes);

        pacienteList = new ArrayList<>();

        fab.setOnClickListener(view -> {
            //Snackbar.make(view, "Botoncito Añadir", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Intent i = new Intent(AgendaActivity.this, AddNewContactoAgenda.class);
            startActivity(i);
        });

        showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://192.168.1.78/dif/listPacientes.php", response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getString("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("nombre_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    pacienteList.add(p);
                }
                Pacientes_Adapter adapter = new Pacientes_Adapter(pacienteList, getApplicationContext());
                lvPacientes.setAdapter(adapter);
                Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Toast.makeText(AgendaActivity.this,"Funcion No Jalo",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}