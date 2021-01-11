package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class CitasActivity extends AppCompatActivity {

    ListView lvCitas;
    List<Cita> citasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        FloatingActionButton add = findViewById(R.id.add);
        lvCitas = (ListView) findViewById(R.id.lvCitas);

        citasList = new ArrayList<>();

        add.setOnClickListener(view -> {
            Intent i = new Intent(CitasActivity.this, AddNewCitaActivity.class);
            startActivity(i);
        });

        showList();


    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.69/dif/listCitas.php", response -> {
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


}