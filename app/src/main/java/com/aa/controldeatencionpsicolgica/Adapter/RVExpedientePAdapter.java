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
import com.aa.controldeatencionpsicolgica.ExpedientePActivity;
import com.aa.controldeatencionpsicolgica.ExpedientePDetailsActivity;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.R;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RVExpedientePAdapter extends RecyclerView.Adapter<RVExpedientePAdapter.ExpedienteViewHolder> {

    List<Paciente_peritaje> expedientes;
    Context context;
    private List<Paciente_peritaje> pacienteList;
    int us;

    public RVExpedientePAdapter(List<Paciente_peritaje> expedientes){
        this.expedientes = expedientes;
    }

    @NonNull
    @Override
    public ExpedienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pacientes, parent, false);
        ExpedienteViewHolder evh = new ExpedienteViewHolder(v);
        context = v.getContext();
        pacienteList = new ArrayList<>();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVExpedientePAdapter.ExpedienteViewHolder holder, int position) {
        holder.nombre.setText("Nombre: " + expedientes.get(position).getNombres() + " " + expedientes.get(position).getAp() + " " + expedientes.get(position).getAm());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paciente_peritaje paciente = pacienteList.get(position);
                Intent intent = new Intent(context, ExpedientePDetailsActivity.class);
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

        showList();
    }

    @Override
    public int getItemCount() {
        return expedientes.size();
    }

    public static class ExpedienteViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView nombre;
        TextView telefono;

        ExpedienteViewHolder(@NonNull View itemView) {
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

    private void showList() {
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesP.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"), pacObj.getString("nombres"), pacObj.getString("ap"), pacObj.getString("am"));
                    pacienteList.add(p);
                }
                PacientesP_Adapter adapter = new PacientesP_Adapter(pacienteList, context);
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