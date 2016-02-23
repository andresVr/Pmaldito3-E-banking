/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.eBanking.modelo.Sesion;
import com.espe.distribuidas.eBanking.servicio.SesionServicio;
import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.model.Cuenta;
import com.espe.distribuidas.servicio.ClienteServicio;
import com.espe.distribuidas.servicio.CuentaServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class ConsolidadoBean implements Serializable{

    @EJB
    private CuentaServicio cuentaServicio;

    @EJB
    private ClienteServicio clienteServicio;

    @EJB
    private SesionServicio sesionServicio;

    private List<Sesion>sesiones;
    
    private Cliente clienteInicio;
    
    private List<Cuenta> cuentas;
    
    private Cuenta totalSaldo;
    
    @PostConstruct
    public void init() {
        Sesion sesionTmp = sesionServicio.obtenerSesion();
        this.clienteInicio = clienteServicio.obtenerclientePorId(sesionTmp.getIdCliente());
       this.cuentas=this.cuentaServicio.obtenerConsolidado(sesionTmp.idCliente);
       totalSaldo=new Cuenta();
       totalSaldo.setSaldo(this.cuentaServicio.totalConsolidado(this.cuentas));

    }
    


    public Cliente getClienteInicio() {
        return clienteInicio;
    }

    public void setClienteInicio(Cliente clienteInicio) {
        this.clienteInicio = clienteInicio;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public Cuenta getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(Cuenta totalSaldo) {
        this.totalSaldo = totalSaldo;
    }


}
