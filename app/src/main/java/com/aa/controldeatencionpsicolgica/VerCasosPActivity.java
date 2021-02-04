package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.Casos_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Casos;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerCasosPActivity extends AppCompatActivity {

    private ListView lvCasos;
    private List<Casos> casosList;
    private int id_caso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_casos_p);

        lvCasos = findViewById(R.id.lvCasos);

        casosList = new ArrayList<>();
        showList();

        lvCasos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Le envio el id del caso para en detalles se muesten las personas en el caso
                Casos caso = casosList.get(position);
                Intent intent = new Intent(VerCasosPActivity.this, PacientesPCasosActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("casoData", caso);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showList() {
        StringRequest stringRequest = new StringRequest(Global.ip+"listCasos.php", response -> {
            try{
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("casosList");
                for(int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Casos caso = new Casos(pacObj.getInt("id_caso"), pacObj.getString("fecha_apertura"), pacObj.getString("descripcion_general"), pacObj.getInt("estado"));
                    casosList.add(caso);
                }
                Casos_Adapter adapter = new Casos_Adapter(casosList, getApplicationContext());
                lvCasos.setAdapter(adapter);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {});
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}