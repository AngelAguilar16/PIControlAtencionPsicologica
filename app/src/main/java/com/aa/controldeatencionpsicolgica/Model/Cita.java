package com.aa.controldeatencionpsicolgica.Model;

public class Cita {

    String id, fecha, hora, paciente, nombre, usuario, asistio;

    public Cita(String id, String fecha, String hora, String paciente, String nombre, String usuario, String asistio) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.nombre = nombre;
        this.usuario = usuario;
        this.asistio = asistio;
    }

    public String getId() {
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

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAsistio() {
        return asistio;
    }
}
