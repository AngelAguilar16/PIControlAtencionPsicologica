package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditPeritajeActivity extends AppCompatActivity {
    EditText nombre, motivo, notas;
    Button close, editar;

    String URL = Global.ip + "updatePeritaje.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_peritaje);

        nombre = findViewById(R.id.editTextPacienteP);
        motivo = findViewById(R.id.editTextMotivoP);
        notas = findViewById(R.id.editTextConsultaP);

        close = findViewById(R.id.btnCloseReporteP);
        editar = findViewById(R.id.btnGuardarDatosConsultaP);

        Bundle objeto = getIntent().getExtras();
        Peritaje p = null;

        if(objeto != null){
            p = (Peritaje) objeto.getSerializable("peritaje");

            //Toast.makeText(EditContactoAgendaActivity.this, "El ID es: " + paciente.getId(), Toast.LENGTH_SHORT).show();

            nombre.setText(p.getPaciente());
            motivo.setText(p.getMotivo_atencion());
            notas.setText(p.getNotas_sesion());

        }

        Peritaje fp = p;

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EditPeritajeActivity.this, "Datos actualizados!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditPeritajeActivity.this, ListaPeritaje.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditPeritajeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<>();

                        parametros.put("id", Integer.toString(fp.getId_peritaje()));
                        parametros.put("nombre", nombre.getText().toString());
                        parametros.put("motivo", motivo.getText().toString());
                        parametros.put("notas", notas.getText().toString());

                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(EditPeritajeActivity.this);
                requestQueue.add(stringRequest);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPeritajeActivity.this, ListaPeritaje.class);
                startActivity(intent);
                finish();
            }
        });
    }
}