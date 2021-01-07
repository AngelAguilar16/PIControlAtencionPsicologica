package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.AgendaActivity;
import com.aa.controldeatencionpsicolgica.Connector;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerUsuario;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Usuario;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class SenderUsuario extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    String id_usuario, nombre, ap, am, correo, password, tipo_usuario;
    ProgressDialog pd;

    public SenderUsuario(Context c, String urlAddress, String mail) {
        this.c = c;
        this.urlAddress = urlAddress;

        this.correo = mail;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Iniciar Sesión");
        pd.setMessage("Iniciando sesión... Espere un momento");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        pd.dismiss();
        if (response != null) {
            if (response.equals("0")) {
                //guardarDatos();
                Toast.makeText(c, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray("Usuario");

                    JSONObject usuObj = array.getJSONObject(0);
                    Usuario u = new Usuario(usuObj.getInt("id_usuario"),
                                            usuObj.getString("nombre"),
                                            usuObj.getString("ap"),
                                            usuObj.getString("am"),
                                            usuObj.getString("correo"),
                                            usuObj.getString("password"),
                                            usuObj.getString("tipo_usuario"));
                    //Toast.makeText(c,"a "+u.getCorreo(),Toast.LENGTH_LONG).show();
                    guardarDatos(u.getId_usuario(), u.getNombre(), u.getAp(), u.getAm(), u.getCorreo(), u.getPassword(), u.getTipo_usuario());
                } catch (JSONException e) {
                    Toast.makeText(c,"Funcion No Jala " + e,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        } else {
            Toast.makeText(c, "Error " + response, Toast.LENGTH_LONG).show();
        }
    }


    private String send() {

        HttpURLConnection con = Connector.connect(urlAddress);

        if (con == null) {
            return null;
        }

        try {
            OutputStream os = con.getOutputStream();


            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(new DataPackagerUsuario(correo).packData());

            bw.flush();


            bw.close();
            os.close();

            int responseCode = con.getResponseCode();

            if (responseCode == con.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                return response.toString();

            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void guardarDatos(int id, String nombre, String ap, String am, String email, String pass, String t_us) {

        SharedPreferences preferences = c.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

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

