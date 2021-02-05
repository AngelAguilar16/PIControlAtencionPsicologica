package com.aa.controldeatencionpsicolgica.Model;

import java.io.Serializable;
import java.util.List;

public class Paciente implements Serializable {

    int id;
    int usuario;
    String fecha_registro;
    String nombre;
    String ap;
    String am;
    String nombre_pmt;
    String ap_pmt;
    String am_pmt;
    String telefono;
    String estado;
    String municipio;
    String domicilio;
    String localidad;
    String calle;
    String numero_casa;
    String cp;
    String sexo;
    String fecha_nacimiento;
    String estado_civil;
    String escolaridad;
    String ocupacion;
    int caso;
    int id_global;

    public Paciente(int id, int usuario, String fecha_registro, String nombre, String ap, String am, String nombre_pmt, String ap_pmt, String am_pmt, String telefono, String estado, String municipio, String localidad, String calle, String numero_casa, String cp, String sexo, String fecha_nacimiento, String estado_civil, String escolaridad, String ocupacion, int caso) {
        this.id = id;
        this.usuario = usuario;
        this.fecha_registro = fecha_registro;
        this.nombre = nombre;
        this.ap = ap;
        this.am = am;
        this.nombre_pmt = nombre_pmt;
        this.ap_pmt = ap_pmt;
        this.am_pmt = am_pmt;
        this.telefono = telefono;
        this.estado = estado;
        this.municipio = municipio;
        this.localidad = localidad;
        this.calle = calle; // Domicilio
        this.numero_casa = numero_casa;
        this.cp = cp;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.estado_civil = estado_civil;
        this.escolaridad = escolaridad;
        this.ocupacion = ocupacion;
        this.caso = caso;
    }

    public Paciente(int id_paciente, int usuario, String fecha_registro, String nombre, String ap, String am, String telefono, String estado, String municipio, String domicilio, String sexo, String fecha_nacimiento, String estado_civil, String escolaridad, String ocupacion, int caso) {
        this(id_paciente, usuario, fecha_registro, nombre, ap, am, "", "", "", telefono, estado, municipio,"", domicilio, "", "", sexo, fecha_nacimiento, estado_civil, escolaridad, ocupacion, caso);
    }

    public Paciente(int id_paciente, String nombre, String ap, String am){
        id = id_paciente;
        this.nombre = nombre;
        this.ap = ap;
        this.am = am;
    }

    public int getId(){
        return id;
    }

    public int getUsuario() { return usuario;}

    public String getFecha_registro() {
        return fecha_registro;
    }

    public String getNombre() {
        return nombre;
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

    public int getCaso() { return caso; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Paciente other = (Paciente) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    private List<Paciente> pacientes;
}