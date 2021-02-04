package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerNewContacto;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerNewPacienteP;
import com.aa.controldeatencionpsicolgica.Handlers.Connector;
import com.aa.controldeatencionpsicolgica.MenuActivity;
import com.aa.controldeatencionpsicolgica.MenuActivityPeritaje;

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

public class SenderNewPacienteP extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    String sexo;
    EditText nombres, apellido_paterno, apellido_materno, fecNac, CURP;
    String nom;
    String ap;
    String am;
    String curp;
    String fechaN;
    int usuario, caso;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    ProgressDialog pd;

    public SenderNewPacienteP(Context c, String urlAddress, String sexo, int usuario, int caso, EditText... editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.sexo = sexo;
        this.usuario = usuario;
        this.caso = caso;

        this.nombres = editTexts[0];
        this.apellido_paterno = editTexts[1];
        this.apellido_materno = editTexts[2];
        this.CURP = editTexts[3];
        this.fecNac = editTexts[4];

        nom = nombres.getText().toString();
        ap = apellido_paterno.getText().toString();
        am = apellido_materno.getText().toString();
        curp = CURP.getText().toString();
        fechaN = fecNac.getText().toString();

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

                Intent ii = new Intent(c, MenuActivityPeritaje.class);
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
            bw.write(new DataPackagerNewPacienteP(nom, ap, am, sexo, fechaN, curp, date, usuario, caso).packData());

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

