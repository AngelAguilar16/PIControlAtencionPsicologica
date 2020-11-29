package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddPacientesActivity extends AppCompatActivity  {

    private ImageView imgPaciente;
    private EditText txtName, txtPaterno, txtMaterno;
    private  EditText txtTelefono;
    private Button btnAddPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pacientes);

        // Declaracion y enlace
        imgPaciente = findViewById(R.id.imgPaciente_AddPaciente);
        txtName = findViewById(R.id.txtName_AddPaciente);
        txtPaterno = findViewById(R.id.txtPaterno_AddPacientes);
        txtMaterno = findViewById(R.id.txtMaterno_AddPacientes);
        txtTelefono = findViewById(R.id.txtTelefono_AddPacientes);
        btnAddPaciente = findViewById(R.id.btnAddPaciente);

        // Implementacion del evento onClick para que actue el
        // boton al presionarlo
        btnAddPaciente.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(AddPacientesActivity.this, "Verifica que los campos esten" +
                            "completos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddPacientesActivity.this, "Usuario registrado",
                            Toast.LENGTH_SHORT).show();

                    txtName.setText("");    txtPaterno.setText("");
                    txtMaterno.setText(""); txtTelefono.setText("");

                    Intent intent = new Intent(AddPacientesActivity.this, InfoPacientesActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}