package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import java.util.List;

public class Pacientes_Adapter extends ArrayAdapter<Paciente> {

    private List<Paciente> pacienteList;
    private Context mCtx;

    public Pacientes_Adapter(List<Paciente> P, Context c){
        super(c, R.layout.list_pacientes, P);
        this.pacienteList = P;
        this.mCtx = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_pacientes, null, true);

        TextView name = view.findViewById(R.id.tvNombres);
        Paciente paciente = pacienteList.get(position);

        name.setText(paciente.getNombre() + " " + paciente.getAp() + " " + paciente.getAm());

        return view;
    }
}
