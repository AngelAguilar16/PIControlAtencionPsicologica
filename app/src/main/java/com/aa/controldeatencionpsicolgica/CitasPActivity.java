package com.aa.controldeatencionpsicolgica;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.Citas_Adapter;
import com.aa.controldeatencionpsicolgica.Fragments.ExpedientesFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class CitasPActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String fecha, hora;
    private Date date1, date2;
    DateFormat dateFormat;
    Context context;
    ListView lvCitas;
    List<Cita> citasList;
    ArrayList<Paciente_peritaje> pacienteList;

    public CitasPActivity() {
        // Required empty public constructor
    }

    public static CitasPActivity newInstance(String param1, String param2) {
        CitasPActivity fragment = new CitasPActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_citas_p, container, false);

        context = getContext();

        FloatingActionButton add = v.findViewById(R.id.add);
        lvCitas = (ListView) v.findViewById(R.id.lvCitasP);

        citasList = new ArrayList<>();
        pacienteList = new ArrayList<>();

        Date date = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecha = dateFormat.format(date);

        pacienteList = Global.getPacientesP(context);

        add.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), AddNewCitaPActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("arraylist",(Serializable)pacienteList);
            i.putExtra("pacienteList", args);
            startActivity(i);
        });
        showList();

        lvCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsCitaPActivity.class);
                Cita cita = citasList.get(position);

                intent.putExtra("cita", cita.getId());
                intent.putExtra("fecha", cita.getFecha());
                intent.putExtra("hora", cita.getHora());
                intent.putExtra("usuario", cita.getUsuario());

                startActivity(intent);
            }
        });

        return v;
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global.ip + "listCitasP.php?usuario=" + Global.us, response -> {
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
                            Cita c = new Cita(pacObj.getInt("id_citap"), pacObj.getString("fecha"), pacObj.getString("hora"),
                                    pacObj.getInt("usuario"), pacObj.getInt("asistio"));
                            citasList.add(c);
                        }
                    }
                }
                Citas_Adapter adapter = new Citas_Adapter(citasList, context);
                lvCitas.setAdapter(adapter);
                //Toast.makeText(CitasActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException | ParseException e) {
                //Toast.makeText(CitasActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

}