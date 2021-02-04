package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.Sender.SenderCita;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddNewCitaPActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePicker dp;
    TimePicker tp;
    String fecha, hora;
    int np;

    DateFormat dateFormat;

    //EditText n_paciente;
    Spinner paciente;
    Button crear_cita;
    private Button btnAddPacientes;
    private TextView txtPacientesCitas;

    String urlAddress= Global.ip + "addCitaP.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cita_p);

        dp = (DatePicker) findViewById(R.id.datePicker);
        tp = (TimePicker) findViewById(R.id.timePicker);
        //n_paciente = (EditText) findViewById(R.id.n_paciente);
        setSpinner();

        crear_cita = (Button) findViewById(R.id.agregarcitaBtn);


        crear_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
                Date date = new Date();
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                fecha = dateFormat.format(date);
                //fecha = dp.getYear() + "-" + dp.getMonth() + "-" + dp.getDayOfMonth();

                Intent intent = getIntent();
                Bundle args = intent.getBundleExtra("pacienteList");
                ArrayList<Paciente_peritaje> p = (ArrayList<Paciente_peritaje>) args.getSerializable("arraylist");
                Paciente_peritaje paciente = p.get(np);
                //Toast.makeText(AddNewCitaActivity.this,"" + paciente.getId(),Toast.LENGTH_LONG).show();

                SenderCita s = new SenderCita(AddNewCitaPActivity.this, urlAddress, fecha, hora, Global.us, 1,paciente.getId_pacp());
                s.execute();
            }
        });
    }


    private void setSpinner() {
        paciente = (Spinner) findViewById(R.id.pacientep);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("pacienteList");
        ArrayList<Paciente_peritaje> p = (ArrayList<Paciente_peritaje>) args.getSerializable("arraylist");


        if (p != null) {

            ArrayAdapter<Paciente_peritaje> adapter = new ArrayAdapter<Paciente_peritaje>(this, android.R.layout.simple_spinner_dropdown_item, p);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            paciente.setAdapter(adapter);

            // Spinner click listener
            paciente.setOnItemSelectedListener(this);
        } else {
            Toast.makeText(AddNewCitaPActivity.this,"p  AAAA",Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.pacientep){
            np = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}