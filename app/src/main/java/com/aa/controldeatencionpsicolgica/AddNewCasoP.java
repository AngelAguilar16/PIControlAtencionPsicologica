package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.PacientesP_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.Sender.SenderCaso;
import com.aa.controldeatencionpsicolgica.Sender.SenderCasoP;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddNewCasoP extends AppCompatActivity {

    EditText etDescCaso;
    ListView lvPacientes;
    Button btnSelectPacienteCaso, btnCrearCaso, btnVerCasos;
    Paciente_peritaje paciente = null;
    String urlAddress = Global.ip + "addCaso.php";
    ArrayList<Paciente_peritaje> pac = new ArrayList<>();
    ArrayList<Paciente_peritaje> pacienteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_caso_p);

        etDescCaso = (EditText) findViewById(R.id.etDescCaso);
        lvPacientes = findViewById(R.id.lvPacientesSelec);
        String a = getIntent().getStringExtra("desc");
        Bundle objeto = getIntent().getExtras();

        if(objeto != null){
            pac = (ArrayList<Paciente_peritaje>) objeto.getSerializable("pacientes");
            paciente = (Paciente_peritaje) objeto.getSerializable("pacienteData");
            pac.add(paciente);
        }
        if (a != null){ etDescCaso.setText(a); }


        btnSelectPacienteCaso = (Button) findViewById(R.id.btnSelectPacienteCaso);
        btnCrearCaso = (Button) findViewById(R.id.btnCrearCaso);
        btnVerCasos = findViewById(R.id.btnVerCasos);
        showList();

        btnSelectPacienteCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddNewCasoP.this, ListaPacienteP.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacientes", pac);
                i.putExtras(bundle);
                i.putExtra("desc", etDescCaso.getText().toString());
                startActivity(i);
            }
        });

        btnCrearCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SenderCasoP s = new SenderCasoP(AddNewCasoP.this, urlAddress, etDescCaso.getText().toString(), pac);
                s.execute();
            }
        });

        btnVerCasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewCasoP.this, VerCasosPActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesP.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("CURP"),pacObj.getInt("caso"));

                    if (pac.contains(p)){
                        pacienteList.add(p);
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