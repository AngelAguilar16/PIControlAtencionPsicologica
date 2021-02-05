package com.aa.controldeatencionpsicolgica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.aa.controldeatencionpsicolgica.Adapter.RVPacientesAdapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class AgendaActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Paciente> pacienteList;
    RecyclerView rvPacientes;
    List<Paciente> pacientes ;
    Context context;
    ImageButton btnCSA;

    public AgendaActivity(){
    }

    public static AgendaActivity newInstance(String param1, String param2) {
        AgendaActivity fragment = new AgendaActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_agenda, container, false);
        context = getContext();

        FloatingActionButton fab = v.findViewById(R.id.fab);
        rvPacientes = v.findViewById(R.id.rvPacientes);
        btnCSA = v.findViewById(R.id.btnCSA);


        rvPacientes.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rvPacientes.setLayoutManager(llm);
        pacientes = new ArrayList<>();

        pacienteList = new ArrayList<>();

        fab.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), AddNewContactoAgenda.class);
            startActivity(i);
        });

        btnCSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBtnA();
            }
        });

        showList();

        return v;
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientes.php?usuario="+ Global.us, response -> {
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
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void logoutBtnA() {
        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("s_ini", Boolean.FALSE);
        editor.commit();

        SharedPreferences pref = getActivity().getSharedPreferences("a", Context.MODE_PRIVATE);

        SharedPreferences.Editor e = pref.edit();

        e.putString("u", "false");
        e.commit();

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }

}