package com.aa.controldeatencionpsicolgica.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.controldeatencionpsicolgica.ExpedienteDetailsActivity;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RVExpedienteAdapter extends RecyclerView.Adapter<RVExpedienteAdapter.ExpedienteViewHolder> {

    List<Expediente> expedientes;
    Context context;
    private List<Paciente> pacienteList;
    int us;

    public RVExpedienteAdapter(List<Expediente> expedientes){
        this.expedientes = expedientes;
    }

    @NonNull
    @Override
    public ExpedienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_expedientes, parent, false);
        ExpedienteViewHolder evh = new ExpedienteViewHolder(v);
        context = v.getContext();

        pacienteList = new ArrayList<>();
        cargarSP();
        showList();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVExpedienteAdapter.ExpedienteViewHolder holder, int position) {
        holder.paciente.setText("Paciente: " + expedientes.get(position).getPaciente());
        holder.fecha.setText("Fecha: " + expedientes.get(position).getFecha());
        holder.notas.setText("Notas: " + expedientes.get(position).getNotas_sesion());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Paciente paciente = pacienteList.get(position);
                Intent intent = new Intent(context, ExpedienteDetailsActivity.class);
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
        TextView notas;

        ExpedienteViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvExpediente);
            paciente = itemView.findViewById(R.id.cvTxtPE);
            fecha = itemView.findViewById(R.id.cvTxtFE);
            notas = itemView.findViewById(R.id.cvTxtNotas);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void showList() {
        StringRequest stringRequest = new StringRequest("http://192.168.1.78/dif/listPacientes.php?usuario="+ us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombre"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"), pacObj.getInt("caso"));
                    pacienteList.add(p);
                }
                //Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void cargarSP() {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        us = preferences.getInt("id", 0);
    }

}
