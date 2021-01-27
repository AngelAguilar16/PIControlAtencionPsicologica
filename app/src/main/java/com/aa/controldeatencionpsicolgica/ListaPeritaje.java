package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Peritaje_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPeritaje extends AppCompatActivity {

    ListView lvPeritaje;
    List<Peritaje> peritajeList;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_peritaje);
        FloatingActionButton fab = findViewById(R.id.fab);
        lvPeritaje = findViewById(R.id.lvPeritaje);
        back = findViewById(R.id.imageButton);

        peritajeList = new ArrayList<>();

        fab.setOnClickListener(view -> {
            Intent i = new Intent(ListaPeritaje.this, PeritajeActivity.class);
            startActivity(i);
        });

        lvPeritaje.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Peritaje peritaje = peritajeList.get(position);
                Intent intent = new Intent(getApplicationContext(), PeritajeDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("peritajeData", peritaje);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivityPeritaje.class);
                startActivity(intent);
                finish();
            }
        });
        showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPeritaje.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("peritajeList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Peritaje p = new Peritaje(pacObj.getInt("id_peritaje"),pacObj.getInt("usuario"),pacObj.getString("paciente"),pacObj.getString("fecha"),pacObj.getString("hora"),pacObj.getString("motivo_atencion"), pacObj.getString("notas_sesion"));
                    peritajeList.add(p);
                }
                Peritaje_Adapter a = new Peritaje_Adapter(peritajeList, getApplicationContext());
                lvPeritaje.setAdapter(a);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
