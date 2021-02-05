package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Handlers.Connector;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerReg;
import com.aa.controldeatencionpsicolgica.MenuActivity;
import com.aa.controldeatencionpsicolgica.MenuActivityPeritaje;
import com.aa.controldeatencionpsicolgica.MenuMaterial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class SenderReg extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    String t_us;
    EditText nombre, a_p, a_m, mail, password1, password2;
    int id;
    String nom,ap,am,correo,pass;

    ProgressDialog pd;

    public SenderReg(Context c, String urlAddress, String t_us, EditText...editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.t_us = t_us;

        this.nombre=editTexts[0];
        this.a_p=editTexts[1];
        this.a_m=editTexts[2];
        this.mail=editTexts[3];
        this.password1=editTexts[4];


        nom=nombre.getText().toString();
        ap=a_p.getText().toString();
        am=a_m.getText().toString();

        correo=mail.getText().toString();
        pass=password1.getText().toString();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Registrar");
        pd.setMessage("Registrando... Espere un momento");
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

        if(response != null || response.equals("false"))
        {
            if(response.equals("0") ) {
                Toast.makeText(c, "Ya hay un usuario registrado con ese correo", Toast.LENGTH_LONG).show();
            } else {
                id = Integer.parseInt(response);
                guardarDatos();
                if(t_us.equals("Peritaje")){
                    Intent ii = new Intent(c, MenuActivityPeritaje.class);
                    c.startActivity(ii);
                } else {
                    Intent ii = new Intent(c, MenuMaterial.class);
                    c.startActivity(ii);
                }

            }

        }else
        {
            Toast.makeText(c,"Error "+response,Toast.LENGTH_LONG).show();
        }
    }


    private String send()
    {

        HttpURLConnection con= Connector.connect(urlAddress);

        if(con==null)
        {
            return null;
        }

        try
        {
            OutputStream os=con.getOutputStream();


            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(new DataPackagerReg(nom,ap,am,correo,pass,t_us).packData());

            bw.flush();


            bw.close();
            os.close();

            int responseCode=con.getResponseCode();

            if(responseCode==con.HTTP_OK)
            {

                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response=new StringBuffer();

                String line;

                while ((line=br.readLine()) != null)
                {
                    response.append(line);
                }

                br.close();

                return response.toString();

            }else
            {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void guardarDatos() {

        SharedPreferences preferences = c.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Boolean s_ini = Boolean.TRUE; //True significa que la sesión se quedará iniciada cada que se inicie la aplicación, se cambiará el valor a False cuando se cierre sesión.

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", id);
        editor.putString("user", nom);
        editor.putString("ap", ap);
        editor.putString("am", am);
        editor.putString("email", correo);
        editor.putString("pass", pass);
        editor.putString("t_us", t_us);
        editor.putBoolean("s_ini", s_ini);

        editor.commit();
    }

}