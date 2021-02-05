package com.aa.controldeatencionpsicolgica.Sender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerCaso;
import com.aa.controldeatencionpsicolgica.Global.Global;
import com.aa.controldeatencionpsicolgica.Handlers.Connector;
import com.aa.controldeatencionpsicolgica.DataPackager.DataPackagerLog;
import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.MenuActivity;
import com.aa.controldeatencionpsicolgica.MenuMaterial;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SenderCaso extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    String descripcion;
    String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    ArrayList<Paciente> paciente;
    ProgressDialog pd;

    public SenderCaso(Context c, String urlAddress, String descripcion, ArrayList<Paciente> paciente) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.paciente = paciente;

        this.descripcion = descripcion;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Crear Caso");
        pd.setMessage("Creando Caso... Espere un momento");
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
            if (response.equals("false")) {
                Toast.makeText(c, "Hubo un error", Toast.LENGTH_LONG).show();
            } else {
                for (Paciente pp : paciente){
                    asignarCaso(response, pp.getId());
                }

                Intent ii = new Intent(c, MenuMaterial.class);
                c.startActivity(ii);
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
            bw.write(new DataPackagerCaso(descripcion, fecha).packData());

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

    private void asignarCaso(String caso, int paci){
        StringRequest stringRequest = new StringRequest(Global.ip + "asignarCaso.php?paciente="+ paci + "&caso=" + caso, response -> {
            try {
                Toast.makeText(c, "" + response, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(c).addToRequestQueue(stringRequest);
    }
}
