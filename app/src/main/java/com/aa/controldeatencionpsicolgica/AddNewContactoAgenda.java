package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Sender.SenderNewContacto;

public class AddNewContactoAgenda extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerMunicipio, spinnerEstado, spinnerSexo;
    EditText nombres, apellido_paterno, apellido_materno, telefono, domicilio, sexo, fecNac, estCiv, escolaridad, ocupacion;
    String[] oMunicipio = { "Municipio", "Manzanillo", "Tecomán", "Armería", "Comala", "Villa de Álvarez", "Cuauhtémoc", "Ixtlahuacán", "Coquimatlán", "Minatitlán"};
    String[] oEstado = { "Estado", "Colima" };
    String[] oSexo = {"Sexo", "Masculino", "Femenino", "Otro"};
    int opM = 0, opE = 0, opS = 0, usuario;//caso = 1
    String urlAddress= Global.ip + "addContacto.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contacto_agenda);

        nombres = findViewById(R.id.etNombres);
        apellido_paterno = findViewById(R.id.etApPaterno);
        apellido_materno = findViewById(R.id.etApMaterno);
        telefono = findViewById(R.id.editTextTelefonoPaciente);
        domicilio = findViewById(R.id.editTextDomicilioPaciente);
        fecNac = findViewById(R.id.editTextFCPaciente);
        estCiv = findViewById(R.id.editTextECPaciente);
        escolaridad = findViewById(R.id.editTextEscolaridadPaciente);
        ocupacion = findViewById(R.id.editTextOcupacionPaciente);

        spinnerMunicipio = (Spinner) findViewById(R.id.spinnerEstadoM);
        spinnerEstado = (Spinner) findViewById(R.id.spinnerMunicipioM);
        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexoM);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(AddNewContactoAgenda.this,android.R.layout.simple_dropdown_item_1line, oMunicipio);
        ArrayAdapter<String> ab = new ArrayAdapter<String>(AddNewContactoAgenda.this,android.R.layout.simple_dropdown_item_1line, oEstado);
        ArrayAdapter<String> ac = new ArrayAdapter<String>(AddNewContactoAgenda.this,android.R.layout.simple_dropdown_item_1line, oSexo);

        spinnerMunicipio.setAdapter(aa);
        spinnerMunicipio.setOnItemSelectedListener(this);

        spinnerEstado.setAdapter(ab);
        spinnerEstado.setOnItemSelectedListener(this);

        spinnerSexo.setAdapter(ac);
        spinnerSexo.setOnItemSelectedListener(this);
    }

    public void addNewContactBtn(View view) {
        usuario = cargarIdusuario();
        SenderNewContacto s = new SenderNewContacto(AddNewContactoAgenda.this, urlAddress, oEstado[opE], oMunicipio[opM], oSexo[opS], usuario, 1, nombres, apellido_paterno, apellido_materno, telefono, domicilio, fecNac, estCiv, escolaridad, ocupacion);
        s.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.spinnerEstadoM){
            opM = i;
        }
        if(adapterView.getId()==R.id.spinnerMunicipioM){
            opE = i;
        }
        if(adapterView.getId()==R.id.spinnerSexoM){
            opS = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public int cargarIdusuario() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getInt("id", 0);
    }
}