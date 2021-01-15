package com.aa.controldeatencionpsicolgica.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aa.controldeatencionpsicolgica.Adapter.Expediente_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVExpedienteAdapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVPacientesAdapter;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.R;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpedientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpedientesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Expediente> expedientesList;
    RecyclerView rvExpedientes;
    Context context;
    int us;

    public ExpedientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpedientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpedientesFragment newInstance(String param1, String param2) {
        ExpedientesFragment fragment = new ExpedientesFragment();
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
        View v = inflater.inflate(R.layout.fragment_expedientes, container, false);

        context = getContext();

        rvExpedientes = v.findViewById(R.id.rvExpedientes);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rvExpedientes.setLayoutManager(llm);
        expedientesList = new ArrayList<>();
        cargarSP();
        showList();

        return v;
    }

    private void showList() {
        StringRequest stringRequest = new StringRequest("http://192.168.1.78/dif/listExpediente.php?paciente="+ 1, response -> {
            try{
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("expedienteList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Expediente expediente = new Expediente(pacObj.getInt("id_consulta"), pacObj.getInt("usuario"), pacObj.getInt("cita"),
                            pacObj.getInt("caso"), pacObj.getInt("paciente"), pacObj.getString("fecha"), pacObj.getString("hora"),
                            pacObj.getString("motivo_atencion"), pacObj.getString("notas_sesion"), pacObj.getString("tipo_consulta"));
                    expedientesList.add(expediente);
                }
                RVExpedienteAdapter adapter = new RVExpedienteAdapter(expedientesList);
                rvExpedientes.setAdapter(adapter);

            } catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {});
        Handler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void cargarSP() {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        us = preferences.getInt("id", 0);
    }
}