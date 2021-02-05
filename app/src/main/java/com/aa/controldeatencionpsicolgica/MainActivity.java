package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Sender.SenderLog;

public class MainActivity extends AppCompatActivity {
    
    EditText mail, password;
    Button login, register;
    Intent i;
    String urlAddress= Global.ip + "login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);

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
        String t_us = preferences.getString("t_us", "false");
        int id = preferences.getInt("id", 0);

        if (s_ini.equals(Boolean.TRUE)){
            Global.setUsuario(id);
            if(t_us.equals("Peritaje")){
                i = new Intent(MainActivity.this, MenuActivityPeritaje.class);
                startActivity(i);
            } else {
                i = new Intent(MainActivity.this, MenuMaterial.class);
                startActivity(i);
            }

        }

    }

    public void loginBtn(View view) {
        SenderLog s = new SenderLog(MainActivity.this, urlAddress, mail, password);
        s.execute();
    }


}