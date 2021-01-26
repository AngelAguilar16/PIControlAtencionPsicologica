package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Adapter.Pacientes_Adapter;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Connector;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerLog;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.MenuActivity;
import com.aa.controldeatencionpsicolgica.MenuActivityPeritaje;
import com.aa.controldeatencionpsicolgica.MenuMaterial;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Usuario;
import com.android.volley.toolbox.StringRequest;

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
import java.util.ArrayList;

public class SenderLog extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText mail, password;
    String correo, pass;
    String tipo_usuario;
    ArrayList<Usuario> lusuario = new ArrayList<>();

    ProgressDialog pd;

    public SenderLog(Context c, String urlAddress, EditText... editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;

        this.mail = editTexts[0];
        this.password = editTexts[1];

        correo = mail.getText().toString();
        pass = password.getText().toString();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Iniciar sesión");
        pd.setMessage("Iniciando sesión... Espere un momento");
        pd.show();
        getUsuario();
        //Toast.makeText(c,"" + tipo_usuario,Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Void... params) {return this.send();}

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();

        if (response != null) {
            if (response.equals("1")) {
                guardarDatos();
                String a = a();
                if(a.equals("Peritaje")){
                    Intent ii = new Intent(c, MenuActivityPeritaje.class);
                    c.startActivity(ii);
                } else {
                    Intent ii = new Intent(c, MenuActivity.class);
                    c.startActivity(ii);
                }

            } else {
                Toast.makeText(c, "El usuario no existe", Toast.LENGTH_LONG).show();
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
            bw.write(new DataPackagerLog(correo, pass).packData());

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

    public void guardarDatos() {

        SharedPreferences preferences = c.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        Boolean s_ini = Boolean.TRUE;

        editor.putString("email", correo);
        editor.putString("pass", pass);
        editor.putBoolean("s_ini", s_ini);


        editor.commit();
    }

    public void getUsuario(){

        StringRequest stringRequest = new StringRequest(Global.ip + "getUsuario.php?correo="+ correo, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("Usuario");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Usuario u = new Usuario(pacObj.getInt("id_usuario"), pacObj.getString("nombres"), pacObj.getString("ap"), pacObj.getString("am"), pacObj.getString("correo"), pacObj.getString("password"), pacObj.getString("tipo_usuario"));
                    lusuario.add(u);

                }
                Usuario pa = lusuario.get(0);
                String tu = pa.getTipo_usuario();
                guardar(tu);
                //Toast.makeText(c,"" + tu,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                //Toast.makeText(AgendaActivity.this,"Funcion No Jalo " + e,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(c).addToRequestQueue(stringRequest);


    }

    public void guardar(String u) {

        SharedPreferences preferences = c.getSharedPreferences("a", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("u", u);

        editor.commit();
    }

    public String a(){
        SharedPreferences preferences = c.getSharedPreferences("a", Context.MODE_PRIVATE);
        return preferences.getString("u", "Error");
    }
}
