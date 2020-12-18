package com.aa.controldeatencionpsicolgica.Model;

public class Paciente {

    String id;
    String fecha_registro;
    String nombres;
    String ap;
    String am;
    String nombre_pmt;
    String ap_pmt;
    String am_pmt;
    String telefono;
    String estado;
    String municipio;
    String localidad;
    String calle;
    String numero_casa;
    String cp;
    String sexo;
    String fecha_nacimiento;
    String estado_civil;
    String escolaridad;
    String ocupacion;

    public Paciente(String id, String fecha_registro, String nombres, String ap, String am, String nombre_pmt, String ap_pmt, String am_pmt, String telefono, String estado, String municipio, String localidad, String calle, String numero_casa, String cp, String sexo, String fecha_nacimiento, String estado_civil, String escolaridad, String ocupacion) {
        this.id = id;
        this.fecha_registro = fecha_registro;
        this.nombres = nombres;
        this.ap = ap;
        this.am = am;
        this.nombre_pmt = nombre_pmt;
        this.ap_pmt = ap_pmt;
        this.am_pmt = am_pmt;
        this.telefono = telefono;
        this.estado = estado;
        this.municipio = municipio;
        this.localidad = localidad;
        this.calle = calle;
        this.numero_casa = numero_casa;
        this.cp = cp;
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

    public String getAp() {
        return ap;
    }

    public String getAm() { return am; }

    public String getNombre_pmt() { return nombre_pmt; }

    public String getAp_pmt() { return ap_pmt; }

    public String getAm_pmt() { return am_pmt; }

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
        return calle;
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
