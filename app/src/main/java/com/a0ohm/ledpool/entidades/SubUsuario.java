package com.a0ohm.ledpool.entidades;

/**
 * Created by osplaza on 22-10-17.
 */

public class SubUsuario
{
    private String idsubUsuarios;

    public SubUsuario(String idsubUsuarios, String nombre, String telefono, String clave) {
        this.idsubUsuarios = idsubUsuarios;
        this.nombre = nombre;
        this.telefono = telefono;
        this.clave = clave;
    }

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
        nombre = nombre;
    }

    public String gettelefono() {
        return telefono;
    }

    public void settelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    private String nombre;
    private String telefono;
    private String clave;


}
