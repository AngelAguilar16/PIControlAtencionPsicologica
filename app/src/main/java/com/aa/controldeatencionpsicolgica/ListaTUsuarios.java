package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.RVPacientesAdapterTrans;
import com.aa.controldeatencionpsicolgica.Adapter.RVUsuariosAdapterTrans;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Usuario;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaTUsuarios extends AppCompatActivity {


    ListView lvPacientes;
    List<Usuario> usuarioList;
    RecyclerView rvUsuarios;
    List<Usuario> usuarios;
    int us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_t_usuarios);


        rvUsuarios = findViewById(R.id.rvUsuario);
        rvUsuarios.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvUsuarios.setLayoutManager(llm);
        usuarios = new ArrayList<>();

        usuarioList = new ArrayList<>();


        showList();
    }

    private void showList() {
        StringRequest stringRequest = new StringRequest(Global.ip + "listUsuarios.php", response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("usuariosList");
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

}