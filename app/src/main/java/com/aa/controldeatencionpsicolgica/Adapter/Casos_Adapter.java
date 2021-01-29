package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aa.controldeatencionpsicolgica.Model.Casos;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;

import java.util.List;

public class Casos_Adapter extends ArrayAdapter<Casos> {

    private List<Casos> casosList;
    private Context mCtx;


    public Casos_Adapter(List<Casos> P, Context c) {
        super(c, R.layout.list_casos, P);
        this.casosList = P;
        this.mCtx = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_casos, null, true);

        TextView descripcion = view.findViewById(R.id.lblDescripcion);
        Casos caso = casosList.get(position);

        descripcion.setText(caso.getDescripcion_general());

        return view;
    }
}
