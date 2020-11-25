package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                i = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });
    }

    public void loginBtn(View view) {
        trustAllCertificates();//Si vas a hacer mas llamadas a cualquier URL utiliza esta funcion antes de cada llamada, para que no de error en los certificados
        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.78/dif/login.php", //Si no te funciona esto, pon la ip de tu computadora
                response -> {

                    if (response.contains("1")) {
                        Toast.makeText(MainActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        i = new Intent(MainActivity.this, MenuActivity.class);
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
                params.put("correo",  mail.getText().toString());
                params.put("password", password.getText().toString()) ;
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    //Hace que no cheque los certificados y confia en todos, vamos a quitar esto antes de que salga a producción la app
    public static void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}