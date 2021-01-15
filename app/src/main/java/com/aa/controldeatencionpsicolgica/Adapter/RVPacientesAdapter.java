package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.controldeatencionpsicolgica.Dialogs.EditPacienteDialog;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;


import java.util.List;

public class RVPacientesAdapter extends RecyclerView.Adapter<RVPacientesAdapter.PacienteViewHolder> {

    List<Paciente> pacientes;
    Context context;

    public RVPacientesAdapter(List<Paciente> pacientes){
        this.pacientes = pacientes;
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pacientes, parent, false);
        PacienteViewHolder pvh = new PacienteViewHolder(v);
        context = v.getContext();
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        holder.nombre.setText("Nombre: " + pacientes.get(position).getNombre());
        holder.telefono.setText("Telefono: " + pacientes.get(position).getTelefono());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paciente paciente = pacientes.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pacienteData", paciente);
                EditPacienteDialog dialogFragment = new EditPacienteDialog();
                FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                dialogFragment.setArguments(bundle);
                ft.replace(R.id.frameLayout, dialogFragment);
                ft.commit();
                /*Fragment prev = ((AppCompatActivity)context).getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                dialogFragment.show(ft, "epd");
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                EditPacienteDialog.display(manager);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return pacientes.size();
    }

    public static class PacienteViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView nombre;
        TextView telefono;

        PacienteViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvPaciente);
            nombre = itemView.findViewById(R.id.cvTxtNombre);
            telefono = itemView.findViewById(R.id.cvTxtTelefono);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
