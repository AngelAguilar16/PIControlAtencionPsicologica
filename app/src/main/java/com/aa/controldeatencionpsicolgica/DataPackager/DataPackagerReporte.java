package com.aa.controldeatencionpsicolgica.DataPackager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class DataPackagerReporte {

    String motivo, notas, t_consulta, fecha, hora;
    int paciente, usuario, cita, id_global;


    public DataPackagerReporte(int paciente, String motivo, String notas, int usuario, int cita, String t_consulta, String fecha, String hora, int id_global) {
        this.paciente = paciente;
        this.motivo = motivo;
        this.notas = notas;
        this.usuario = usuario;
        this.cita = cita;
        this.t_consulta = t_consulta;
        this.fecha = fecha;
        this.hora = hora;
        this.id_global = id_global;
    }

    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer packedData=new StringBuffer();

        try
        {
            jo.put("paciente", paciente);
            jo.put("motivo", motivo);
            jo.put("notas", notas);
            jo.put("usuario", usuario);
            jo.put("cita", cita);
            jo.put("t_consulta", t_consulta);
            jo.put("fecha", fecha);
            jo.put("hora", hora);
            jo.put("id_global", id_global);


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