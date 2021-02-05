package com.aa.controldeatencionpsicolgica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Usuario;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuMaterialPeritaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_material_peritaje);
        getUsuario(cargarCorreo());

        BottomNavigationView bottomNavP = findViewById(R.id.bottomNavP);

        openFragment(new ListaPeritaje());

        bottomNavP.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeP:
                        openFragment(new ListaPeritaje());
                        return true;

                    case R.id.trendP:
                        openFragment(new CitasPActivity());
                        return true;

                    case R.id.accountP:
                        openFragment(new AddNewPacienteP());
                        return true;

                    case R.id.settingP:
                        openFragment(new AddNewCasoP());
                        return true;
                }

                return false;
            }
        });
    }

    void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutP, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public String cargarCorreo() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        return preferences.getString("email", "Error");
    }

    public void guardarDatos(int id, String nombre, String ap, String am, String email, String pass, String t_us) {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Boolean s_ini = Boolean.TRUE; //True significa que la sesión se quedará iniciada cada que se inicie la aplicación, se cambiará el valor a False cuando se cierre sesión.

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", id);
        editor.putString("user", nombre);
        editor.putString("ap", ap);
        editor.putString("am", am);
        editor.putString("email", email);
        editor.putString("pass", pass);
        editor.putString("t_us", t_us);
        editor.putBoolean("s_ini", s_ini);

        editor.apply();
    }

    public void getUsuario(String correo){
        StringRequest stringRequest = new StringRequest(Global.ip + "getUsuario.php?correo=" + correo, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("Usuario");
                for (int i = 0; i < array.length(); i++){
                    JSONObject usuObj = array.getJSONObject(i);

                    Usuario u = new Usuario(usuObj.getInt("id_usuario"),
                            usuObj.getString("nombres"),
                            usuObj.getString("ap"),
                            usuObj.getString("am"),
                            usuObj.getString("correo"),
                            usuObj.getString("password"),
                            usuObj.getString("tipo_usuario"));
                    Global.setUsuario(u.getId_usuario());
                    guardarDatos(u.getId_usuario(), u.getNombre(), u.getAp(), u.getAm(), u.getCorreo(), u.getPassword(), u.getTipo_usuario());
                    //Toast.makeText(MenuActivityPeritaje.this, u.getId_usuario() + "", Toast.LENGTH_SHORT).show();

                }


            } catch (JSONException e) {
                Toast.makeText(MenuMaterialPeritaje.this,"Hubo un error" + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}