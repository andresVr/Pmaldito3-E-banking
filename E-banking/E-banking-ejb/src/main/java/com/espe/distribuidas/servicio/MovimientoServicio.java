/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.servicio;

import com.espe.distribuidas.dao.MovimientoDAO;
import com.espe.distribuidas.model.Movimiento;
import javax.ejb.EJB;

/**
 *
 * @author Andres Vr
 */
public class MovimientoServicio {
    @EJB
    private MovimientoDAO movimientoDAO;
    
    public void insertarMovimiento(Movimiento movimiento){
        this.movimientoDAO.insert(movimiento);
    
    }
    
    
}
