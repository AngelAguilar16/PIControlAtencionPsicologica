package com.aa.controldeatencionpsicolgica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Usuario;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Bienvenido");
        getUsuario(cargarCorreo());
        //Toast.makeText(MenuActivity.this, nombre + "", Toast.LENGTH_LONG).show();
    }

    public String cargarCorreo() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        return mail = preferences.getString("email", "Error");
    }

    public void agendaBtn(View v){
        Intent i = new Intent(MenuActivity.this, AgendaActivity.class);
        startActivity(i);
    }

    public void citasBtn(View v){
        Intent i = new Intent(MenuActivity.this, CitasActivity.class);
        startActivity(i);
    }

    public void logoutBtn(View view) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("s_ini", Boolean.FALSE);
        editor.commit();

        Intent i = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void getUsuario(String correo){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.69/dif/getUsuario.php", response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("Usuario");
                for (int i = 0; i < array.length(); i++){
                    JSONObject usuObj = array.getJSONObject(i);
                    if (usuObj.getString("correo").equals(correo)) {
                        Usuario u = new Usuario(usuObj.getInt("id_usuario"),
                                                usuObj.getString("nombre"),
                                                usuObj.getString("ap"),
                                                usuObj.getString("am"),
                                                usuObj.getString("correo"),
                                                usuObj.getString("password"),
                                                usuObj.getString("tipo_usuario"));
                        guardarDatos(u.getId_usuario(), u.getNombre(), u.getAp(), u.getAm(), u.getCorreo(), u.getPassword(), u.getTipo_usuario());
                    }

                }


            } catch (JSONException e) {
                Toast.makeText(MenuActivity.this,"Hubo un error",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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

}