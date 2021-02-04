package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.Handlers.Connector;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerNewContacto;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SenderNewContacto extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    String estado, municipio, sexo;
    EditText nombres, apellido_paterno, apellido_materno, telefono, domicilio, fecNac, estCiv, escolaridad, ocupacion;
    String nom;
    String ap;
    String am;
    String tel;
    String dom;
    String fechaN;
    String estadoC;
    String esc;
    String ocup;
    int usuario, caso;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    ProgressDialog pd;

    public SenderNewContacto(Context c, String urlAddress, String estado, String municipio, String sexo, int usuario, int caso, EditText... editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.estado = estado;
        this.municipio = municipio;
        this.sexo = sexo;
        this.usuario = usuario;
        this.caso = caso;

        this.nombres = editTexts[0];
        this.apellido_paterno = editTexts[1];
        this.apellido_materno = editTexts[2];
        this.telefono = editTexts[3];
        this.domicilio = editTexts[4];
        this.fecNac = editTexts[5];
        this.estCiv = editTexts[6];
        this.escolaridad = editTexts[7];
        this.ocupacion = editTexts[8];

        nom = nombres.getText().toString();
        ap = apellido_paterno.getText().toString();
        am = apellido_materno.getText().toString();
        tel = telefono.getText().toString();
        dom = domicilio.getText().toString();
        fechaN = fecNac.getText().toString();
        estadoC = estCiv.getText().toString();
        esc = escolaridad.getText().toString();
        ocup = ocupacion.getText().toString();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Registrar paciente");
        pd.setMessage("Registrando paciente... Espere un momento");
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
            if (response.equals("1")) {
                //guardarDatos();

                Intent ii = new Intent(c, MenuActivity.class);
                c.startActivity(ii);

            } else if(response.equals("2")){
                Toast.makeText(c, "Error!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Ya existe", Toast.LENGTH_LONG).show();
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
            bw.write(new DataPackagerNewContacto(nom, ap, am, tel, dom, sexo, fechaN, estadoC, esc, ocup, date, estado, municipio, usuario, caso).packData());

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
}
