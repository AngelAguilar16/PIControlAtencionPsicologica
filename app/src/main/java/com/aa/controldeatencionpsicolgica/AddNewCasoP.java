package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.aa.controldeatencionpsicolgica.Adapter.PacientesP_Adapter;
import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Fragments.ExpedientesFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.Sender.SenderCaso;
import com.aa.controldeatencionpsicolgica.Sender.SenderCasoP;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddNewCasoP extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etDescCaso;
    ListView lvPacientes;
    ImageButton btnCSNCP;
    Button btnSelectPacienteCaso, btnCrearCaso, btnVerCasos;
    Paciente_peritaje paciente = null;
    String urlAddress = Global.ip + "addCaso.php";
    ArrayList<Paciente_peritaje> pac = new ArrayList<>();
    ArrayList<Paciente_peritaje> pacienteList = new ArrayList<>();
    Context context;
    String a;

    public AddNewCasoP() {
        // Required empty public constructor
    }

    public static AddNewCasoP newInstance(String param1, String param2) {
        AddNewCasoP fragment = new AddNewCasoP();
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
        View v = inflater.inflate(R.layout.activity_add_new_caso_p, container, false);

        context = getContext();


        etDescCaso = (EditText) v.findViewById(R.id.etDescCaso);
        lvPacientes = v.findViewById(R.id.lvPacientesSelec);
        btnCSNCP = v.findViewById(R.id.btnCSNCP);

        Bundle objeto = getArguments();

        if(objeto != null){
            a = getArguments().get("desc").toString();
            pac = (ArrayList<Paciente_peritaje>) objeto.getSerializable("pacientes");
            paciente = (Paciente_peritaje) objeto.getSerializable("pacienteData");
            pac.add(paciente);
        }
        if (a != null){ etDescCaso.setText(a); }


        btnSelectPacienteCaso = (Button) v.findViewById(R.id.btnSelectPacienteCaso);
        btnCrearCaso = (Button) v.findViewById(R.id.btnCrearCaso);
        btnVerCasos = v.findViewById(R.id.btnVerCasos);
        showList();

        btnSelectPacienteCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(getActivity(), ListaPacienteP.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacientes", pac);
                i.putExtras(bundle);
                i.putExtra("desc", etDescCaso.getText().toString());
                startActivity(i);*/
                Bundle args = new Bundle();
                args.putSerializable("pacientes", pac);
                args.putString("desc", etDescCaso.getText().toString());
                ListaPacienteP fragment = new ListaPacienteP();
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutP, fragment);
                transaction.commit();
            }
        });

        btnCrearCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SenderCasoP s = new SenderCasoP(context, urlAddress, etDescCaso.getText().toString(), pac);
                s.execute();
            }
        });

        btnVerCasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VerCasosPActivity.class);
                startActivity(intent);
            }
        });

        btnCSNCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBtnCSNCP();
            }
        });

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

                    if (pac.contains(p)){
                        pacienteList.add(p);
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

    public void logoutBtnCSNCP() {
        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("s_ini", Boolean.FALSE);
        editor.apply();

        SharedPreferences pref = getActivity().getSharedPreferences("a", Context.MODE_PRIVATE);

        SharedPreferences.Editor e = pref.edit();

        e.putString("u", "false");
        e.apply();

        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);
    }

}