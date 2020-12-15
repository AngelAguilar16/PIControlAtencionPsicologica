package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {
    
    EditText mail, password;
    Button login, register;
    Intent i;
    String urlAddress="http://192.168.1.78/dif/login.php";

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
                i = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });

        cargarSP();
    }

    private void cargarSP() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Boolean s_ini = preferences.getBoolean("s_ini", Boolean.FALSE);

        if (s_ini.equals(Boolean.TRUE)){
            i = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(i);
        }

    }

    public void loginBtn(View view) {
        SenderLog s = new SenderLog(MainActivity.this, urlAddress, mail, password);
        s.execute();
    }


}