package com.aa.controldeatencionpsicolgica.Model;

public class Usuario {
    Integer id_usuario;
    String nombre;
    String ap;
    String am;
    String correo;
    String password;
    String tipo_usuario;

    public Usuario(Integer id_usuario, String nombre, String ap, String am, String correo, String password, String tipo_usuario) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.ap = ap;
        this.am = am;
        this.correo = correo;
        this.password = password;
        this.tipo_usuario = tipo_usuario;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
