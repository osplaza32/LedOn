package com.a0ohm.ledpool.entidades;

import android.widget.Button;

/**
 * Created by osplaza on 22-10-17.
 */

public class Luces
{
    private Button button;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    private int orden;
    private int estado;

    public Luces(Button button, int orden, int estado) {
        this.button = button;
        this.orden = orden;
        this.estado = estado;
    }


}
