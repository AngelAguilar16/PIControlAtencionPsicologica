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
import com.aa.controldeatencionpsicolgica.MenuMaterialPeritaje;
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
    String a;

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

        //Toast.makeText(c,"" + tipo_usuario,Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Void... params) {return this.send();}

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();

        if (response != null) {
            if (response.equals("0")) {

                Toast.makeText(c, "El usuario no existe", Toast.LENGTH_LONG).show();
            } else {
                guardarDatos();
                //Toast.makeText(c, "" + response, Toast.LENGTH_LONG).show();
                if(response.equals("Peritaje")){
                    Intent ii = new Intent(c, MenuMaterialPeritaje.class);
                    c.startActivity(ii);
                } else if (response.equals("Psicología") || response.equals("Psiquiatría")){
                    Intent ii = new Intent(c, MenuMaterial.class);
                    c.startActivity(ii);
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


        editor.apply();
    }


}
