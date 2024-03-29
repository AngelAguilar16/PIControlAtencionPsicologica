package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerPeritaje;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerReporte;
import com.aa.controldeatencionpsicolgica.Handlers.Connector;
import com.aa.controldeatencionpsicolgica.MenuActivity;
import com.aa.controldeatencionpsicolgica.MenuActivityPeritaje;
import com.aa.controldeatencionpsicolgica.MenuMaterialPeritaje;

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

public class SenderPeritaje extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText motivo, notas, paciente;
    String mot, nots, pac;
    int usuario, cita;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    ProgressDialog pd;

    public SenderPeritaje(Context c, String urlAddress, int usuario, int cita, EditText... editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.usuario = usuario;
        this.cita = cita;
        this.motivo = editTexts[0];
        this.notas = editTexts[1];

        mot = motivo.getText().toString();
        nots = notas.getText().toString();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Guardando Reporte");
        pd.setMessage("Guardando Reporte... Espere un momento");
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
                Intent ii = new Intent(c, MenuMaterialPeritaje.class);
                c.startActivity(ii);
            } else {
                Toast.makeText(c, "Error php" + response, Toast.LENGTH_LONG).show();
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
            bw.write(new DataPackagerPeritaje(cita, mot, nots, usuario, date, hora).packData());

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
