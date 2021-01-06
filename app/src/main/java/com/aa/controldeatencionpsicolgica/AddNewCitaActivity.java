package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddNewCitaActivity extends AppCompatActivity {
    DatePicker dp;
    TimePicker tp;
    EditText np;
    String fecha, hora;
    int nombre, usuario, paciente;
    Button crear_cita;

    String urlAddress="http://192.168.1.69/dif/addCita.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cita);

        dp = (DatePicker) findViewById(R.id.datePicker);
        tp = (TimePicker) findViewById(R.id.timePicker);
        np = (EditText) findViewById(R.id.nombrePaciente);
        crear_cita = (Button) findViewById(R.id.agregarcitaBtn);

        crear_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
                fecha = dp.getYear() + "-" + dp.getMonth() + "-" + dp.getDayOfMonth();
                //agregar selector de pacientes en un modal y agregar cita
                paciente = Integer.parseInt(np.getText().toString());
                usuario = 1;

                SenderCita s = new SenderCita(AddNewCitaActivity.this, urlAddress, fecha, hora, usuario, paciente);
                s.execute();
            }
        });
    }

    public void cargarSp() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        nombre = preferences.getInt("id", Integer.parseInt("Error"));
    }
}