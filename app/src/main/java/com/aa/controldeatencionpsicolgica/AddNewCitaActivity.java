package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class AddNewCitaActivity extends AppCompatActivity {
    DatePicker dp;
    TimePicker tp;
    String fecha, hora;
    int  opP = 0;
    Button crear_cita, pacienteBtn;
    int size = getIntent().getIntExtra("size", 0);
    String[] oPacientes = new String[size];
    int[] idPacientes = new int[size];
    String urlAddress="http://192.168.1.68/dif/addCita.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cita);

        dp = (DatePicker) findViewById(R.id.datePicker);
        tp = (TimePicker) findViewById(R.id.timePicker);

        pacienteBtn = (Button) findViewById(R.id.pacienteBtn);
        crear_cita = (Button) findViewById(R.id.agregarcitaBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige un paciente");



        pacientesList();

        builder.setItems(oPacientes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i >= 0){
                    opP = i;
                }
            }
        });

        //Toast.makeText(AddNewCitaActivity.this,"" ,Toast.LENGTH_LONG).show();
        pacienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        crear_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
                fecha = dp.getYear() + "-" + dp.getMonth() + "-" + dp.getDayOfMonth();
                //agregar selector de pacientes en un modal y agregar cita


                SenderCita s = new SenderCita(AddNewCitaActivity.this, urlAddress, fecha, hora, cargarIdusuario(), idPacientes[opP]);
                s.execute();
            }
        });
    }

    public int cargarIdusuario() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        return preferences.getInt("id", 0);
    }

    public void pacientesList(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.68/dif/listPacientes.php", response -> {
            try {

                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");

                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente pa = new Paciente(pacObj.getInt("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombre"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    oPacientes[i] = pa.getId() + " " + pa.getNombre() + " " + pa.getAp()+ " " + pa.getAm();
                    idPacientes[i] = pa.getId();
                }

                Toast.makeText(AddNewCitaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Toast.makeText(AddNewCitaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

}