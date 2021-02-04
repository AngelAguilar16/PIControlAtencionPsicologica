package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Usuario;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivityPeritaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_peritaje);
        getUsuario(cargarCorreo());
    }

    public void nuevoPeritaje(View view) {
        Intent i = new Intent(MenuActivityPeritaje.this, PeritajeActivity.class);
        startActivity(i);
    }

    public void reportesBtn(View view) {
        Intent i = new Intent(MenuActivityPeritaje.this, ListaPeritaje.class);
        startActivity(i);
    }

    public void logoutBtn(View view) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("s_ini", Boolean.FALSE);
        editor.commit();

        SharedPreferences pref = getSharedPreferences("a", Context.MODE_PRIVATE);

        SharedPreferences.Editor e = pref.edit();

        e.putString("u", "false");
        e.commit();

        Intent i = new Intent(MenuActivityPeritaje.this, MainActivity.class);
        startActivity(i);
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
                Toast.makeText(MenuActivityPeritaje.this,"Hubo un error" + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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

        editor.commit();
    }

    public void nuevaCita(View view) {
        Intent i = new Intent(MenuActivityPeritaje.this, CitasPActivity.class);
        startActivity(i);
    }

    public void nuevoPaciente(View view) {
        Intent i = new Intent(MenuActivityPeritaje.this, AddNewPacienteP.class);
        startActivity(i);
    }

    public void nuevoCaso(View view) {
        Intent i = new Intent(MenuActivityPeritaje.this, AddNewCasoP.class);
        startActivity(i);
    }
}