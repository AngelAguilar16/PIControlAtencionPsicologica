package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.R;

import java.util.List;

public class RVCitasAdapter extends RecyclerView.Adapter<RVCitasAdapter.CitaViewHolder> {

    List<Cita> citas;
    Context context;

    public RVCitasAdapter(List<Cita> citas){
        this.citas = citas;
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_citas, parent, false);
        RVCitasAdapter.CitaViewHolder cvh = new RVCitasAdapter.CitaViewHolder(v);
        context = v.getContext();
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVCitasAdapter.CitaViewHolder holder, int position) {
        holder.fecha.setText("Fecha: " + citas.get(position).getFecha());
        holder.hora.setText("Hora: " + citas.get(position).getHora());
        SharedPreferences prefs = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);
        holder.usuario.setText("Encargado: " + user);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cita cita = citas.get(position);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("citaData", cita);
                /*EditPacienteDialog dialogFragment = new EditPacienteDialog();
                FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                dialogFragment.setArguments(bundle);
                ft.replace(R.id.frameLayout, dialogFragment);
                ft.commit();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public static class CitaViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView fecha;
        TextView hora;
        TextView usuario;

        CitaViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvCita);
            fecha = itemView.findViewById(R.id.cvTxtFecha);
            hora = itemView.findViewById(R.id.cvTxtHora);
            usuario = itemView.findViewById(R.id.cvTxtUsuarioE);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
