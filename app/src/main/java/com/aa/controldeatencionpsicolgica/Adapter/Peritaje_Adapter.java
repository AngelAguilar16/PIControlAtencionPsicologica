package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.aa.controldeatencionpsicolgica.R;
import java.util.List;

public class Peritaje_Adapter extends ArrayAdapter<Peritaje> {

    private List<Peritaje> peritajeList;
    private Context mCtx;

    public Peritaje_Adapter(List<Peritaje> C, Context ctx){
        super(ctx, R.layout.list_peritaje, C);
        this.peritajeList = C;
        this.mCtx = ctx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_peritaje, null, true);

        TextView name = view.findViewById(R.id.tvNombre);
        TextView fecha = view.findViewById(R.id.tvfecha);
        TextView hora = view.findViewById(R.id.tvhora);
        Peritaje per = peritajeList.get(position);

        name.setText(per.getPaciente());
        fecha.setText(per.getFecha());
        hora.setText(per.getHora());

        return view;
    }
}
