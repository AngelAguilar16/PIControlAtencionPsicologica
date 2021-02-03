package com.aa.controldeatencionpsicolgica.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aa.controldeatencionpsicolgica.Adapter.Citas_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVCitasAdapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVPacientesAdapter;
import com.aa.controldeatencionpsicolgica.Dialogs.AddCitaDialog;
import com.aa.controldeatencionpsicolgica.Dialogs.AddPacienteDialog;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rvCitas;
    Context context;
    ExtendedFloatingActionButton exfab;
    List<Cita> citasList;
    List<Paciente> pacientesList;

    public CitasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CitasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CitasFragment newInstance(String param1, String param2) {
        CitasFragment fragment = new CitasFragment();
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
        View v = inflater.inflate(R.layout.fragment_citas, container, false);
        context = getContext();
        citasList = new ArrayList<>();
        pacientesList = new ArrayList<>();
        exfab = v.findViewById(R.id.extFabAC);
        rvCitas = v.findViewById(R.id.rvCitas);

        exfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exfab.isExtended()){
                    openDialog();
                }
                else {
                    exfab.extend();
                }
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rvCitas.setLayoutManager(llm);
        //showList();
        return v;
    }

    /*private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.78/dif/listCitas.php?usuario=" + 3, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("citasList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Cita c = new Cita(pacObj.getInt("id_cita"), pacObj.getString("fecha"), pacObj.getString("hora"), pacObj.getInt("paciente"), pacObj.getInt("usuario"), pacObj.getInt("asistio"));
                    citasList.add(c);
                }
                RVCitasAdapter adapter = new RVCitasAdapter(citasList);
                rvCitas.setAdapter(adapter);
                //Toast.makeText(CitasActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(CitasActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }
    */

    public void cargarSP() {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        int us = preferences.getInt("id", 0);

    }

    private void getUsuarios(){
        StringRequest stringRequest = new StringRequest("http://192.168.1.78/dif/listPacientes.php?usuario="+3, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    //Paciente p = new Paciente(pacObj.getString("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    //Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombre"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"), pacObj.getInt("caso"));
                    pacientesList.add(p);
                }
                //Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }



    private void openDialog() {
        AddCitaDialog.display(getActivity().getSupportFragmentManager());
        /*Bundle bundle = new Bundle();
        bundle.putSerializable("pacienteData", (Serializable)pacientesList);
        AddCitaDialog dialogFragment = new AddCitaDialog();
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        dialogFragment.setArguments(bundle);
        ft.replace(R.id.frameLayout, dialogFragment);
        ft.commit();*/
    }

}