package com.aa.controldeatencionpsicolgica.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

import com.aa.controldeatencionpsicolgica.AgendaActivity;
import com.aa.controldeatencionpsicolgica.EditContactoAgendaActivity;
import com.aa.controldeatencionpsicolgica.Fragments.AgendaFragment;
import com.aa.controldeatencionpsicolgica.MenuMaterial;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditPacienteDialog extends DialogFragment {

    public static final String TAG = "epd";
    private Toolbar toolbar;
    Context context;
    private EditText nombres, telefono, estado, apellido_paterno, apellido_materno;
    private EditText muncipio, domicilio, sexo;
    private EditText fecNac, estCiv, escolaridad, ocupacion;
    Spinner spinnerMunicipio, spinnerEstado, spinnerSexo;
    String[] oMunicipio = {"Manzanillo", "Tecomán", "Armería", "Comala", "Villa de Álvarez", "Cuauhtémoc", "Ixtlahuacán", "Coquimatlán", "Minatitlán"};
    String[] oEstado = {"Colima"};
    String[] oSexo = {"Masculino", "Femenino", "Otro"};
    String urlAddress = "http://192.168.1.78/dif/updatePacientes.php";
    int opM = 0, opE = 0, opS = 0, usuario;
    Paciente paciente;

    public static EditPacienteDialog display(FragmentManager fragmentManager) {
        EditPacienteDialog exampleDialog = new EditPacienteDialog();
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
        View view = inflater.inflate(R.layout.dialog_edit_paciente, container, false);
        context = getContext();

        toolbar = view.findViewById(R.id.toolbarEP);
        spinnerMunicipio = (Spinner) view.findViewById(R.id.spinnerMunicipioM);
        spinnerEstado = (Spinner) view.findViewById(R.id.spinnerEstadoM);
        spinnerSexo = (Spinner) view.findViewById(R.id.spinnerSexoM);
        nombres = view.findViewById(R.id.editTextNombrePaciente);
        apellido_paterno = view.findViewById(R.id.editTextAPPaciente);
        apellido_materno = view.findViewById(R.id.editTextAMPaciente);
        telefono = view.findViewById(R.id.editTextTelefonoPaciente);
        domicilio = view.findViewById(R.id.editTextDomicilioPaciente);
        fecNac = view.findViewById(R.id.editTextFCPaciente);
        estCiv = view.findViewById(R.id.editTextECPaciente);
        escolaridad = view.findViewById(R.id.editTextEscolaridadPaciente);
        ocupacion = view.findViewById(R.id.editTextOcupacionPaciente);

        paciente = (Paciente) getArguments().getSerializable("pacienteData");
        nombres.setText(paciente.getNombre());
        apellido_paterno.setText(paciente.getAp());
        apellido_materno.setText(paciente.getAm());
        telefono.setText(paciente.getTelefono());
        //estado.setText(paciente.getEstado());
        //muncipio.setText(paciente.getMunicipio());
        domicilio.setText(paciente.getDomicilio());
        //sexo.setText(paciente.getSexo());
        fecNac.setText(paciente.getFecha_nacimiento());
        estCiv.setText(paciente.getEstado_civil());
        escolaridad.setText(paciente.getEscolaridad());
        ocupacion.setText(paciente.getOcupacion());

        ArrayAdapter<String> aa = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line, oMunicipio);
        ArrayAdapter<String> ab = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line, oEstado);
        ArrayAdapter<String> ac = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line, oSexo);
        spinnerMunicipio.setAdapter(aa);
        spinnerMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opM = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEstado.setAdapter(ab);
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opE = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSexo.setAdapter(ac);
        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opS = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Toast.makeText(context, "El ID es: " + paciente.getId(), Toast.LENGTH_SHORT).show();

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        toolbar.setTitle("Nuevo Paciente");
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_save) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAddress, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, "Datos actualizados!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MenuMaterial.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parametros = new HashMap<>();

                            parametros.put("id", Integer.toString(paciente.getId()));
                            parametros.put("nombres", nombres.getText().toString());
                            parametros.put("ap",apellido_paterno.getText().toString());
                            parametros.put("am", apellido_materno.getText().toString());
                            parametros.put("telefono", telefono.getText().toString());
                            parametros.put("estado", oEstado[opE]);
                            parametros.put("municipio", oMunicipio[opM]);
                            parametros.put("domicilio", domicilio.getText().toString());
                            parametros.put("sexo", oSexo[opS]);
                            parametros.put("fecNac", fecNac.getText().toString());
                            parametros.put("estCiv", estCiv.getText().toString());
                            parametros.put("escolaridad", escolaridad.getText().toString());
                            parametros.put("ocupacion", ocupacion.getText().toString());

                            return parametros;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                }
                return true;
            }

        });
    }
}
