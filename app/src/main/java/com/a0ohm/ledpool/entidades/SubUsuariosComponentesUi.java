package com.a0ohm.ledpool.entidades;

import android.widget.Button;
import android.widget.EditText;

/**
 * Created by osplaza on 10-11-17.
 */

public class SubUsuariosComponentesUi
{


    public SubUsuariosComponentesUi(Button agregar, Button eliminar, EditText telefono, EditText nombre, EditText clave) {
        Agregar = agregar;
        Eliminar = eliminar;
        Telefono = telefono;
        Nombre = nombre;
        Clave = clave;
    }



    public Button getAgregar() {
        return Agregar;
    }

    public void setAgregar(Button agregar) {
        Agregar = agregar;
    }

    public Button getEliminar() {
        return Eliminar;
    }

    public void setEliminar(Button eliminar) {
        Eliminar = eliminar;
    }

    public EditText getTelefono() {
        return Telefono;
    }

    public void setTelefono(EditText telefono) {
        Telefono = telefono;
    }

    public EditText getNombre() {
        return Nombre;
    }

    public void setNombre(EditText nombre) {
        Nombre = nombre;
    }

    public EditText getClave() {
        return Clave;
    }

    public void setClave(EditText clave) {
        Clave = clave;
    }

    private EditText Telefono;
    private EditText Nombre;
    private EditText Clave;
    private Button Eliminar;
    private Button Agregar;




}
