package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Sender.SenderNewContacto;
import com.aa.controldeatencionpsicolgica.Sender.SenderNewPacienteP;

public class AddNewPacienteP extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerSexo;
    EditText nombres, apellido_paterno, apellido_materno, fecNac, curp;
    String[] oSexo = {"Sexo", "Masculino", "Femenino", "Otro"};
    int opS = 0;//caso = 1
    String urlAddress= Global.ip + "addPacienteP.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_paciente_p);

        nombres = findViewById(R.id.etNombres);
        apellido_paterno = findViewById(R.id.etApPaterno);
        apellido_materno = findViewById(R.id.etApMaterno);
        fecNac = findViewById(R.id.editTextFCPaciente);
        curp = findViewById(R.id.editTextCurp);



        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexoM);

        ArrayAdapter<String> ac = new ArrayAdapter<String>(AddNewPacienteP.this,android.R.layout.simple_dropdown_item_1line, oSexo);


        spinnerSexo.setAdapter(ac);
        spinnerSexo.setOnItemSelectedListener(this);
    }

    public void addNewContactPBtn(View view) {
        SenderNewPacienteP s = new SenderNewPacienteP(AddNewPacienteP.this, urlAddress, oSexo[opS], Global.us, 1, nombres, apellido_paterno, apellido_materno, curp,fecNac);
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