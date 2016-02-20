/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.servicio;

import com.espe.distribuidas.dao.ClienteDAO;
import com.espe.distribuidas.dao.CuentaDAO;
import com.espe.distribuidas.dao.MovimientoDAO;
import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.model.Cuenta;
import com.espe.distribuidas.model.Movimiento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;

/**
 *
 * @author Andres Vr
 */
@LocalBean
@Stateless
public class CuentaServicio {

    @EJB
    CuentaDAO cuentaDAO;

    @EJB
    ClienteDAO clienteDAO;

    @EJB
    MovimientoDAO movimientoDAO;


    public Cliente obtenerCuentaClienteParte1(String cuenta) {
        Cuenta cuentatmp = new Cuenta(cuenta);
        return cuentaDAO.find(cuentatmp).get(0).getClienteCuenta();
    }

    public Cuenta obtenerCuentaClienteParte2(String cuenta) {
        Cuenta cuentarmp = new Cuenta();
        return cuentaDAO.find(cuentarmp).get(0);
    }

    public BigDecimal calcularNuevoSaldo(Integer tipo, BigDecimal saldo, BigDecimal transaccion) {
        BigDecimal valor = null;
        if (tipo == 0) {
            valor = saldo.add(transaccion);
        } else {
            valor = saldo.subtract(transaccion);
        }
        return valor;
    }

    public String Deposito(String numeroCuenta, String valorDeposito, String cedula, Date fecha) {

        Cuenta cuentatmp = obtenerCuentaClienteParte2(numeroCuenta);
        if (cuentatmp != null) {
            Movimiento movimientotmp = new Movimiento();
            movimientotmp.setFechaHora(fecha);
            movimientotmp.setMonto(new BigDecimal(valorDeposito));
            movimientotmp.setMovimientoCuenta(cuentatmp);
            movimientotmp.setSaldo(calcularNuevoSaldo(0, cuentatmp.getSaldo(), new BigDecimal(valorDeposito)));
            movimientotmp.setTipoMovimiento("DP");
            return "00" + "_" + calcularNuevoSaldo(0, cuentatmp.getSaldo(), new BigDecimal(valorDeposito));
        } else {
            return "01";

        }
    }

    public String Retiro(String numeroCuenta, String valorRetiro, String cedula, Date fecha) {

        Cuenta cuentatmp = obtenerCuentaClienteParte2(numeroCuenta);
        if (cuentatmp != null) {

            Movimiento movimientotmp = new Movimiento();
            movimientotmp.setFechaHora(fecha);
            movimientotmp.setMonto(new BigDecimal(valorRetiro));
            movimientotmp.setMovimientoCuenta(cuentatmp);
            movimientotmp.setSaldo(calcularNuevoSaldo(1, cuentatmp.getSaldo(), new BigDecimal(valorRetiro)));
            movimientotmp.setTipoMovimiento("RP");
            return "00" + "_" + calcularNuevoSaldo(0, cuentatmp.getSaldo(), new BigDecimal(valorRetiro));
        } else {
            return "01";

        }

    }
    //e-banking

    public Boolean relacionClienteCuenta(String cedula, String cuenta) {
//        Cuenta busqueda = new Cuenta();
//        busqueda.setNumeroCuenta(cuenta);
//        Cuenta cuentatmp = this.cuentaDAO.find(busqueda).get(0);
//        return cuentatmp.getClienteCuenta().getCedula().equals(cedula);
          Boolean verificar=false; 
          Cliente clientmp=new Cliente();
          clientmp.setCedula(cedula);
          Cliente cliente=clienteDAO.find(clientmp).get(0);
          System.out.println("cliente"+cliente);
          List<Cuenta> cuentas=cliente.getCuentaCliente();
          for(int i=0;i<cuentas.size();i++)
          {
              System.out.println("cuentas"+cuentas.get(i).getNumeroCuenta());
              System.out.println("pae"+cuenta);
              if(cuentas.get(i).getNumeroCuenta().equals(cuenta)){
                  System.out.println("Parametro"+cuenta);
              verificar=true;
              }
          }
          return verificar;
    }

    public BigDecimal saldoCuenta(String numeroCuenta) {
        Cuenta tmp = new Cuenta();
        tmp.setNumeroCuenta(numeroCuenta);
        return this.cuentaDAO.find(tmp).get(0).getSaldo();

    }

    public Boolean validarUsuario(String cedula) {
        Cliente clientetmp=new Cliente();
        clientetmp.setCedula(cedula);
        return !this.clienteDAO.find(clientetmp).isEmpty();
    }

    public List<Cuenta> obtenerConsolidado(String codigoCliente) {
        Cliente clientetmp = new Cliente();
        clientetmp.setCedula(codigoCliente);
        Cuenta cuentatmp = new Cuenta();
        return this.cuentaDAO.find(cuentatmp);
    }

    public Cuenta obtenerCuentaId(String idCuenta) {
        return this.cuentaDAO.findById(idCuenta, true);
    }
}
