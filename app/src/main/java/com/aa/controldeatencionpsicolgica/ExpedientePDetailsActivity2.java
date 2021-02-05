package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Expediente;
import com.aa.controldeatencionpsicolgica.Model.Peritaje;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExpedientePDetailsActivity2 extends AppCompatActivity {

    private TextView lblMotivo, lblNotas, lblTipoConsulta, fecha, lblTramiento, lblPacientes;
    private String pacientes;
    private int id_cita = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente_details2);

        lblNotas = findViewById(R.id.lblNotas);
        lblMotivo = findViewById(R.id.lblMotivo);
        lblTipoConsulta = findViewById(R.id.lblTipoConsulta);
        fecha = findViewById(R.id.lblFecha);
        lblTramiento = findViewById(R.id.lblTratamiento);
        lblPacientes = findViewById(R.id.lblPacientesExpediente);

        Bundle objeto = getIntent().getExtras();
        Expediente expediente = null;

        if(objeto != null){
            expediente = (Expediente) objeto.getSerializable("expedienteData");
            lblTipoConsulta.setText("Peritaje");
            lblNotas.setText(expediente.getNotas_sesion());
            lblMotivo.setText(expediente.getMotivo_atencion());
            fecha.setText(expediente.getFecha());

            id_cita = expediente.getId_consulta();
            //Toast.makeText(this, ""+ id_cita, Toast.LENGTH_SHORT).show();


            //showPacientes();
        }

    }

    /***********************************************   NO FUNCIONAAAAAA *******************************************/
    private void showPacientes() {
        StringRequest stringRequest = new StringRequest(Global.ip+"getPacientesExpediente.php", response -> {
            try{
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    //Paciente p = new Paciente(pacObj.getString("nombres"), pacObj.getString("am"), pacObj.getString("pm"));
                    pacientes = pacObj.getString("nombres") + " " + pacObj.getString("ap") + " " + pacObj.getString("am") + "\n ";
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {});
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        lblPacientes.setText(pacientes);
    }
}