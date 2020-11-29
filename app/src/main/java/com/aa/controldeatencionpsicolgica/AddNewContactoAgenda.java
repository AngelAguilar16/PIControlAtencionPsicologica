package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddNewContactoAgenda extends AppCompatActivity {

    EditText nombres, telefono, estado, municipio, domicilio, sexo, fecNac, estCiv, escolaridad, ocupacion;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contacto_agenda);

        nombres = findViewById(R.id.etNombres);
        telefono = findViewById(R.id.etTelefono);
        estado = findViewById(R.id.etEstado);
        municipio = findViewById(R.id.etMunicipio);
        domicilio = findViewById(R.id.etDomicilio);
        sexo = findViewById(R.id.etSexo);
        fecNac = findViewById(R.id.etFecNac);
        estCiv = findViewById(R.id.etEstCiv);
        escolaridad = findViewById(R.id.etEscolaridad);
        ocupacion = findViewById(R.id.etOcupacion);

    }

    public void addNewContactBtn(View view) {
            MainActivity.trustAllCertificates();//Si vas a hacer mas llamadas a cualquier URL utiliza esta funcion antes de cada llamada, para que no de error en los certificados
            StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.78/dif/addContacto.php", //Si no te funciona esto, pon la ip de tu computadora
                    response -> {
                        if (response.contains("1")) {
                            Toast.makeText(this, "Paciente Añadido", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(this, AgendaActivity.class); //Puse una activity en blanco para cuando se registre el usuario
                            startActivity(i);
                        } else if (response.contains("0")) {
                            Toast.makeText(this, "El paciente ya existe", Toast.LENGTH_SHORT).show();
                        } else if (response.contains("2")) {
                            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, response+"", Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("fecha_registro", date);
                    params.put("nombres", nombres.getText().toString());
                    params.put("nombre_pmt", "NOTIENE");
                    params.put("telefono", telefono.getText().toString());
                    params.put("estado", estado.getText().toString());
                    params.put("municipio", municipio.getText().toString());
                    params.put("domicilio", domicilio.getText().toString());
                    params.put("sexo", sexo.getText().toString());
                    params.put("fecha_nacimiento", fecNac.getText().toString());
                    params.put("estado_civil", estCiv.getText().toString());
                    params.put("escolaridad", escolaridad.getText().toString());
                    params.put("ocupacion", ocupacion.getText().toString());
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        /*if (TextUtils.isEmpty(nombre.getText().toString().trim()) || TextUtils.isEmpty(mail.getText().toString().trim())) //No logro hacer que compruebe si está vacío el campo, pero ya funciona el registro
            Toast.makeText(RegistroActivity.this, "Completar todos los campos", Toast.LENGTH_LONG).show();*/
    }
}