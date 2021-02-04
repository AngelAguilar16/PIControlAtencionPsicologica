package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Pariente;
import com.aa.controldeatencionpsicolgica.R;

import java.util.ArrayList;
import java.util.List;

public class Pariente_Adapter extends ArrayAdapter<Pariente> {

    private List<Pariente> parientesList;
    private Context mCtx;

    public Pariente_Adapter(List<Pariente> P, Context c){
        super(c, R.layout.list_pacientes, P);
        this.parientesList = P;
        this.mCtx = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_pacientes, null, true);

        TextView name = view.findViewById(R.id.tvNombres);
        Pariente pariente = parientesList.get(position);

        name.setText(pariente.getPaciente() + " - " + pariente.getTipo());

        return view;
    }
}
