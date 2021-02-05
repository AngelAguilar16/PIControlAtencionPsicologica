package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Peritaje_Adapter;
import com.aa.controldeatencionpsicolgica.Fragments.AgendaFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPeritaje extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView lvPeritaje;
    List<Peritaje> peritajeList;
    ImageButton back;
    Context context;

    public ListaPeritaje() {
        // Required empty public constructor
    }

    public static ListaPeritaje newInstance(String param1, String param2) {
        ListaPeritaje fragment = new ListaPeritaje();
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
        View v = inflater.inflate(R.layout.activity_lista_peritaje, container, false);
        context = getContext();
        FloatingActionButton fab = v.findViewById(R.id.fab);
        lvPeritaje = v.findViewById(R.id.lvPeritaje);
        back = v.findViewById(R.id.imageButton);

        peritajeList = new ArrayList<>();

        fab.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), PeritajeActivity.class);
            startActivity(i);
        });

        lvPeritaje.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Peritaje peritaje = peritajeList.get(position);
                Intent intent = new Intent(context, PeritajeDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("peritajeData", peritaje);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenuActivityPeritaje.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        showList();

        return v;
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPeritaje.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("peritajeList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Peritaje p = new Peritaje(pacObj.getInt("id_peritaje"),pacObj.getInt("usuario"),pacObj.getString("nombres"),
                                            pacObj.getString("ap"), pacObj.getString("am"), pacObj.getString("fecha"),
                                            pacObj.getString("hora"),pacObj.getString("motivo_atencion"), pacObj.getString("notas_sesion"));
                    peritajeList.add(p);
                }
                Peritaje_Adapter a = new Peritaje_Adapter(peritajeList, context);
                lvPeritaje.setAdapter(a);
                //Toast.makeText(AgendaActivity.this,"Funcion Activada" + Global.us,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }
}
