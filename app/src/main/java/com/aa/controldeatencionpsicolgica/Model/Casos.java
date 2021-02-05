package com.aa.controldeatencionpsicolgica.Model;

import java.io.Serializable;

public class Casos implements Serializable {

    private int id, estado;
    private String fecha_apertura, descripcion_general;

    public Casos(int id, String fecha_apertura, String descripcion_general, int estado) {
        this.id = id;
        this.estado = estado;
        this.fecha_apertura = fecha_apertura;
        this.descripcion_general = descripcion_general;
    }

    public Casos(int id, String descripcion_general){
        this.id = id;
        this.descripcion_general = descripcion_general;
    }

    public int getId() {
        return id;
    }

    public int getEstado() {
        return estado;
    }

    public String getFecha_apertura() {
        return fecha_apertura;
    }

    public String getDescripcion_general() {
        return descripcion_general;
    }

}
