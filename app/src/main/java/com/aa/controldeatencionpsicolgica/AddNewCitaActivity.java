package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Sender.SenderCita;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddNewCitaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePicker dp;
    TimePicker tp;
    String fecha, hora;
    int np;
    //EditText n_paciente;
    Spinner paciente;
    Button crear_cita;
    String urlAddress="http://192.168.1.69/dif/addCita.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cita);

        dp = (DatePicker) findViewById(R.id.datePicker);
        tp = (TimePicker) findViewById(R.id.timePicker);
        //n_paciente = (EditText) findViewById(R.id.n_paciente);
        setSpinner();

        crear_cita = (Button) findViewById(R.id.agregarcitaBtn);


        crear_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
                fecha = dp.getYear() + "-" + dp.getMonth() + "-" + dp.getDayOfMonth();

                Intent intent = getIntent();
                Bundle args = intent.getBundleExtra("pacienteList");
                ArrayList<Paciente> p = (ArrayList<Paciente>) args.getSerializable("arraylist");
                Paciente paciente = p.get(np);
                //Toast.makeText(AddNewCitaActivity.this,"" + paciente.getId(),Toast.LENGTH_LONG).show();

                SenderCita s = new SenderCita(AddNewCitaActivity.this, urlAddress, fecha, hora, cargarIdusuario(), paciente.getId());
                s.execute();
            }
        });
    }

    public int cargarIdusuario() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        return preferences.getInt("id", 0);
    }

    private void setSpinner() {
        paciente = (Spinner) findViewById(R.id.paciente);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("pacienteList");
        ArrayList<Paciente> p = (ArrayList<Paciente>) args.getSerializable("arraylist");


        if (p != null) {

            ArrayAdapter<Paciente> adapter = new ArrayAdapter<Paciente>(this, android.R.layout.simple_spinner_dropdown_item, p);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            paciente.setAdapter(adapter);

            // Spinner click listener
            paciente.setOnItemSelectedListener(this);
        } else {
            Toast.makeText(AddNewCitaActivity.this,"p  AAAA",Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.paciente){
            np = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}