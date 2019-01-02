package com.a0ohm.ledpool.entidades;

import java.io.Serializable;

/**
 * Created by osplaza on 18-10-17.
 */
@SuppressWarnings("serial")
public class Equipo implements Serializable
{
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLuzMAX() {
        return luzMAX;
    }

    public void setLuzMAX(String luzMAX) {
        this.luzMAX = luzMAX;
    }

    public String codigo;

    public String getNluz() {
        return nluz;
    }

    public void setNluz(String nluz) {
        this.nluz = nluz;
    }

    public String nluz;

    public Equipo(String codigo, String nluz, String luzMAX) {
        this.codigo = codigo;
        this.nluz = nluz;
        this.luzMAX = luzMAX;
    }

    public  String luzMAX;

    public Equipo(String codigo, String nluz, String luzMAX, String STRINGLinght) {
        this.codigo = codigo;
        this.nluz = nluz;
        this.luzMAX = luzMAX;
        this.STRINGLinght = STRINGLinght;
    }

    public String getSTRINGLinght() {
        return STRINGLinght;
    }

    public void setSTRINGLinght(String STRINGLinght) {
        this.STRINGLinght = STRINGLinght;
    }

    public String STRINGLinght;
}
