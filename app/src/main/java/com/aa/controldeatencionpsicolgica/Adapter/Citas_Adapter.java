package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import java.util.List;

public class Citas_Adapter extends ArrayAdapter<Cita> {

    List<Cita> citaList;
    Context mCtx;

    public Citas_Adapter(List<Cita> C, Context ctx){
        super(ctx, R.layout.list_pacientes, C);
        this.citaList = C;
        this.mCtx = ctx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_pacientes, null, true);

        TextView name = view.findViewById(R.id.tvNombres);
        Cita cita = citaList.get(position);

        name.setText(cita.getFecha());

        return view;
    }
}
