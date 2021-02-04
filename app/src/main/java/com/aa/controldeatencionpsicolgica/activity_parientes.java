package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.Citas_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pariente_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Pariente;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class activity_parientes extends AppCompatActivity {

    ListView lvParientes;
    List<Pariente> parientesList;
    ArrayList<Paciente> pacienteList;
    int id_paciente;
    Button btnAddPariente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parientes);

        lvParientes = findViewById(R.id.lvParientes);
        btnAddPariente = findViewById(R.id.btnAddPariente);



        parientesList = new ArrayList<>();

        Bundle objeto = getIntent().getExtras();
        Paciente paciente = null;

        if(objeto != null){
            paciente = (Paciente) objeto.getSerializable("pacienteData1");
            //Toast.makeText(EditContactoAgendaActivity.this, "El ID es: " + paciente.getId(), Toast.LENGTH_SHORT).show();
            id_paciente = paciente.getId();
        }

        pacienteList = Global.getPacientes(getApplicationContext());
        btnAddPariente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity_parientes.this, AddNewParienteActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("arraylist",(Serializable)pacienteList);
                i.putExtra("pacienteList", args);
                i.putExtra("id_paciente", id_paciente);
                startActivity(i);
            }
        });

        showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listParientes.php?paciente="+ id_paciente, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("parientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Pariente p = new Pariente(pacObj.getInt("id"),pacObj.getInt("id_paciente"), pacObj.getInt("id_paciente_1"),pacObj.getString("tipo"));
                    parientesList.add(p);
                }
                Pariente_Adapter adapter = new Pariente_Adapter(parientesList, getApplicationContext());
                lvParientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}