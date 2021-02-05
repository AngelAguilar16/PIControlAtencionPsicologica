package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVExpedienteAdapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVPacientesAdapter;
import com.aa.controldeatencionpsicolgica.Fragments.ExpedientesFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExpedienteActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rvExpedientes;
    private List<Paciente> pacienteList;
    Context context;

    public ExpedienteActivity() {
        // Required empty public constructor
    }

    public static ExpedienteActivity newInstance(String param1, String param2) {
        ExpedienteActivity fragment = new ExpedienteActivity();
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
        View v = inflater.inflate(R.layout.activity_expediente, container, false);
        context = getContext();
        rvExpedientes = v.findViewById(R.id.rvExpedientes);

        pacienteList = new ArrayList<>();
        rvExpedientes.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rvExpedientes.setLayoutManager(llm);

        /*lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente paciente = pacienteList.get(position);
                Intent intent = new Intent(context, ExpedienteDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacienteData", paciente);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
        showList();

        return v;
    }

    private void showList() {
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientes.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),
                            pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"),
                            pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"),
                            pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"),
                            pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"),
                            pacObj.getInt("caso"));
                    pacienteList.add(p);
                }
                RVExpedienteAdapter adapter = new RVExpedienteAdapter(pacienteList);
                rvExpedientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }
}