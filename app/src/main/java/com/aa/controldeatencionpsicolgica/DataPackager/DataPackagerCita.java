package com.aa.controldeatencionpsicolgica.DataPackager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;


public class DataPackagerCita {

    String fecha, hora;
    int usuario, paciente, visible;


    public DataPackagerCita(String fecha, String hora, int usuario, int visible,int paciente) {
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.visible = visible;
        this.paciente = paciente;
    }

    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer packedData=new StringBuffer();

        try
        {
            jo.put("fecha", fecha);
            jo.put("hora", hora);
            jo.put("usuario", usuario);
            jo.put("visible", visible);
            jo.put("paciente", paciente);


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