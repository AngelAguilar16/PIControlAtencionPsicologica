package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.PacientesP_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.CasoU;
import com.aa.controldeatencionpsicolgica.Model.Casos;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacientesPCasosActivity extends AppCompatActivity {

    private int id_caso;
    private ListView lvPacientes;
    private List<Paciente_peritaje> pacienteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes_p_casos);

        // Consigo los datos del activity "Ver Casos"
        Bundle objeto = getIntent().getExtras();
        CasoU caso = null;
        if(objeto != null){
            caso = (CasoU) objeto.getSerializable("casoData");
            id_caso = caso.getId_caso();
        }

        lvPacientes = findViewById(R.id.lvPacientesCasosDetalles);
        pacienteList = new ArrayList<>();

        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente_peritaje paciente = pacienteList.get(position);
                Intent intent = new Intent(getApplicationContext(), ExpedienteDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacienteData", paciente);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        showList();
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listCasosPacientesP.php?id_caso="+id_caso, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("CURP"),pacObj.getInt("caso"));
                    pacienteList.add(p);
                }
                PacientesP_Adapter adapter = new PacientesP_Adapter(pacienteList, getApplicationContext());
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void eliminarCaso(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Global.ip + "deleteCasoP.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PacientesPCasosActivity.this, "Caso Eliminado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuActivityPeritaje.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PacientesPCasosActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id_caso", Integer.toString(id_caso));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PacientesPCasosActivity.this);
        requestQueue.add(stringRequest);
    }

}