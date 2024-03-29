package com.aa.controldeatencionpsicolgica.Model;

import java.io.Serializable;

public class Expediente implements Serializable {

    private String tratamiento;
    private Integer id_consulta, usuario, cita, caso, paciente;
    private String fecha, hora, motivo_atencion, notas_sesion, tipo_consulta;

    public Expediente(int id_consulta, int usuario, int cita, String fecha, String hora, String motivo_atencion, String notas_sesion, String tipo_consulta, String tratamiento) {
        this.id_consulta = id_consulta;
        this.usuario = usuario;
        this.cita = cita;
        //this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo_atencion = motivo_atencion;
        this.notas_sesion = notas_sesion;
        this.tipo_consulta = tipo_consulta;
        this.tratamiento = tratamiento;

    }

    public Expediente(Integer id_consulta, Integer usuario, Integer cita, String fecha, String hora, String motivo_atencion, String notas_sesion) {
        this.id_consulta = id_consulta;
        this.usuario = usuario;
        this.cita = cita;
        this.caso = caso;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo_atencion = motivo_atencion;
        this.notas_sesion = notas_sesion;
    }

    public Expediente(int id_cita, String motivo) {
        id_consulta = id_cita;
        motivo_atencion = motivo;
    }

    public Integer getId_consulta() {
        return id_consulta;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public Integer getCita() {
        return cita;
    }

    public Integer getCaso() {
        return caso;
    }

    public Integer getPaciente() {
        return paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getMotivo_atencion() {
        return motivo_atencion;
    }

    public String getNotas_sesion() {
        return notas_sesion;
    }

    public String getTipo_consulta() {
        return tipo_consulta;
    }

    public String getTratamiento(){return tratamiento;}
}