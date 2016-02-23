/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.jdni.ejb;

import com.espe.distribuidas.dao.ClienteDAO;
import com.espe.distribuidas.dao.EmpleadoDAO;
import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.model.Cuenta;
import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.servicio.CuentaServicio;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Andres Vr
 */
@Stateless
public class ClienteJDNIServicio implements ClienteJDNIServicioRemote {

    @EJB
    private ClienteDAO clienteDAO;
    
    @EJB
    private EmpleadoDAO empleadoDAO;
    
    @EJB
    private CuentaServicio cuentaServicio;

    @Override
    public Boolean loginEmpleado(String usuario, String pass) {
        Empleado empleadotmp = new Empleado(usuario, pass);
        return empleadoDAO.find(empleadotmp).size() == 1;

    }

    public List<Cuenta> obterCuentasCliente(Cliente cliente) {
        Cliente clientetmp = cliente;
        return clienteDAO.find(clientetmp).get(0).getCuentaCliente();
    }

    public Cliente obtenerClienteId(Cliente cliente) {
        return clienteDAO.findById(cliente.getCedula(), true);

    }

    public List<Cliente> obtenerTodosClientes() {
        return clienteDAO.findAll();

    }


    @Override
    public String realizarDeposito(String numeroCuenta, String monto,String cedula) {
       return this.cuentaServicio.Deposito(numeroCuenta, monto, new Date());
    }

    @Override
    public String consultarCuentaCliente(String numeroCuenta) {
        if(this.cuentaServicio.obtenerCuentaId(numeroCuenta)!=null)
        return cuentaServicio.obtenerCuentaClienteParte1(numeroCuenta);
        else
            return "No";
    }

    @Override
    public String realizarRetiro(String numeroCuenta, String monto, String cedula) {
       return this.cuentaServicio.Retiro(numeroCuenta, monto, new Date());
   
    }
}
