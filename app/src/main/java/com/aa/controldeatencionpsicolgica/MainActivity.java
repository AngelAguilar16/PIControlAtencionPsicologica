package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText mail, password;
    Button login, register;
    String us, pass;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = (EditText) findViewById(R.id.etCorreoMain);
        password = (EditText) findViewById(R.id.etPasswdMain);

        login = (Button) findViewById(R.id.btnLogin);



        register = (Button) findViewById(R.id.btnRegisterScreen);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, registro.class);
                startActivity(i);
            }
        });
    }

    public void loginBtn(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://10.0.2.2/dif/login.php",
                response -> {

                    if (response.contains("1")) {
                        Toast.makeText(MainActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        i = new Intent(MainActivity.this, eact.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("correo",  /*mail.getText().toString()*/ "dmontesdeoca1@ucol.mx");
                params.put("password", /*password.getText().toString()*/ "123456");
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}