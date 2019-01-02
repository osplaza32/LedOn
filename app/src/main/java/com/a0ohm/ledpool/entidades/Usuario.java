package com.a0ohm.ledpool.entidades;

import java.io.Serializable;

/**
 * Created by osplaza on 18-10-17.
 */
@SuppressWarnings("serial")
public class Usuario implements Serializable
{
    private String idUsuarios;
    private String nombre;

    public String getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(String idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    private String mail;

    public Usuario(String idUsuarios, String nombre, String mail, String rut, String clave, String rol, String telefono, Equipo equipo) {
        this.idUsuarios = idUsuarios;
        this.nombre = nombre;
        this.mail = mail;
        this.rut = rut;
        this.Clave = clave;
        this.rol = rol;
        this.telefono = telefono;
        this.equipo = equipo;
    }

    private String rut;
    private String Clave;
    private String rol;
    private String telefono;
    private Equipo equipo;
    private  SubUsuario sub;

    public Usuario(String idUsuarios, String nombre, String mail, String rut, String clave, String rol, String telefono, SubUsuario sub) {
        this.idUsuarios = idUsuarios;
        this.nombre = nombre;
        this.mail = mail;
        this.rut = rut;
        Clave = clave;
        this.rol = rol;
        this.telefono = telefono;
        this.sub = sub;
    }
}
