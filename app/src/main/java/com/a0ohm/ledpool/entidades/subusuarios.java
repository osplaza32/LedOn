package com.a0ohm.ledpool.entidades;

/**
 * Created by osplaza on 13-11-17.
 */

public class subusuarios
{
    public String getIdsubUsuarios() {
        return idsubUsuarios;
    }

    public void setIdsubUsuarios(String idsubUsuarios) {
        this.idsubUsuarios = idsubUsuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
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

    String idsubUsuarios;

    public subusuarios(String idsubUsuarios, String nombre, String idUsuario,
                       String clave, String telefono, Equipo equipo) {
        this.idsubUsuarios = idsubUsuarios;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        Clave = clave;
        this.telefono = telefono;
        this.equipo = equipo;
    }

    String nombre;
    String idUsuario;
    String Clave;
    String telefono;
    Equipo equipo;
}
