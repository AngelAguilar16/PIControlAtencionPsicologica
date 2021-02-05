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
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditPeritajeActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText nombre, motivo, notas;
    Button close, editar;

    String URL = Global.ip + "updatePeritaje.php";
    Context context;

    public EditPeritajeActivity() {
        // Required empty public constructor
    }

    public static PeritajeDetailsActivity newInstance(String param1, String param2) {
        PeritajeDetailsActivity fragment = new PeritajeDetailsActivity();
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
        View v = inflater.inflate(R.layout.activity_edit_peritaje, container, false);
        context = getContext();


        nombre = v.findViewById(R.id.editTextPacienteP);
        motivo = v.findViewById(R.id.editTextMotivoP);
        notas = v.findViewById(R.id.editTextConsultaP);

        close = v.findViewById(R.id.btnCloseReporteP);
        editar = v.findViewById(R.id.btnGuardarDatosConsultaP);

        Bundle objeto = getArguments();
        Peritaje p = null;

        if(objeto != null){
            p = (Peritaje) objeto.getSerializable("peritaje");

            //Toast.makeText(EditContactoAgendaActivity.this, "El ID es: " + paciente.getId(), Toast.LENGTH_SHORT).show();

            nombre.setText(p.getPaciente());
            motivo.setText(p.getMotivo_atencion());
            notas.setText(p.getNotas_sesion());

        }

        Peritaje fp = p;

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Datos actualizados!", Toast.LENGTH_SHORT).show();
                        ListaPeritaje fragment = new ListaPeritaje();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayoutP, fragment);
                        transaction.commit();
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

                        parametros.put("id", Integer.toString(fp.getId_peritaje()));
                        parametros.put("nombre", nombre.getText().toString());
                        parametros.put("motivo", motivo.getText().toString());
                        parametros.put("notas", notas.getText().toString());

                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListaPeritaje fragment = new ListaPeritaje();
                Bundle args = new Bundle();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutP, fragment);
                transaction.commit();
            }
        });

        return v;
    }
}