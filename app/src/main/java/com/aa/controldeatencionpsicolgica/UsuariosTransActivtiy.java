package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVPacientesAdapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVUsuariosAdapterTrans;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Usuario;
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

public class UsuariosTransActivtiy extends AppCompatActivity {


    List<Paciente> pacienteList;
    List<Usuario> usuarioList;
    RecyclerView rvPacientes, rvUsuarios;
    List<Paciente> pacientes ;
    List<Usuario> usuarios;
    private int id_user, id_paciente;
    Button btnpacientes, btnusuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_trans_activtiy);
        btnpacientes = (Button) findViewById(R.id.pacientet);
        btnusuarios = (Button) findViewById(R.id.usuariot);

        rvPacientes = findViewById(R.id.rvPacientes);
        rvPacientes.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvPacientes.setLayoutManager(llm);
        pacientes = new ArrayList<>();
        pacienteList = new ArrayList<>();

        rvUsuarios = findViewById(R.id.rvUsuario);
        rvUsuarios.setHasFixedSize(true);
        LinearLayoutManager llmm = new LinearLayoutManager(this);
        rvUsuarios.setLayoutManager(llmm);
        usuarios = new ArrayList<>();

        usuarioList = new ArrayList<>();


        if (Global.paciente != null){ showPaciente(); }

        if (Global.usuario != null){ showUsuario(); }
    }

    private void showPaciente(){
        StringRequest stringRequest = new StringRequest(Global.ip + "getPaciente.php?id_paciente="+ Global.paciente.getId(), response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"), pacObj.getInt("caso"));
                    pacientes.add(p);
                }
                RVPacientesAdapter adapter = new RVPacientesAdapter(pacientes);
                rvPacientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void showUsuario() {
        StringRequest stringRequest = new StringRequest(Global.ip + "getUsuarioT.php?id=" + Global.usuario.getId_usuario(), response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("Usuario");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject pacObj = array.getJSONObject(i);
                    Usuario p = new Usuario(pacObj.getInt("id_usuario"), pacObj.getString("nombres"), pacObj.getString("ap"), pacObj.getString("am"), pacObj.getString("correo"), pacObj.getString("password"), pacObj.getString("tipo_usuario"));
                    usuarios.add(p);
                }
                RVUsuariosAdapterTrans adapter = new RVUsuariosAdapterTrans(usuarios);
                rvUsuarios.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> {
        });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void selPaciente(View view) {
        Intent i = new Intent(UsuariosTransActivtiy.this, ListaTPacientes.class);
        startActivity(i);
    }

    public void selUsuario(View view) {
        Intent i = new Intent(UsuariosTransActivtiy.this, ListaTUsuarios.class);
        startActivity(i);
    }

    public void transferirPaciente(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Global.ip + "transferirPaciente.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UsuariosTransActivtiy.this, "Paciente transferido a " + Global.usuario.getNombre(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuMaterial.class);
                Global.clearPacienteTrans();
                Global.clearUsuarioTrans();
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UsuariosTransActivtiy.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id_usuario", Integer.toString(Global.usuario.getId_usuario()));
                parametros.put("id_paciente", Integer.toString(Global.paciente.getId()));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UsuariosTransActivtiy.this);
        requestQueue.add(stringRequest);
    }
}