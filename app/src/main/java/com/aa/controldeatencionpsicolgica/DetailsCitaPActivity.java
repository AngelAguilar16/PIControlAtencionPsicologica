package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.PacientesP_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsCitaPActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView textViewFecha, textViewHora;
    private Button btnAddPacientes, btnSesiones, btndeleteCita;

    private ListView lvPacientes;
    private List<Paciente_peritaje> pacienteList;

    private String fecha, hora;
    private int paciente, cita, id_global, id_paciente = 0, usuario = 0;
    Context context;

    public DetailsCitaPActivity() {
        // Required empty public constructor
    }

    public static DetailsCitaPActivity newInstance(String param1, String param2) {
        DetailsCitaPActivity fragment = new DetailsCitaPActivity();
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
        View v = inflater.inflate(R.layout.activity_details_cita_p, container, false);

        context = getContext();


        textViewFecha = v.findViewById(R.id.textViewFecha);
        textViewHora = v.findViewById(R.id.textViewHora);
        btnAddPacientes = v.findViewById(R.id.btnAddPacienteCita);
        lvPacientes = v.findViewById(R.id.lvPacientesCitasP);
        btnSesiones = v.findViewById(R.id.btnSesion);
        btndeleteCita = v.findViewById(R.id.btndeleteCita);

        fecha = getArguments().getString("fecha");
        hora = getArguments().getString("hora");
        cita = getArguments().getInt("cita",0);
        usuario = getArguments().getInt("usuario",0);

        textViewHora.setText(hora);
        textViewFecha.setText(fecha);

        pacienteList = new ArrayList<>();
        showList();

        btnAddPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mandarlo a SelectPacienteActivityP
                Bundle args = new Bundle();
                args.putInt("id_cita", cita);
                SelectPacienteActivityP fragment = new SelectPacienteActivityP();
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutP, fragment);
                transaction.commit();
            }
        });

        btnSesiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PeritajeActivity.class);
                intent.putExtra("listaPacientes", (Serializable) pacienteList);
                intent.putExtra("paciente", paciente);
                intent.putExtra("cita", cita);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        btndeleteCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Global.ip + "deleteCita.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Cita Eliminada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MenuMaterialPeritaje.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<>();
                        parametros.put("id_cita", Integer.toString(cita));
                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });

        return v;
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesCitaP.php?id_cita="+ cita, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("citasList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    // Crear adaptador para Paciente pero solo usando
                    // nombre, ap, am, id_global, paciente;
                    //id_paciente = pacObj.getInt("id_pacp");
                    //usuario = pacObj.getInt("usuario");
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_citap"), pacObj.getString("nombres"),
                                                                pacObj.getString("ap"), pacObj.getString("am"));
                    pacienteList.add(p);
                }
                PacientesP_Adapter adapter = new PacientesP_Adapter(pacienteList, context);
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(DetailsCitaPActivity.this,"Funcion Activada" + id_paciente,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }
}