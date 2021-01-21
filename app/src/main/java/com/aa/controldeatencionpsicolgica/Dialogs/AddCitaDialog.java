package com.aa.controldeatencionpsicolgica.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.aa.controldeatencionpsicolgica.AddNewCitaActivity;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import com.aa.controldeatencionpsicolgica.Sender.SenderCita;
import com.aa.controldeatencionpsicolgica.Sender.SenderNewContacto;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCitaDialog extends DialogFragment {

    public static final String TAG = "acd";
    private Toolbar toolbar;
    EditText hora, fecha;
    Spinner paciente;
    Context context;
    TextInputLayout fechaLayout, horaLayout;
    int usuario, np;
    ArrayList<Paciente> pacientes;
    String urlAddress="http://192.168.1.78/dif/addCita.php";

    public static AddCitaDialog display(FragmentManager fragmentManager) {
        AddCitaDialog exampleDialog = new AddCitaDialog();
        exampleDialog.show(fragmentManager, TAG);
        return exampleDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.Widget_MaterialComponents_Slider);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_add_cita, container, false);
        context = getContext();
        toolbar = view.findViewById(R.id.toolbar);
        hora = view.findViewById(R.id.editTextHoraCita);
        fecha = view.findViewById(R.id.editTextFechaCita);
        horaLayout = view.findViewById(R.id.text_input_layout_horacita);
        fechaLayout = view.findViewById(R.id.text_input_layout_fechacita);
        paciente = view.findViewById(R.id.spinerPacientes);

        pacientes = new ArrayList<>();

        //pacientes = (ArrayList<Paciente>)getArguments().getSerializable("pacienteData");


        fechaLayout.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            //To show current date in the datepicker
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                    selectedmonth = selectedmonth + 1;
                    String fec = "" + selectedyear + "/" + selectedmonth + "/" + selectedday;
                    fecha.setText(fec);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        });

        fecha.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            //To show current date in the datepicker
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                    selectedmonth = selectedmonth + 1;
                    String fec = "" + selectedyear + "/" + selectedmonth + "/" + selectedday;
                    fecha.setText(fec);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hora.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        getUsuarios();
        setSpinner();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Nueva cita");
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_save) {
                    addNewCitaBtn();
                }

                return true;
            }
        });
    }

    public void addNewCitaBtn() {
        usuario = cargarIdusuario();
        SenderCita s = new SenderCita(context, urlAddress, fecha.getText().toString(), hora.getText().toString(), cargarIdusuario(), 1,pacientes.get(np).getId());
        s.execute();
    }


    public int cargarIdusuario() {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getInt("id", 0);
    }

    private void setSpinner() {

        if (pacientes != null) {
            ArrayAdapter<Paciente> adapter = new ArrayAdapter<Paciente>(context, android.R.layout.simple_spinner_dropdown_item, pacientes);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

            paciente.setAdapter(adapter);

            // Spinner click listener
            paciente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    np = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            Toast.makeText(context,"p  AAAA",Toast.LENGTH_LONG).show();

        }


    }

    private void getUsuarios(){
        StringRequest stringRequest = new StringRequest("http://192.168.1.78/dif/listPacientes.php?usuario="+ cargarIdusuario(), response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    //Paciente p = new Paciente(pacObj.getString("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    //Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getString("fecha_registro"),pacObj.getString("nombre"),pacObj.getString("ap"),pacObj.getString("am"),pacObj.getString("nombre_pmt"),pacObj.getString("ap_pmt"),pacObj.getString("am_pmt"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("municipio"),pacObj.getString("calle"),pacObj.getString("numero_casa"),pacObj.getString("cp"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"));
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"), pacObj.getInt("caso"));
                    pacientes.add(p);
                }
                //Toast.makeText(AgendaActivity.this,"Funcion Activada",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(context).addToRequestQueue(stringRequest);
    }

    /*public void addNewContactBtn() {
        usuario = cargarIdusuario();
        SenderNewContacto s = new SenderNewContacto(context, urlAddress, oEstado[opE], oMunicipio[opM], oSexo[opS], usuario, nombres, apellido_paterno, apellido_materno, telefono, domicilio, fecNac, estCiv, escolaridad, ocupacion);
        s.execute();
    }*/

}
