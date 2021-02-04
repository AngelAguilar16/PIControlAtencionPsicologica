package com.aa.controldeatencionpsicolgica.Global;


import android.content.Context;

import com.aa.controldeatencionpsicolgica.Handlers.Handler;
import com.aa.controldeatencionpsicolgica.Model.Paciente;
import com.aa.controldeatencionpsicolgica.Model.Paciente_peritaje;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Global {

    public final static String ip ="http://p5s.000webhostapp.com/dif/";
    //public final static String ip ="http://192.168.1.78/dif/";
    public static int us = 0;

    public static void setUsuario(int usuario){
        us = usuario;
    }

    public static ArrayList<Paciente> getPacientes(Context c){
        ArrayList<Paciente> pacienteList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(ip + "listPacientes.php?usuario="+us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente p = new Paciente(pacObj.getInt("id_paciente"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("telefono"), pacObj.getString("estado"), pacObj.getString("municipio"), pacObj.getString("domicilio"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("estado_civil"), pacObj.getString("escolaridad"), pacObj.getString("ocupacion"), pacObj.getInt("caso"));
                    pacienteList.add(p);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(c).addToRequestQueue(stringRequest);

        return pacienteList;
    }

    public static ArrayList<Paciente_peritaje> getPacientesP(Context c){
        ArrayList<Paciente_peritaje> pacienteList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(ip + "listPacientesP.php?usuario="+us, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("pacientesList");
                for (int i = 0; i < array.length(); i++){
                    JSONObject pacObj = array.getJSONObject(i);
                    Paciente_peritaje p = new Paciente_peritaje(pacObj.getInt("id_pacp"),pacObj.getInt("usuario"),pacObj.getString("fecha_registro"),pacObj.getString("nombres"),pacObj.getString("ap"),pacObj.getString("am"), pacObj.getString("sexo"),pacObj.getString("fecha_nacimiento"), pacObj.getString("CURP"),pacObj.getInt("caso"));
                    pacienteList.add(p);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> { });
        Handler.getInstance(c).addToRequestQueue(stringRequest);

        return pacienteList;
    }


}
