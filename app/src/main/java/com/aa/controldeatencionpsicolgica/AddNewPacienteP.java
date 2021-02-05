package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.aa.controldeatencionpsicolgica.Fragments.ExpedientesFragment;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Sender.SenderNewContacto;
import com.aa.controldeatencionpsicolgica.Sender.SenderNewPacienteP;

public class AddNewPacienteP extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spinnerSexo;
    EditText nombres, apellido_paterno, apellido_materno, fecNac, curp;
    String[] oSexo = {"Sexo", "Masculino", "Femenino", "Otro"};
    int opS = 0;//caso = 1
    Context context;
    Button btnAñadirPacienteP;
    String urlAddress= Global.ip + "addPacienteP.php";

    public AddNewPacienteP() {
        // Required empty public constructor
    }

    public static AddNewPacienteP newInstance(String param1, String param2) {
        AddNewPacienteP fragment = new AddNewPacienteP();
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
        View v = inflater.inflate(R.layout.activity_add_new_paciente_p, container, false);

        context = getContext();


        nombres = v.findViewById(R.id.etNombres);
        apellido_paterno = v.findViewById(R.id.etApPaterno);
        apellido_materno = v.findViewById(R.id.etApMaterno);
        fecNac = v.findViewById(R.id.editTextFCPaciente);
        curp = v.findViewById(R.id.editTextCurp);
        btnAñadirPacienteP = v.findViewById(R.id.btnAñadirPacienteP);



        spinnerSexo = (Spinner) v.findViewById(R.id.spinnerSexoM);

        ArrayAdapter<String> ac = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, oSexo);


        spinnerSexo.setAdapter(ac);
        spinnerSexo.setOnItemSelectedListener(this);

        btnAñadirPacienteP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewContactPBtn();
            }
        });

        return v;
    }

    public void addNewContactPBtn() {
        SenderNewPacienteP s = new SenderNewPacienteP(context, urlAddress, oSexo[opS], Global.us, 1, nombres, apellido_paterno, apellido_materno, curp,fecNac);
        s.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId()==R.id.spinnerSexoM){
            opS = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}