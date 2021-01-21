package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Sender.SenderCaso;
import com.aa.controldeatencionpsicolgica.Sender.SenderLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.ArrayList;

public class AddNewCaso extends AppCompatActivity {

    EditText etDescCaso;
    Button btnCrearPacienteCaso, btnSelectPacienteCaso, btnCrearCaso;
    Paciente paciente = null;
    String urlAddress = Global.ip + "addCaso.php";
    ArrayList<Paciente> pac = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_caso);

        etDescCaso = (EditText) findViewById(R.id.etDescCaso);
        String a = getIntent().getStringExtra("desc");
        Bundle objeto = getIntent().getExtras();

        if(objeto != null){
            pac = (ArrayList<Paciente>) objeto.getSerializable("pacientes");
            paciente = (Paciente) objeto.getSerializable("pacienteData");
            pac.add(paciente);
        }
        if (a != null){ etDescCaso.setText(a); }

        btnCrearPacienteCaso = (Button) findViewById(R.id.btnCrearPacienteCaso);
        btnSelectPacienteCaso = (Button) findViewById(R.id.btnSelectPacienteCaso);
        btnCrearCaso = (Button) findViewById(R.id.btnCrearCaso);

        btnCrearPacienteCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSelectPacienteCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddNewCaso.this, ListaPacientes.class);
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
                SenderCaso s = new SenderCaso(AddNewCaso.this, urlAddress, etDescCaso.getText().toString(), pac);
                s.execute();
            }
        });
    }



}