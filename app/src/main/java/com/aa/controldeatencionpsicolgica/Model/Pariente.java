package com.aa.controldeatencionpsicolgica.Model;

public class Pariente {

    int id , id_paciente;
    String nombre, tipo;

    public Pariente(int id, int id_paciente, String nombre, String tipo) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public int getPaciente() {
        return id_paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}
