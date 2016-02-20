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
    ClienteDAO clienteDAO;
@EJB
    EmpleadoDAO empleadoDAO;

     @Override
    public Boolean loginEmpleado(String usuario, String pass) {
        Empleado empleadotmp = new Empleado(usuario, pass);
        return empleadoDAO.find(empleadotmp).size() == 1;

    }


    public List<Cuenta> obterCuentasCliente(Cliente cliente) {
        Cliente clientetmp = cliente;
        return clienteDAO.find(clientetmp).get(0).getCuentaCliente();
    }
    
    public Cliente obtenerClienteId(Cliente cliente){
        return clienteDAO.findById(cliente.getCedula(), true);
    
    }
    
        public List<Cliente> obtenerTodosClientes(){
        return clienteDAO.findAll();
    
    }
   }
