package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.List;

public class ListaPacienteP extends AppCompatActivity {

    ListView lvPacientes;
    List<Paciente_peritaje> pacienteList;
    ArrayList<Paciente_peritaje> pList = new ArrayList<>();
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_paciente_p);
        lvPacientes = findViewById(R.id.lvPacientesCaso);
        pacienteList = new ArrayList<>();
        Bundle objeto = getIntent().getExtras();

        pList = (ArrayList<Paciente_peritaje>) objeto.getSerializable("pacientes");
        a = getIntent().getStringExtra("desc");
        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente_peritaje paciente = pacienteList.get(position);
                Intent intent = new Intent(getApplicationContext(), AddNewCasoP.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacienteData", paciente);
                bundle.putSerializable("pacientes", pList);
                intent.putExtras(bundle);
                intent.putExtra("desc", a);
                startActivity(intent);
                finish();
            }
        });
        showList();

    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesP.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("CURP"),pacObj.getInt("caso"));
                    if (pList.size() == 0) {
                        pacienteList.add(p);
                    } else {
                        if (!pList.contains(p)){
                            pacienteList.add(p);
                        }
                    }
                }
                PacientesP_Adapter adapter = new PacientesP_Adapter(pacienteList, getApplicationContext());
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(ListaPacientes.this,"" + pList.size(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}