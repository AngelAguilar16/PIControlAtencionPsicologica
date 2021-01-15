package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;

import java.util.List;

public class Expediente_Adapter extends ArrayAdapter<Expediente> {

    private List<Expediente> expedienteList;
    private Context mCtx;

    public Expediente_Adapter(List<Expediente> E, Context c){
        super(c, R.layout.list_expedientes, E);
        this.expedienteList = E;
        this.mCtx = c;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_expedientes, null, true);
        TextView expediente = view.findViewById(R.id.tvExpedientes);
        Expediente expediente1 = expedienteList.get(position);

        expediente.setText(expediente1.getMotivo_atencion());

        return view;
    }
}