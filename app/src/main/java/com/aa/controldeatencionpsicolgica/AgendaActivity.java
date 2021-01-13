package com.aa.controldeatencionpsicolgica;

import android.content.Intent;
import android.os.Bundle;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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

        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente paciente = pacienteList.get(position);
                Intent intent = new Intent(getApplicationContext(), AgendaDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacienteData", paciente);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest("http://192.168.1.69/dif/listPacientes.php", response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    //Paciente p = new Paciente(pacObj.getString("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    //Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombre"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    pacienteList.add(p);
                }
                Pacientes_Adapter adapter = new Pacientes_Adapter(pacienteList, getApplicationContext());
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}