package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Sender.SenderReg;

public class RegistroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spTipoUsuario;
    String[] opciones = {"Psicología", "Psiquiatría", "Peritaje"};
    Integer tipo;
    EditText nombre, ap, am, mail, password1;
    Intent ii;
    Button btnRegister, btnLogin;
    String urlAddress= Global.ip + "register.php";

    static Boolean succ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        spTipoUsuario = (Spinner) findViewById(R.id.spTipoUsuario);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLoginSreen);
        nombre = (EditText) findViewById(R.id.etUsernameMain);
        ap = (EditText) findViewById(R.id.editTextAP);
        am = (EditText) findViewById(R.id.editTextAM);
        mail = (EditText) findViewById(R.id.etEmail);
        password1 = (EditText) findViewById(R.id.etPasswdMain);

        ArrayAdapter<String> aa = new ArrayAdapter<>(RegistroActivity.this, android.R.layout.simple_dropdown_item_1line, opciones);

        spTipoUsuario.setAdapter(aa);
        spTipoUsuario.setOnItemSelectedListener(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ii = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(ii);
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

    public void registerBtn(View view){
        SenderReg s = new SenderReg(RegistroActivity.this, urlAddress, opciones[tipo], nombre, ap, am, mail, password1);
        s.execute();
    }

}