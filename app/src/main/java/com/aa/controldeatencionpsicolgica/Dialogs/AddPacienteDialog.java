package com.aa.controldeatencionpsicolgica.Dialogs;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.aa.controldeatencionpsicolgica.AddNewContactoAgenda;
import com.aa.controldeatencionpsicolgica.R;
import com.aa.controldeatencionpsicolgica.Sender.SenderNewContacto;

public class AddPacienteDialog extends DialogFragment {

    public static final String TAG = "example_dialog";
    private Toolbar toolbar;
    Spinner spinnerMunicipio, spinnerEstado, spinnerSexo;
    EditText nombres, apellido_paterno, apellido_materno, telefono, domicilio, sexo, fecNac, estCiv, escolaridad, ocupacion;
    String[] oMunicipio = { "Manzanillo", "Tecomán", "Armería", "Comala", "Villa de Álvarez", "Cuauhtémoc", "Ixtlahuacán", "Coquimatlán", "Minatitlán"};
    String[] oEstado = { "Colima" };
    String[] oSexo = {"Masculino", "Femenino", "Otro"};
    String urlAddress="http://192.168.1.78/dif/addContacto.php";
    int opM = 0, opE = 0, opS = 0, usuario, caso = 1;
    Context context;

    public static AddPacienteDialog display(FragmentManager fragmentManager) {
        AddPacienteDialog exampleDialog = new AddPacienteDialog();
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
        View view = inflater.inflate(R.layout.dialog_add_paciente, container, false);
        context = getContext();

        toolbar = view.findViewById(R.id.toolbar);
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


        ArrayAdapter<String> aa = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line, oMunicipio);
        ArrayAdapter<String> ab = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line, oEstado);
        ArrayAdapter<String> ac = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line, oSexo);
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

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Nuevo Paciente");
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_save) {
                    addNewContactBtn();
                }

                return true;
            }
        });
    }

    public int cargarIdusuario() {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getInt("id", 0);
    }

    public void addNewContactBtn() {
        usuario = cargarIdusuario();
        SenderNewContacto s = new SenderNewContacto(context, urlAddress, oEstado[opE], oMunicipio[opM], oSexo[opS], usuario, caso,nombres, apellido_paterno, apellido_materno, telefono, domicilio, fecNac, estCiv, escolaridad, ocupacion);
        s.execute();
    }
}
