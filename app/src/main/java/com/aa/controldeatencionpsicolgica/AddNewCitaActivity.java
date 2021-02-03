package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Sender.SenderCita;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddNewCitaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePicker dp;
    TimePicker tp;
    EditText horaet, fechaet;
    String fecha, hora;
    int np;
    TextInputLayout fechaLayout, horaLayout;

    DateFormat dateFormat;

    //EditText n_paciente;
    Spinner paciente;
    Button crear_cita;
    private Button btnAddPacientes;
    private TextView txtPacientesCitas;

    String urlAddress= Global.ip + "addCita.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cita);

        horaLayout = findViewById(R.id.text_input_layout_horacita);
        fechaLayout = findViewById(R.id.text_input_layout_fechacita);
        horaet = findViewById(R.id.editTextHoraCita);
        fechaet = findViewById(R.id.editTextFechaCita);
        //n_paciente = (EditText) findViewById(R.id.n_paciente);
        setSpinner();

        crear_cita = (Button) findViewById(R.id.agregarcitaBtn);

        fechaLayout.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            //To show current date in the datepicker
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(AddNewCitaActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                    selectedmonth = selectedmonth + 1;
                    String fec = "" + selectedyear + "/" + selectedmonth + "/" + selectedday;
                    fechaet.setText(fec);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        });

        fechaet.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            //To show current date in the datepicker
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(AddNewCitaActivity.this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                    selectedmonth = selectedmonth + 1;
                    String fec = "" + selectedyear + "/" + selectedmonth + "/" + selectedday;
                    fechaet.setText(fec);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        });

        horaet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddNewCitaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        horaet.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        crear_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = horaet.getText().toString();
                Date date = new Date();
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                fecha = fechaet.getText().toString();
                //fecha = dp.getYear() + "-" + dp.getMonth() + "-" + dp.getDayOfMonth();

                Intent intent = getIntent();
                Bundle args = intent.getBundleExtra("pacienteList");
                ArrayList<Paciente> p = (ArrayList<Paciente>) args.getSerializable("arraylist");
                Paciente paciente = p.get(np);
                //Toast.makeText(AddNewCitaActivity.this,"" + paciente.getId(),Toast.LENGTH_LONG).show();

                SenderCita s = new SenderCita(AddNewCitaActivity.this, urlAddress, fecha, hora, cargarIdusuario(), 1,paciente.getId());
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