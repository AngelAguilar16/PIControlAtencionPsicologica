package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateInformacionActivity extends AppCompatActivity {

    private ImageView imgPaciente;
    private EditText txtName, txtPaterno, txtMaterno;
    private  EditText txtTelefono;
    private Button btnUpdatePaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_informacion);

        // Declaracion y enlace
        imgPaciente = findViewById(R.id.imgPaciente_Update);
        txtName = findViewById(R.id.txtName_Update);
        txtPaterno = findViewById(R.id.txtPaterno_Update);
        txtMaterno = findViewById(R.id.txtMaterno_Update);
        txtTelefono = findViewById(R.id.txtTelefono_Update);
        btnUpdatePaciente = findViewById(R.id.btnUpdatePaciente);

        // Implementacion del evento onClick para que actue el
        // boton al presionarlo
        btnUpdatePaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se consiguen los valores a ingregar para la DB
                String namePaciente = txtName.getText().toString();
                String paternoPaciente = txtPaterno.getText().toString();
                String maternoPaciente = txtMaterno.getText().toString();
                String telefonoPaciente = txtTelefono.getText().toString();

                // Verificacion de que los campos estan rellenos, en caso positivo se registran
                // de lo contrario se muestra notificacion al usuario
                if(namePaciente.isEmpty() || paternoPaciente.isEmpty() ||
                        maternoPaciente.isEmpty() || telefonoPaciente.isEmpty()){
                    Toast.makeText(UpdateInformacionActivity.this, "Verifica que los campos esten" +
                            "completos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateInformacionActivity.this, "Usuario registrado",
                            Toast.LENGTH_SHORT).show();

                    txtName.setText("");    txtPaterno.setText("");
                    txtMaterno.setText(""); txtTelefono.setText("");

                    Intent intent = new Intent(UpdateInformacionActivity.this, InfoPacientesActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}