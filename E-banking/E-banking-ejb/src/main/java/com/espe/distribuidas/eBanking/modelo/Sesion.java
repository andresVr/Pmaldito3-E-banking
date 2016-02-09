/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.eBanking.modelo;

import com.espe.distribuidas.eBanking.persistence.BaseEntity;
import java.util.Date;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author Andres Vr
 */
@Entity(value = "sesion")
public class Sesion extends BaseEntity{

    public String idCliente;
    public Date fechaSesion;

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(Date fechaSesion) {
        this.fechaSesion = fechaSesion;
    }
    
    
    @Override
    public String toString() {
            return "Sesion:"+"Id cliente:"+ idCliente+ "fecha: "+fechaSesion;
    }
    
    
}
