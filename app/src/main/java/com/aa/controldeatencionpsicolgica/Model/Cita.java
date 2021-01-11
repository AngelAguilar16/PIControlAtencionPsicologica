package com.aa.controldeatencionpsicolgica.Model;

public class Cita {
    int id, paciente ,usuario, asistio;
    String fecha, hora;

    public Cita(int id, String fecha, String hora, int paciente, int usuario, int asistio) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.usuario = usuario;
        this.asistio = asistio;
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
}
