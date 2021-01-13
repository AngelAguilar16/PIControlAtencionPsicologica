package com.aa.controldeatencionpsicolgica.DataPackager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;


public class DataPackagerNewContacto {

    String nombres, apellido_paterno, apellido_materno, telefono, domicilio, sexo, fecNac, estCiv, escolaridad, ocupacion, date, estado, municipio;
    int usuario;


    public DataPackagerNewContacto(String nombres,String apellido_paterno, String apellido_materno,String telefono,String domicilio,String sexo,String fecNac,String estCiv,String escolaridad,String ocupacion, String date, String estado, String municipio, int usuario) {
        this.estado = estado;
        this.municipio = municipio;
        this.date = date;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.sexo = sexo;
        this.fecNac = fecNac;
        this.estCiv = estCiv;
        this.escolaridad = escolaridad;
        this.ocupacion = ocupacion;
        this.usuario = usuario;
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
            jo.put("telefono", telefono);
            jo.put("estado", estado);
            jo.put("municipio", municipio);
            jo.put("domicilio", domicilio);
            jo.put("sexo", sexo);
            jo.put("fecha_nacimiento", fecNac);
            jo.put("estado_civil", estCiv);
            jo.put("escolaridad", escolaridad);
            jo.put("ocupacion", ocupacion);
            jo.put("usuario", usuario);


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
