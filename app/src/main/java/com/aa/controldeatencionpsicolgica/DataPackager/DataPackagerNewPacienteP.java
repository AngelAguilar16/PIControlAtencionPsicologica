package com.aa.controldeatencionpsicolgica.DataPackager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class DataPackagerNewPacienteP {

    String nombres, apellido_paterno, apellido_materno, sexo, fecNac, date, curp;
    int usuario, caso;


    public DataPackagerNewPacienteP(String nombres, String apellido_paterno, String apellido_materno,String sexo,String fecNac, String curp,String date, int usuario, int caso) {

        this.date = date;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.sexo = sexo;
        this.fecNac = fecNac;
        this.curp = curp;
        this.usuario = usuario;
        this.caso = caso;
    }

    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer packedData=new StringBuffer();

        try
        {
            jo.put("fecha_registro", date);
            jo.put("nombres", nombres);
            jo.put("ap", apellido_paterno);
            jo.put("am", apellido_materno);
            jo.put("sexo", sexo);
            jo.put("fecha_nacimiento", fecNac);
            jo.put("curp", curp);
            jo.put("usuario", usuario);
            jo.put("caso", caso);


            Boolean firstValue=true;

            Iterator it=jo.keys();

            do {
                String key=it.next().toString();
                String value=jo.get(key).toString();

                if(firstValue)
                {
                    firstValue=false;
                }else
                {
                    packedData.append("&");
                }

                packedData.append(URLEncoder.encode(key,"UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(value,"UTF-8"));

            }while (it.hasNext());

            return packedData.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
