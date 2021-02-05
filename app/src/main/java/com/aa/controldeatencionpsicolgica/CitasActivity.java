package com.aa.controldeatencionpsicolgica;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Citas_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.RVCitasAdapter;
import com.aa.controldeatencionpsicolgica.Fragments.CitasFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Cita;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class CitasActivity extends Fragment {

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
    ArrayList<Paciente> pacienteList;
    RecyclerView rvCitas;
    ImageButton btnCSC;

    public CitasActivity() {
        // Required empty public constructor
    }

    public static CitasActivity newInstance(String param1, String param2) {
        CitasActivity fragment = new CitasActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View v = inflater.inflate(R.layout.activity_citas, container, false);
        context = getContext();
        FloatingActionButton add = v.findViewById(R.id.add);
        lvCitas = (ListView) v.findViewById(R.id.lvCitas);
        rvCitas = v.findViewById(R.id.rvCitas);
        btnCSC = v.findViewById(R.id.btnCSC);

        rvCitas.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rvCitas.setLayoutManager(llm);
        citasList = new ArrayList<>();
        pacienteList = new ArrayList<>();

        Date date = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecha = dateFormat.format(date);

        pacienteList = Global.getPacientes(context);

        add.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), AddNewCitaActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("arraylist",(Serializable)pacienteList);
            i.putExtra("pacienteList", args);
            startActivity(i);
        });
        showList();

        /*lvCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsCitaActivity.class);
                Cita cita = citasList.get(position);

                intent.putExtra("cita", cita.getId());
                intent.putExtra("fecha", cita.getFecha());
                intent.putExtra("hora", cita.getHora());
                intent.putExtra("usuario", cita.getUsuario());

                startActivity(intent);
            }
        });*/

        btnCSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBtnC();
            }
        });

        return v;
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
                RVCitasAdapter adapter = new RVCitasAdapter(citasList);
                rvCitas.setAdapter(adapter);
                //Toast.makeText(CitasActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException | ParseException e) {
                //Toast.makeText(CitasActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void logoutBtnC() {
        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("s_ini", Boolean.FALSE);
        editor.commit();

        SharedPreferences pref = getActivity().getSharedPreferences("a", Context.MODE_PRIVATE);

        SharedPreferences.Editor e = pref.edit();

        e.putString("u", "false");
        e.commit();

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }

}