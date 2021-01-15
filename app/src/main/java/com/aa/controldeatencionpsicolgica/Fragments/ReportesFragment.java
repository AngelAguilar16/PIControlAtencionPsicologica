package com.aa.controldeatencionpsicolgica.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aa.controldeatencionpsicolgica.CitasActivity;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.MainActivity;
import com.aa.controldeatencionpsicolgica.MenuActivity;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import com.aa.controldeatencionpsicolgica.ReporteCita;
import com.aa.controldeatencionpsicolgica.Sender.SenderReporte;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnCloseReporte, btnGuardarDatosConsulta;
    EditText editTextPaciente,editTextMotivo, editTextConsulta;
    String pa, t_us;
    int paciente, cita, usuario, caso = 1;
    String urlAddress="http://192.168.1.78/dif/addReporte.php";
    Button btnCSM;
    Context context;

    public ReportesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportesFragment newInstance(String param1, String param2) {
        ReportesFragment fragment = new ReportesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reportes, container, false);
        context = getContext();
        editTextPaciente = (EditText) v.findViewById(R.id.editTextPaciente);
        editTextMotivo = (EditText) v.findViewById(R.id.editTextMotivo);
        editTextConsulta = (EditText) v.findViewById(R.id.editTextConsulta);

        btnCloseReporte = v.findViewById(R.id.btnCloseReporte);
        btnGuardarDatosConsulta = v.findViewById(R.id.btnGuardarDatosConsulta);
        cargarSP();
        paciente = 1;
        queryPaciente(paciente);


        btnGuardarDatosConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cita = 1;
                SenderReporte s = new SenderReporte(context, urlAddress, usuario, cita, caso, paciente, t_us, editTextMotivo, editTextConsulta);
                s.execute();
            }
        });

        return v;
    }

    private void queryPaciente(int paciente){
        StringRequest stringRequest = new StringRequest("http://192.168.1.78/dif/getPaciente.php?id_paciente="+ paciente, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    pa = p.getNombre() + " " + p.getAp() + " " + p.getAm();
                    editTextPaciente.setText(pa);
                }
                //Toast.makeText(ReporteCita.this,"Funcion activada" + pa,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                //Toast.makeText(ReporteCita.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void cargarSP() {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        usuario = preferences.getInt("id", 0);
        t_us = preferences.getString("t_us", "a");
    }
}