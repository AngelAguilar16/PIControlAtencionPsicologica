package com.aa.controldeatencionpsicolgica.Model;

public class Cita {
    private int id ,usuario, asistio, paciente, id_global;
    private String fecha, hora;

    public Cita(int id, String fecha, String hora, int paciente, int usuario, int asistio, int id_global) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.usuario = usuario;
        this.asistio = asistio;
        this.id_global = id_global;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getPaciente() {
        return paciente;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getAsistio() {
        return asistio;
    }

    public int getId_global(){return id_global;}
}