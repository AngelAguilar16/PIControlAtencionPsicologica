package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aa.controldeatencionpsicolgica.Adapter.Expediente_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExpedientePDetailsActivity extends AppCompatActivity {

    private TextView lblNombre;
    private ListView lvExpediente;
    private List<Expediente> expedientesList;
    int us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente_p_details);

        lblNombre = findViewById(R.id.lblNombrePaciente);
        lvExpediente = findViewById(R.id.lvExpedientes);
        expedientesList = new ArrayList<>();

        Bundle objeto = getIntent().getExtras();
        Paciente_peritaje paciente = null;

        // Consigo los datos del paciente que quiero ver su expediente
        // Necesito el id_cita - > ExpedienteActivity

        if(objeto != null){
            paciente = (Paciente_peritaje) objeto.getSerializable("pacienteData");
            lblNombre.setText(paciente.getNombres());
            us = paciente.getId_pacp();

            showList();
        }

        lvExpediente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expediente expediente = expedientesList.get(position);
                Intent intent = new Intent(getApplicationContext(), ExpedienteDetailsActivity2.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("expedienteData", expediente);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    // Metodo para mostrar los reportes/citas del paciente
    private void showList() {
        //Toast.makeText(ExpedienteDetailsActivity.this, "Id apciente: "+ us, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Global.ip + "listExpedienteP.php?paciente="+ us, response -> {
            try{
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("expedienteList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Expediente expediente = new Expediente(pacObj.getInt("id_citap"), pacObj.getInt("usuario"),
                            pacObj.getInt("cita"), pacObj.getString("fecha"), pacObj.getString("hora"),
                            pacObj.getString("motivo_atencion"), pacObj.getString("notas_sesion"));
                    expedientesList.add(expediente);
                }
                Expediente_Adapter adapter = new Expediente_Adapter(expedientesList, getApplicationContext());
                lvExpediente.setAdapter(adapter);

            } catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {});
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }



}