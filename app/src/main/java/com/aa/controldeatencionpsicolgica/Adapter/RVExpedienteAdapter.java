package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.R;

import java.util.List;

public class RVExpedienteAdapter extends RecyclerView.Adapter<RVExpedienteAdapter.ExpedienteViewHolder> {

    List<Expediente> expedientes;
    Context context;

    public RVExpedienteAdapter(List<Expediente> expedientes){
        this.expedientes = expedientes;
    }

    @NonNull
    @Override
    public ExpedienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_expedientes, parent, false);
        ExpedienteViewHolder evh = new ExpedienteViewHolder(v);
        context = v.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVExpedienteAdapter.ExpedienteViewHolder holder, int position) {
        holder.paciente.setText("Paciente: " + expedientes.get(position).getPaciente());
        holder.fecha.setText("Fecha: " + expedientes.get(position).getFecha());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Paciente paciente = pacientes.get(position);
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
        return expedientes.size();
    }

    public static class ExpedienteViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView paciente;
        TextView fecha;

        ExpedienteViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvExpediente);
            paciente = itemView.findViewById(R.id.cvTxtPE);
            fecha = itemView.findViewById(R.id.cvTxtFE);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
