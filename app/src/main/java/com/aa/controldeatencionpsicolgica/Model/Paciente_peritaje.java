package com.aa.controldeatencionpsicolgica.Model;

import java.io.Serializable;
import java.util.List;

public class Paciente_peritaje implements Serializable {
    int id_pacp;
    int usuario;
    String fecha_registro;
    String nombres;
    String ap;
    String am;
    String sexo;
    String fecha_nacimiento;
    String CURP;
    int caso;
    int id_global;

    public Paciente_peritaje(int id_pacp, int usuario, String fecha_registro, String nombres, String ap, String am, String sexo, String fecha_nacimiento, String CURP, int caso) {
        this.id_pacp = id_pacp;
        this.usuario = usuario;
        this.fecha_registro = fecha_registro;
        this.nombres = nombres;
        this.ap = ap;
        this.am = am;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.CURP = CURP;
        this.caso = caso;
    }

    public Paciente_peritaje(int id_pacp, int id_global, String nombre, String ap, String am, int usuario){
        this.id_pacp = id_pacp;
        this.usuario = usuario;
        this.id_global = id_global;
        this.nombres = nombre;
        this.ap = ap;
        this.am = am;
    }
    public int getId_pacp() {
        return id_pacp;
    }

    public void setId_pacp(int id_pacp) {
        this.id_pacp = id_pacp;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public int getCaso() {
        return caso;
    }

    public void setCaso(int caso) {
        this.caso = caso;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Paciente_peritaje other = (Paciente_peritaje) obj;
        if (id_pacp != other.id_pacp)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return nombres;
    }

    private List<Paciente_peritaje> pacientes;
}
