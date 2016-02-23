/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.servicio;

import com.espe.distribuidas.dao.MovimientoDAO;
import com.espe.distribuidas.model.Cuenta;
import com.espe.distribuidas.model.Movimiento;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andres Vr
 */
@LocalBean
@Stateless
public class MovimientoServicio {

    @EJB
    private MovimientoDAO movimientoDAO;

    @EJB
    private CuentaServicio cuentaServicio;

    public void insertarMovimiento(Movimiento movimiento) {
        this.movimientoDAO.insert(movimiento);

    }

    public List<Movimiento> consultarMovimientosCuenta(String cuenta) {

        Cuenta cuentaMovimeinto = this.cuentaServicio.obtenerCuentaId(cuenta);
        return cuentaMovimeinto.getListaMovimientoCuenta();
    }

}
