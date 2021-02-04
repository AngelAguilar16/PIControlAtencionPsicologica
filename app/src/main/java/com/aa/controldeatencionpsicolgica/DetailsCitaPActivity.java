package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.PacientesP_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailsCitaPActivity extends AppCompatActivity {

    private TextView textViewFecha, textViewHora;
    private Button btnAddPacientes, btnSesiones;

    private ListView lvPacientes;
    private List<Paciente_peritaje> pacienteList;

    private String fecha, hora;
    private int paciente, cita, id_global, id_paciente = 0, usuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cita_p);

        textViewFecha = findViewById(R.id.textViewFecha);
        textViewHora = findViewById(R.id.textViewHora);
        btnAddPacientes = findViewById(R.id.btnAddPacienteCita);
        lvPacientes = findViewById(R.id.lvPacientesCitas);
        btnSesiones = findViewById(R.id.btnSesion);

        fecha = getIntent().getStringExtra("fecha");
        hora = getIntent().getStringExtra("hora");
        paciente = getIntent().getIntExtra("paciente", 1);
        cita = getIntent().getIntExtra("cita", 0);
        id_global = getIntent().getIntExtra("id_global", 0);

        textViewHora.setText(hora);
        textViewFecha.setText(fecha);

        pacienteList = new ArrayList<>();
        showList();

        btnAddPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectPacienteActivity.class); //FALTA!!!!!!!!!!!!!

                intent.putExtra("fecha", fecha);
                intent.putExtra("hora", hora);
                intent.putExtra("id_paciente", id_paciente);
                intent.putExtra("usuario", usuario);
                intent.putExtra("id_global",id_global);

                startActivity(intent);
            }
        });

        btnSesiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PeritajeActivity.class);
                intent.putExtra("listaPacientes", (Serializable) pacienteList);
                intent.putExtra("nombres", pacienteList.get(id_global).getNombres());
                startActivity(intent);
            }
        });

    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesCitaP.php?id_global="+ id_global, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("citasList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    // Crear adaptador para Paciente pero solo usando
                    // nombre, ap, am, id_global, paciente;
                    id_paciente = pacObj.getInt("id_pacp");
                    usuario = pacObj.getInt("usuario");
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"),pacObj.getInt("id_global"), pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getInt("usuario"));
                    pacienteList.add(p);
                }
                PacientesP_Adapter adapter = new PacientesP_Adapter(pacienteList, getApplicationContext());
                lvPacientes.setAdapter(adapter);
                Toast.makeText(DetailsCitaPActivity.this,"Funcion Activada" + id_paciente,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}