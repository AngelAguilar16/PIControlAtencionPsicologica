package com.aa.controldeatencionpsicolgica.DataPackager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;


public class DataPackagerReg {

    String nombre, ap, am, email, pass, t_us;


    public DataPackagerReg(String nombre, String ap, String am, String email, String pass, String t_us) {
        this.nombre = nombre;
        this.ap = ap;
        this.am = am;
        this.email = email;
        this.pass = pass;
        this.t_us = t_us;
    }

    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer packedData=new StringBuffer();

        try
        {
            jo.put("nombre",nombre);
            jo.put("ap",ap);
            jo.put("am",am);
            jo.put("correo", email);
            jo.put("password", pass);
            jo.put("t_usuario", t_us);

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