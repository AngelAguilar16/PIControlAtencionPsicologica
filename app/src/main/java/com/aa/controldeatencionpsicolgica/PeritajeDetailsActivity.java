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
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PeritajeDetailsActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView nombre, motivo, notas;
    Context context;

    public PeritajeDetailsActivity() {
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
        View v = inflater.inflate(R.layout.activity_peritaje_details, container, false);
        context = getContext();



        nombre = v.findViewById(R.id.lblNombre);
        motivo = v.findViewById(R.id.lblMotivo);
        notas = v.findViewById(R.id.lblnotas);

        Bundle objeto = getArguments();
        Peritaje peritaje = null;

        if(objeto != null){
            peritaje = (Peritaje) objeto.getSerializable("peritajeData");

            nombre.setText(peritaje.getPaciente());
            motivo.setText(peritaje.getMotivo_atencion());
            notas.setText(peritaje.getNotas_sesion());

        } else
            Toast.makeText(context, "El objeto es nulo", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = v.findViewById(R.id.fab);
        Peritaje finalPeritaje = peritaje;
        fab.setOnClickListener(view -> {
            /*Intent i = new Intent(context, EditPeritajeActivity.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("peritaje", finalPeritaje);
            i.putExtras(bundle);
            startActivity(i);*/
            EditPeritajeActivity fragment = new EditPeritajeActivity();
            Bundle args = new Bundle();
            args.putSerializable("peritaje", finalPeritaje);
            fragment.setArguments(args);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutP, fragment);
            transaction.commit();
        });

        return v;
    }
}