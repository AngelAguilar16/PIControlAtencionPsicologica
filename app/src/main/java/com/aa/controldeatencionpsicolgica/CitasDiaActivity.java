package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Citas_Adapter;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CitasDiaActivity extends AppCompatActivity {

    String dia;
    TextView tvDia;
    ListView lvCitas;
    List<Cita> citaList;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_dia);

        dia = getIntent().getStringExtra("dia");

        fab = findViewById(R.id.fab);
        tvDia = findViewById(R.id.tvDia);
        lvCitas = findViewById(R.id.lvCitas);

        citaList = new ArrayList<>();

        if(dia.equals("28")) tvDia.setText("28 de Noviembre");
        if(dia.equals("29")) tvDia.setText("29 de Noviembre");
        if(dia.equals("30")) tvDia.setText("30 de Noviembre");

        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Botoncito Añadir Cita", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Intent i = new Intent(CitasDiaActivity.this, AddNewCitaActivity.class);
            startActivity(i);
        });

        showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://192.168.1.78/dif/listCitas.php", response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("citasList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Cita c = new Cita(pacObj.getString("id_cita"),pacObj.getString("fecha"),pacObj.getString("hora"),pacObj.getString("paciente"), pacObj.getString("nombre"), pacObj.getString("usuario"), pacObj.getString("asistio"));
                    citaList.add(c);
                }
                Citas_Adapter adapter = new Citas_Adapter(citaList, getApplicationContext());
                lvCitas.setAdapter(adapter);
                Toast.makeText(CitasDiaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Toast.makeText(CitasDiaActivity.this,"Funcion No Jalo",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}