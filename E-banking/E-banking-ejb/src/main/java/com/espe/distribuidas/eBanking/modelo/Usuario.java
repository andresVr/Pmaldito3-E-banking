/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.eBanking.modelo;

import com.espe.distribuidas.eBanking.persistence.BaseEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author david
 */
@Entity(value = "usuario")
public class Usuario extends BaseEntity {

    private double montoMaximo;
    private String codigoCliente;
    private int activo;
    private String clave;
    private String nombreUsuario;
    private String correo;
    private String numeroCuenta;

    public double getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(double montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
       // System.err.println("clave real:"+clave);
        //this.clave = DigestUtils.md5Hex(clave);
       this.clave=clave;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    @Override
    public String toString() {
        return "Usuario{" + "montoMaximo=" + montoMaximo + ", codigoCliente=" + codigoCliente + ", activo=" + activo + ", clave=" + clave + ", nombreUsuario=" + nombreUsuario + ", correo=" + correo + ", numeroCuenta=" + numeroCuenta + '}';
    }

}
