package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spTipoUsuario;
    String[] opciones = {"Psicología", "Psiquiatría"};
    Integer tipo;
    EditText nombre, mail, password1, password2;
    Intent ii, i;
    Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        spTipoUsuario = (Spinner) findViewById(R.id.spTipoUsuario);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLoginSreen);
        nombre = (EditText) findViewById(R.id.etUsernameMain);
        mail = (EditText) findViewById(R.id.etEmail);
        password1 = (EditText) findViewById(R.id.etPasswdMain);
        password2 = (EditText) findViewById(R.id.etPasswdConfirm);

        ArrayAdapter<String> aa = new ArrayAdapter<>(RegistroActivity.this, android.R.layout.simple_dropdown_item_1line, opciones);

        spTipoUsuario.setAdapter(aa);
        spTipoUsuario.setOnItemSelectedListener(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.spTipoUsuario){
            tipo = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void registerBtn(View view) {
        if (password1.getText().toString().equals(password2.getText().toString())) {
            MainActivity.trustAllCertificates();//Si vas a hacer mas llamadas a cualquier URL utiliza esta funcion antes de cada llamada, para que no de error en los certificados
            StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.78/dif/register.php", //Si no te funciona esto, pon la ip de tu computadora
                    response -> {
                        if (response.contains("1")) {
                            Toast.makeText(RegistroActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                            ii = new Intent(RegistroActivity.this, MenuActivity.class); //Puse una activity en blanco para cuando se registre el usuario
                            startActivity(ii);
                        } else if (response.contains("0")) {
                            Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        } else if (response.contains("2")) {
                            Toast.makeText(RegistroActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistroActivity.this, response+"", Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Toast.makeText(RegistroActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombres", nombre.getText().toString());
                    params.put("correo", mail.getText().toString());
                    params.put("password", password1.getText().toString());
                    params.put("t_usuario", opciones[tipo]);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        } else if (!password1.getText().toString().equals(password2.getText().toString()))
            Toast.makeText(RegistroActivity.this, "Las constraseñas no coinciden", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(nombre.getText().toString().trim()) || TextUtils.isEmpty(mail.getText().toString().trim())) //No logro hacer que compruebe si está vacío el campo, pero ya funciona el registro
            Toast.makeText(RegistroActivity.this, "Completar todos los campos", Toast.LENGTH_LONG).show();
    }
}