package com.aa.controldeatencionpsicolgica.Model;

import java.io.Serializable;

public class CasoU implements Serializable {
    private int id_caso;
    private String descripcion_general, nombres, ap, am;


    public CasoU(int id_caso,String descripcion_general, String nombres, String ap, String am) {
        this.id_caso = id_caso;

        this.descripcion_general = descripcion_general;
        this.nombres = nombres;
        this.ap = ap;
        this.am = am;
    }

    public int getId_caso() {
        return id_caso;
    }

    public void setId_caso(int id_caso) {
        this.id_caso = id_caso;
    }

    public String getDescripcion_general() {
        return descripcion_general;
    }

    public void setDescripcion_general(String descripcion_general) {
        this.descripcion_general = descripcion_general;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }
}
