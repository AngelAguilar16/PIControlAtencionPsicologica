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
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CitasActivity extends AppCompatActivity {

    Button btn28, btn29, btn30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        FloatingActionButton add = findViewById(R.id.add);

        btn28 = findViewById(R.id.btn28);
        btn29 = findViewById(R.id.btn29);
        btn30 = findViewById(R.id.btn30);

        add.setOnClickListener(view -> {
            Intent i = new Intent(CitasActivity.this, AddNewCitaActivity.class);
            startActivity(i);
        });
    }

    public void seeCitas(View view){
        Intent i = new Intent(CitasActivity.this, CitasDiaActivity.class);
        if(view.getId() == R.id.btn28) i.putExtra("dia", btn28.getText().toString());
        if(view.getId() == R.id.btn29) i.putExtra("dia", btn29.getText().toString());
        if(view.getId() == R.id.btn30) i.putExtra("dia", btn30.getText().toString());
        startActivity(i);
    }

}