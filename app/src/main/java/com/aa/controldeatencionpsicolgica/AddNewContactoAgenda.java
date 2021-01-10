package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.aa.controldeatencionpsicolgica.Sender.SenderNewContacto;

public class AddNewContactoAgenda extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerMunicipio, spinnerEstado;
    EditText nombres, apellido_paterno, apellido_materno, telefono, estado, municipio, domicilio, sexo, fecNac, estCiv, escolaridad, ocupacion;
    String[] oMunicipio = { "Manzanillo", "Tecomán", "Armería", "Comala", "Villa de Álvarez", "Cuauhtémoc", "Ixtlahuacán", "Coquimatlán", "Minatitlán"};
    String[] oEstado = { "Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Coahuila", "Colima", "Chiapas", "Chihuahua", "Durango", "Distrito Federal", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "México", "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"};
    int opM = 0, opE = 0;
    String urlAddress="http://192.168.1.69/dif/addContacto.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contacto_agenda);

        nombres = findViewById(R.id.etNombres);
        apellido_paterno = findViewById(R.id.etApPaterno);
        apellido_materno = findViewById(R.id.etApMaterno);
        telefono = findViewById(R.id.etTelefono);
        domicilio = findViewById(R.id.etDomicilio);
        sexo = findViewById(R.id.etSexo);
        fecNac = findViewById(R.id.etFecNac);
        estCiv = findViewById(R.id.etEstCiv);
        escolaridad = findViewById(R.id.etEscolaridad);
        ocupacion = findViewById(R.id.etOcupacion);

        spinnerMunicipio = (Spinner) findViewById(R.id.spinnerMunicipio);
        spinnerEstado = (Spinner) findViewById(R.id.spinnerEstado);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(AddNewContactoAgenda.this,android.R.layout.simple_dropdown_item_1line, oMunicipio);
        ArrayAdapter<String> ab = new ArrayAdapter<String>(AddNewContactoAgenda.this,android.R.layout.simple_dropdown_item_1line, oEstado);

        spinnerMunicipio.setAdapter(aa);
        spinnerMunicipio.setOnItemSelectedListener(this);

        spinnerEstado.setAdapter(ab);
        spinnerEstado.setOnItemSelectedListener(this);
    }

    public void addNewContactBtn(View view) {
        SenderNewContacto s = new SenderNewContacto(AddNewContactoAgenda.this, urlAddress, oEstado[opE], oMunicipio[opM], nombres, apellido_paterno, apellido_materno, telefono, domicilio, sexo, fecNac, estCiv, escolaridad, ocupacion);
        s.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.spinnerMunicipio){
            opM = i;
        }
        if(adapterView.getId()==R.id.spinnerEstado){
            opE = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}