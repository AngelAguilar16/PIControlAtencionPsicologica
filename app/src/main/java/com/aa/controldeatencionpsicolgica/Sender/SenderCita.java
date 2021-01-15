package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.CitasActivity;
import com.aa.controldeatencionpsicolgica.Handlers.Connector;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerCita;
import com.aa.controldeatencionpsicolgica.MenuMaterial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class SenderCita extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    String fecha, hora;
    int usuario, paciente;

    ProgressDialog pd;

    public SenderCita(Context c, String urlAddress, String fecha, String hora, int usuario, int paciente) {
        this.c = c;
        this.urlAddress = urlAddress;

        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.paciente = paciente;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Agendar cita");
        pd.setMessage("Agendando cita... Espere un momento");
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
                //Toast.makeText(c, "cita realizada", Toast.LENGTH_LONG).show();
                Intent ii = new Intent(c, MenuMaterial.class);
                c.startActivity(ii);
            } else {
                Toast.makeText(c, "Hubo un error php " + response, Toast.LENGTH_LONG).show();
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
            bw.write(new DataPackagerCita(fecha, hora, usuario, paciente).packData());

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