package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.Sender.SenderPeritaje;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PeritajeActivity extends AppCompatActivity {
    Button btnCloseReporte, btnGuardarDatosConsulta;
    ImageButton btnFechaP;
    EditText editTextPaciente,editTextMotivo, editTextConsulta, editTextFechaP;


    String urlAddress= Global.ip + "addPeritaje.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peritaje);
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombres");

        editTextPaciente = (EditText) findViewById(R.id.editTextPacienteP);
        editTextMotivo = (EditText) findViewById(R.id.editTextMotivoP);
        editTextConsulta = (EditText) findViewById(R.id.editTextConsultaP);
        editTextFechaP = findViewById(R.id.editTextFechaCitaP);

        editTextPaciente.setText(nombre);
        btnCloseReporte = findViewById(R.id.btnCloseReporteP);
        btnGuardarDatosConsulta = findViewById(R.id.btnGuardarDatosConsultaP);
        btnFechaP = findViewById(R.id.btnFechaP);

        btnFechaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(PeritajeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        String fec = "" + selectedyear + "/" + selectedmonth + "/" + selectedday;
                        editTextFechaP.setText(fec);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });


        //Toast.makeText(ReporteCita.this,nombre + "",Toast.LENGTH_LONG).show();
        btnCloseReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PeritajeActivity.this, MenuActivityPeritaje.class);
                startActivity(i);
                finish();
            }
        });

        btnGuardarDatosConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SenderPeritaje s = new SenderPeritaje(PeritajeActivity.this, urlAddress, Global.us, editTextPaciente, editTextMotivo, editTextConsulta);
                s.execute();
            }
        });

    }
}