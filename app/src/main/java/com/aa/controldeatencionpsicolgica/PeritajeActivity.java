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
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.aa.controldeatencionpsicolgica.Sender.SenderPeritaje;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeritajeActivity extends AppCompatActivity {
    private Button btnCloseReporte, btnGuardarDatosConsulta;
    private ImageButton btnFechaP;
    private EditText editTextPaciente,editTextMotivo, editTextConsulta, editTextFechaP;
    private TextView textViewNombres;

    private String nombres = "";
    private int cita = 0;

    private String urlAddress= Global.ip + "addPeritaje.php";
    private ArrayList<Integer> idUsuarios;
    private ArrayList<Paciente_peritaje> listaPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peritaje);

        textViewNombres = findViewById(R.id.txtPacientesP);
        editTextMotivo = (EditText) findViewById(R.id.editTextMotivoP);
        editTextConsulta = (EditText) findViewById(R.id.editTextConsultaP);
        editTextFechaP = findViewById(R.id.editTextFechaCitaP);


        btnCloseReporte = findViewById(R.id.btnCloseReporteP);
        btnGuardarDatosConsulta = findViewById(R.id.btnGuardarDatosConsultaP);
        btnFechaP = findViewById(R.id.btnFechaP);

        // Consigo los valores de DetailsCitaPActivity
        cita = getIntent().getIntExtra("cita", 0);

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
                setUpdateCitas();
                SenderPeritaje s = new SenderPeritaje(PeritajeActivity.this, urlAddress, Global.us, cita, editTextMotivo, editTextConsulta);
                s.execute();
            }
        });

    }

    private void setUpdateCitas() {
        StringRequest request = new StringRequest(Request.Method.POST, Global.ip + "updateCitasP.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PeritajeActivity.this, "Cita Actualizada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PeritajeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("id", Integer.toString(cita));
                parametros.put("asistio", "1");
                parametros.put("visible", "0");

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PeritajeActivity.this);
        requestQueue.add(request);
    }


}