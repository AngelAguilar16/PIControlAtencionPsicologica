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
import android.widget.ImageButton;

import com.aa.controldeatencionpsicolgica.Adapter.RVExpedienteAdapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVExpedientePAdapter;
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

public class ExpedientePActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rvExpedientes;
    private List<Paciente_peritaje> pacienteList;
    Context context;
    ImageButton btnCSE;

    public ExpedientePActivity() {
        // Required empty public constructor
    }

    public static ExpedientePActivity newInstance(String param1, String param2) {
        ExpedientePActivity fragment = new ExpedientePActivity();
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
        View v = inflater.inflate(R.layout.activity_expediente_p, container, false);
        context = getContext();
        rvExpedientes = v.findViewById(R.id.rvExpedientes);
        btnCSE = v.findViewById(R.id.btnCSE);

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

        btnCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBtnE();
            }
        });

        showList();

        return v;
    }

    private void showList() {
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesP.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"), pacObj.getString("nombres"), pacObj.getString("ap"), pacObj.getString("am"));
                    pacienteList.add(p);
                }
                RVExpedientePAdapter adapter = new RVExpedientePAdapter(pacienteList);
                rvExpedientes.setAdapter(adapter);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void logoutBtnE() {
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