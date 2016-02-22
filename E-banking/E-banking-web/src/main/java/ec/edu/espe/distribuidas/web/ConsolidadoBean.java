/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.eBanking.modelo.Sesion;
import com.espe.distribuidas.eBanking.servicio.SesionServicio;
import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.servicio.ClienteServicio;
import com.espe.distribuidas.servicio.CuentaServicio;
import java.io.IOException;
import static java.lang.System.out;
import javax.jms.Connection;
import java.sql.SQLException;
import java.util.Queue;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.Session;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class ConsolidadoBean {

    @EJB
    private CuentaServicio cuentaServicio;

    @EJB
    private ClienteServicio clienteServicio;

    @EJB
    private SesionServicio sesionServicio;

    private Cliente clienteInicio;
    
    @PostConstruct
    public void init() {
        Sesion sesionTmp = sesionServicio.obtenerSesion();
        this.clienteInicio = clienteServicio.obtenerclientePorId(sesionTmp.getIdCliente());
        

    }

    public Cliente getClienteInicio() {
        return clienteInicio;
    }

    public void setClienteInicio(Cliente clienteInicio) {
        this.clienteInicio = clienteInicio;
    }


}
