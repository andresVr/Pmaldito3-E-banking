/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.eBanking.modelo;

/**
 *
 * @author Andres Vr
 */
public class Telefonia {
    private String telefono;
    private String montoRecarga;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMontoRecarga() {
        return montoRecarga;
    }

    public void setMontoRecarga(String montoRecarga) {
        this.montoRecarga = montoRecarga;
    }

    @Override
    public String toString() {
        return "Telefonia{" + "telefono=" + telefono + ", montoRecarga=" + montoRecarga + '}';
    }
    
}
