package com.aa.controldeatencionpsicolgica.Model;

import java.io.Serializable;

public class Peritaje implements Serializable {
    int id_peritaje;
    int usuario;
    String paciente;
    String fecha;
    String hora;
    String motivo_atencion;
    String notas_sesion;

    public Peritaje(int id_peritaje, int usuario, String paciente, String fecha, String hora, String motivo_atencion, String notas_sesion) {
        this.id_peritaje = id_peritaje;
        this.usuario = usuario;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo_atencion = motivo_atencion;
        this.notas_sesion = notas_sesion;
    }

    public int getId_peritaje() {
        return id_peritaje;
    }

    public void setId_peritaje(int id_peritaje) {
        this.id_peritaje = id_peritaje;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo_atencion() {
        return motivo_atencion;
    }

    public void setMotivo_atencion(String motivo_atencion) {
        this.motivo_atencion = motivo_atencion;
    }

    public String getNotas_sesion() {
        return notas_sesion;
    }

    public void setNotas_sesion(String notas_sesion) {
        this.notas_sesion = notas_sesion;
    }
}
