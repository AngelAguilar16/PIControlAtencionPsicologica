package com.aa.controldeatencionpsicolgica.Model;

public class Cita {
    int id ,usuario, asistio;
    String fecha, hora, paciente;

    public Cita(int id, String fecha, String hora, String paciente, int usuario, int asistio) {
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

    public String getPaciente() {
        return paciente;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getAsistio() {
        return asistio;
    }
}
