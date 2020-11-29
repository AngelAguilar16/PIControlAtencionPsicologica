package com.aa.controldeatencionpsicolgica.Model;

public class Paciente {

    String id, fecha_registro, nombres, nombre_pmt, telefono, estado, municipio, domicilio, sexo, fecha_nacimiento, estado_civil, escolaridad, ocupacion;

    public Paciente(String id, String fecha_registro, String nombres, String nombre_pmt, String telefono, String estado, String municipio, String domicilio, String sexo, String fecha_nacimiento, String estado_civil, String escolaridad, String ocupacion) {
        this.id = id;
        this.fecha_registro = fecha_registro;
        this.nombres = nombres;
        this.nombre_pmt = nombre_pmt;
        this.telefono = telefono;
        this.estado = estado;
        this.municipio = municipio;
        this.domicilio = domicilio;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.estado_civil = estado_civil;
        this.escolaridad = escolaridad;
        this.ocupacion = ocupacion;
    }

    public String getId(){
        return id;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public String getNombres() {
        return nombres;
    }

    public String getNombre_pmt() {
        return nombre_pmt;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEstado() {
        return estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getSexo() {
        return sexo;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getEstado_civil() {
        return estado_civil;
    }

    public String getEscolaridad() {
        return escolaridad;
    }

    public String getOcupacion() {
        return ocupacion;
    }
}
