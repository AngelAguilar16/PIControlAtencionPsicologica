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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.aa.controldeatencionpsicolgica.AddNewCitaActivity;
import com.aa.controldeatencionpsicolgica.DetailsCitaActivity;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RVCitasAdapter extends RecyclerView.Adapter<RVCitasAdapter.CitaViewHolder> {

    List<Cita> citas;
    List<Cita> citasList;
    Context context;
    ArrayList<Paciente> pacienteList;
    private String fecha, hora;
    private Date date1, date2;
    DateFormat dateFormat;

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
        citasList = new ArrayList<>();
        pacienteList = new ArrayList<>();
        Date date = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecha = dateFormat.format(date);
        pacienteList = Global.getPacientes(context);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsCitaActivity.class);
                Cita cita = citasList.get(position);

                intent.putExtra("cita", cita.getId());
                intent.putExtra("fecha", cita.getFecha());
                intent.putExtra("hora", cita.getHora());
                intent.putExtra("usuario", cita.getUsuario());

                context.startActivity(intent);
                //bundle.putSerializable("citaData", cita);
                /*EditPacienteDialog dialogFragment = new EditPacienteDialog();
                FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                dialogFragment.setArguments(bundle);
                ft.replace(R.id.frameLayout, dialogFragment);
                ft.commit();*/
            }
        });
        showList();
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

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global.ip + "listCitas.php?usuario=" + Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("citasList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    if(pacObj.getInt("visible") == 1 && pacObj.getInt("asistio") == 0 ) {
                        date1 = dateFormat.parse(fecha);
                        date2 = dateFormat.parse(pacObj.getString("fecha"));
                        if(date1.compareTo(date2) > 0 )
                            System.out.println("Sumale 1 al contador si viste este mensaje"); // 1
                        else {
                            Cita c = new Cita(pacObj.getInt("id_cita"), pacObj.getString("fecha"), pacObj.getString("hora"),
                                    pacObj.getInt("usuario"), pacObj.getInt("asistio"));
                            citasList.add(c);
                        }
                    }
                }
                //Toast.makeText(CitasActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException | ParseException e) {
                //Toast.makeText(CitasActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }
}
