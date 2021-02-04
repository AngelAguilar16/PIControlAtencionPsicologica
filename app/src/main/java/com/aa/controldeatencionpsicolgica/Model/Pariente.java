package com.aa.controldeatencionpsicolgica.Model;

public class Pariente {

    int id , id_paciente, id_paciente_1;
    String tipo;

    public Pariente(int id, int id_paciente, int id_paciente_1, String tipo) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.id_paciente_1 = id_paciente_1;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public int getPaciente() {
        return id_paciente;
    }

    public int getId_paciente_1() { return id_paciente_1; }

    public String getTipo() {
        return tipo;
    }
}
