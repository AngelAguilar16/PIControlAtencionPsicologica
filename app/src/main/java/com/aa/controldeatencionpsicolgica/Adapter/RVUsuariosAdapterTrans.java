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

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Usuario;
import com.aa.controldeatencionpsicolgica.R;
import com.aa.controldeatencionpsicolgica.UsuariosTransActivtiy;

import java.util.List;

public class RVUsuariosAdapterTrans extends RecyclerView.Adapter<RVUsuariosAdapterTrans.PacienteViewHolder> {

    List<Usuario> usuarios;
    Context context;

    public RVUsuariosAdapterTrans(List<Usuario> usuarios){
        this.usuarios = usuarios;
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
        holder.nombre.setText("Nombre: " + usuarios.get(position).getNombre() + " " + usuarios.get(position).getAp() + " " + usuarios.get(position).getAm());
        holder.telefono.setText("Área: " + usuarios.get(position).getTipo_usuario());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = usuarios.get(position);
                Intent intent = new Intent(context, UsuariosTransActivtiy.class);
                Global.setUsuarioTans(usuario);
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
        return usuarios.size();
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