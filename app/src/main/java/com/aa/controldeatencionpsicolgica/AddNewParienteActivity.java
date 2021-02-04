package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Sender.SenderCita;
import com.aa.controldeatencionpsicolgica.Sender.SenderPariente;

import java.util.ArrayList;

public class AddNewParienteActivity extends AppCompatActivity {

    Spinner spinnerNombrePariente, spinnerTipoPariente;
    String[] oTipo = {"Tipo de parentesco", "Madre", "Padre", "Hijo", "Hija", "Hermano", "Hermana", "Otro"};
    int np;
    String urlAddress= Global.ip + "addPariente.php";
    Button btnAddNewPariente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pariente);

        btnAddNewPariente = findViewById(R.id.btnAddNewPariente);
        spinnerTipoPariente = findViewById(R.id.spinnerTipoPariente);
        ArrayAdapter<String> ac = new ArrayAdapter<String>(AddNewParienteActivity.this,android.R.layout.simple_dropdown_item_1line, oTipo);
        spinnerTipoPariente.setAdapter(ac);

        btnAddNewPariente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int id_pac = (int) intent.getExtras().get("id_paciente");

                Bundle args = intent.getBundleExtra("pacienteList");
                ArrayList<Paciente> p = (ArrayList<Paciente>) args.getSerializable("arraylist");
                Paciente paciente = p.get(np);
                //Toast.makeText(AddNewCitaActivity.this,"" + paciente.getId(),Toast.LENGTH_LONG).show();

                SenderPariente s = new SenderPariente(AddNewParienteActivity.this, urlAddress, spinnerTipoPariente.getSelectedItem().toString(),paciente.getId(), id_pac);
                s.execute();
            }
        });

        setSpinner();

    }

    private void setSpinner() {
        spinnerNombrePariente = findViewById(R.id.spinnerNombreParientes);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("pacienteList");
        ArrayList<Paciente> p = (ArrayList<Paciente>) args.getSerializable("arraylist");
        if (p != null) {
            ArrayAdapter<Paciente> adapter = new ArrayAdapter<Paciente>(this, android.R.layout.simple_spinner_dropdown_item, p);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerNombrePariente.setAdapter(adapter);
            // Spinner click listener
            spinnerNombrePariente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    np = position;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        else {
            Toast.makeText(AddNewParienteActivity.this,"p  AAAA",Toast.LENGTH_LONG).show();
        }
    }

}