package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.controldeatencionpsicolgica.AgendaDetailsActivity;
import com.aa.controldeatencionpsicolgica.ListaPacientesPeritaje;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.R;

import java.util.List;

public class RVPacientesPAdapter extends RecyclerView.Adapter<RVPacientesPAdapter.PacientePViewHolder>{

    List<Paciente_peritaje> pacientes;
    Context context;

    public RVPacientesPAdapter(List<Paciente_peritaje> pacientes){
        this.pacientes = pacientes;
    }

    @NonNull
    @Override
    public RVPacientesPAdapter.PacientePViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pacientes, parent, false);
        RVPacientesPAdapter.PacientePViewHolder pvh = new RVPacientesPAdapter.PacientePViewHolder(v);
        context = v.getContext();
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVPacientesPAdapter.PacientePViewHolder holder, int position) {
        holder.nombre.setText("Nombre: " + pacientes.get(position).getNombres() + " " + pacientes.get(position).getAp() + " " + pacientes.get(position).getAm());
        holder.telefono.setText("Telefono: " + pacientes.get(position).getCaso());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paciente_peritaje paciente = pacientes.get(position);
                Intent intent = new Intent(context, PacientesPeritajeDetails.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacienteData", paciente);
                intent.putExtras(bundle);
                context.startActivity(intent);
                /*Paciente paciente = pacientes.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pacienteData", paciente);
                EditPacienteDialog dialogFragment = new EditPacienteDialog();
                FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                dialogFragment.setArguments(bundle);
                ft.replace(R.id.frameLayout, dialogFragment);
                ft.commit();
                Fragment prev = ((AppCompatActivity)context).getSupportFragmentManager().findFragmentByTag("dialog");
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

    public static class PacientePViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView nombre;
        TextView telefono;

        PacientePViewHolder(@NonNull View itemView) {
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
