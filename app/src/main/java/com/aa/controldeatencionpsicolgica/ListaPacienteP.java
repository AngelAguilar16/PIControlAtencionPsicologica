package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.PacientesP_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Fragments.ExpedientesFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPacienteP extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView lvPacientes;
    List<Paciente_peritaje> pacienteList;
    ArrayList<Paciente_peritaje> pList = new ArrayList<>();
    String a;
    Context context;


    public ListaPacienteP() {
        // Required empty public constructor
    }

    public static ListaPacienteP newInstance(String param1, String param2) {
        ListaPacienteP fragment = new ListaPacienteP();
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
        View v = inflater.inflate(R.layout.activity_lista_paciente_p, container, false);

        context = getContext();


        lvPacientes = v.findViewById(R.id.lvPacientesCaso);
        pacienteList = new ArrayList<>();
        Bundle objeto = getArguments();

        pList = (ArrayList<Paciente_peritaje>) objeto.getSerializable("pacientes");
        a = getArguments().get("desc").toString();
        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente_peritaje paciente = pacienteList.get(position);
                /*Intent intent = new Intent(context, AddNewCasoP.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacienteData", paciente);
                bundle.putSerializable("pacientes", pList);
                intent.putExtras(bundle);
                intent.putExtra("desc", a);*/
                Bundle args = new Bundle();
                args.putSerializable("pacienteData", paciente);
                args.putSerializable("pacientes", pList);
                args.putString("desc", a);
                AddNewCasoP fragment = new AddNewCasoP();
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutP, fragment);
                transaction.commit();
            }
        });
        showList();
        return v;
    }

        private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientesP.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("CURP"),pacObj.getInt("caso"));
                    if (pList.size() == 0) {
                        pacienteList.add(p);
                    } else {
                        if (!pList.contains(p)){
                            pacienteList.add(p);
                        }
                    }
                }
                PacientesP_Adapter adapter = new PacientesP_Adapter(pacienteList, context);
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(ListaPacientes.this,"" + pList.size(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }
}