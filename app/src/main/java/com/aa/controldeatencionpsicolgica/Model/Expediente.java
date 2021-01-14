package com.aa.controldeatencionpsicolgica.Model;

import java.io.Serializable;

public class Expediente implements Serializable {

    private Integer id_consulta, usuario, cita, caso, paciente;
    private String fecha, hora, motivo_atencion, notas_sesion, tipo_consulta;

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

    public Expediente(int id_consulta, int usuario, int cita, int caso, int paciente, String fecha, String hora, String motivo_atencion, String notas_sesion, String tipo_consulta) {
        this.id_consulta = id_consulta;
        this.usuario = usuario;
        this.cita = cita;
        this.caso = caso;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo_atencion = motivo_atencion;
        this.notas_sesion = notas_sesion;
        this.tipo_consulta = tipo_consulta;

    }
}
