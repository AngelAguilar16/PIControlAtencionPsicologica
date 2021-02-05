package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Fragments.ReportesFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Sender.SenderCaso;
import com.aa.controldeatencionpsicolgica.Sender.SenderLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.ArrayList;

public class AddNewCaso extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText etDescCaso;
    ListView lvPacientes;
    Button btnSelectPacienteCaso, btnCrearCaso, btnVerCasos;
    Paciente paciente = null;
    String urlAddress = Global.ip + "addCaso.php";
    ArrayList<Paciente> pac = new ArrayList<>();
    ArrayList<Paciente> pacienteList = new ArrayList<>();
    Context context;
    ImageButton btnCSNC;

    public AddNewCaso() {
        // Required empty public constructor
    }

    public static AddNewCaso newInstance(String param1, String param2) {
        AddNewCaso fragment = new AddNewCaso();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_add_new_caso, container, false);
        context = getContext();
        etDescCaso = (EditText) v.findViewById(R.id.etDescCaso);
        lvPacientes = v.findViewById(R.id.lvPacientesSelec);
        btnCSNC = v.findViewById(R.id.btnCSNC);
        String a = getActivity().getIntent().getStringExtra("desc");
        Bundle objeto = getActivity().getIntent().getExtras();

        if(objeto != null){
            pac = (ArrayList<Paciente>) objeto.getSerializable("pacientes");
            paciente = (Paciente) objeto.getSerializable("pacienteData");
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
                Intent i = new Intent(getActivity(), ListaPacientes.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("pacientes", pac);
                i.putExtras(bundle);
                i.putExtra("desc", etDescCaso.getText().toString());
                startActivity(i);
            }
        });

        btnCrearCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SenderCaso s = new SenderCaso(getActivity(), urlAddress, etDescCaso.getText().toString(), pac);
                s.execute();
            }
        });

        btnVerCasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VerCasosActivity.class);
                startActivity(intent);
            }
        });

        btnCSNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBtnNC();
            }
        });

        return v;
    }

    private void showList(){
        StringRequest stringRequest = new StringRequest(Global.ip + "listPacientes.php?usuario="+ Global.us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"), pacObj.getInt("caso"));

                    if (pac.contains(p)){
                        pacienteList.add(p);
                    }

                }
                Pacientes_Adapter adapter = new Pacientes_Adapter(pacienteList, context);
                lvPacientes.setAdapter(adapter);
                //Toast.makeText(ListaPacientes.this,"" + pList.size(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void logoutBtnNC() {
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