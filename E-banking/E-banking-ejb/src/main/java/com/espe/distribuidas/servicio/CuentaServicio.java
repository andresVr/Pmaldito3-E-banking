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

/**
 *
 * @author Andres Vr
 */
@LocalBean
@Stateless
public class CuentaServicio {

    @EJB
    private CuentaDAO cuentaDAO;

    @EJB
    private ClienteDAO clienteDAO;

    @EJB
    private MovimientoDAO movimientoDAO;

    @EJB
    private MovimientoServicio movimientoServicio;
    
    

    private BigDecimal total = new BigDecimal(0);

    public String obtenerCuentaClienteParte1(String cuenta) {
        Cuenta cuentatmp = new Cuenta(cuenta);
        Cuenta cuentaCliente = cuentaDAO.find(cuentatmp).get(0);
        Cliente cliente = cuentaDAO.find(cuentatmp).get(0).getClienteCuenta();
        if (cliente != null && cuentaCliente != null) {
            return "OK_" + cliente.getNombre() + "_" + cuentaCliente.getTipo() + "_" + cuentaCliente.getSaldo();
        } else {
        }
        return "NO";
    }

    public Cuenta obtenerCuentaClienteParte2(String cuenta) {
        Cuenta cuentarmp = new Cuenta();
        return cuentaDAO.find(cuentarmp).get(0);
    }

    public List<Cuenta> obtenercuentas(){
    return this.cuentaDAO.findAll();
    
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
            this.actualizarSaldo(numeroCuenta, movimientotmp.getSaldo());
            this.movimientoServicio.insertarMovimiento(movimientotmp);
            return "00" + "_" + calcularNuevoSaldo(0, cuentatmp.getSaldo(), new BigDecimal(valorDeposito));
        } else {
            return "01";

        }
    }

    public BigDecimal totalConsolidado(List<Cuenta> consolidado) {
        System.out.println("size" + consolidado.size());
        for (int i = 0; i < consolidado.size(); i++) {
            this.total = this.total.add(consolidado.get(i).getSaldo());

        }

        return this.total;
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
            this.movimientoServicio.insertarMovimiento(movimientotmp);
            this.actualizarSaldo(numeroCuenta, movimientotmp.getSaldo());
            return "00" + "_" + calcularNuevoSaldo(0, cuentatmp.getSaldo(), new BigDecimal(valorRetiro));
        } else {
            return "01";

        }

    }

    public void actualizarSaldo(String numeroCuenta, BigDecimal saldo) {
        Cuenta cuenta = new Cuenta(numeroCuenta);
        Cuenta cuentatmp = this.cuentaDAO.find(cuenta).get(0);
        cuentatmp.setSaldo(saldo);
        this.cuentaDAO.update(cuentatmp);
    }
    //e-banking

    public Boolean relacionClienteCuenta(String cedula, String cuenta) {
//        Cuenta busqueda = new Cuenta();
//        busqueda.setNumeroCuenta(cuenta);
//        Cuenta cuentatmp = this.cuentaDAO.find(busqueda).get(0);
//        return cuentatmp.getClienteCuenta().getCedula().equals(cedula);
        Boolean verificar = false;
        Cliente clientmp = new Cliente();
        clientmp.setCedula(cedula);
        Cliente cliente = clienteDAO.find(clientmp).get(0);
        System.out.println("cliente" + cliente);
        List<Cuenta> cuentas = cliente.getCuentaCliente();
        for (int i = 0; i < cuentas.size(); i++) {
            System.out.println("cuentas" + cuentas.get(i).getNumeroCuenta());
            System.out.println("pae" + cuenta);
            if (cuentas.get(i).getNumeroCuenta().equals(cuenta)) {
                System.out.println("Parametro" + cuenta);
                verificar = true;
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
        Cliente clientetmp = new Cliente();
        clientetmp.setCedula(cedula);
        return !this.clienteDAO.find(clientetmp).isEmpty();
    }

    public List<Cuenta> obtenerConsolidado(String codigoCliente) {
        Cliente clientetmp = new Cliente();
        clientetmp.setCedula(codigoCliente);
        Cliente cliente = this.clienteDAO.find(clientetmp).get(0);
        return cliente.getCuentaCliente();
    }

    public Cuenta obtenerCuentaId(String idCuenta) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(idCuenta);
        return this.cuentaDAO.find(cuenta).get(0);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String Transferencia(String numeroCuenta, String valorDeposito, String cedula, Date fecha) {

        Cuenta cuentatmp = obtenerCuentaClienteParte2(numeroCuenta);
        if (cuentatmp != null) {
            Movimiento movimientotmp = new Movimiento();
            movimientotmp.setFechaHora(fecha);
            movimientotmp.setMonto(new BigDecimal(valorDeposito));
            movimientotmp.setMovimientoCuenta(cuentatmp);
            movimientotmp.setSaldo(calcularNuevoSaldo(0, cuentatmp.getSaldo(), new BigDecimal(valorDeposito)));
            movimientotmp.setTipoMovimiento("TR");
            this.movimientoServicio.insertarMovimiento(movimientotmp);
            this.actualizarSaldo(numeroCuenta, movimientotmp.getSaldo());
            return "SI";
        }
        else
            return "NO";
    }

}
