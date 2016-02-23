/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.eBanking.servicio.SesionServicio;
import com.espe.distribuidas.model.Cuenta;
import com.espe.distribuidas.servicio.ClienteServicio;
import com.espe.distribuidas.servicio.CuentaServicio;
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
public class TransferenciaBean {
    @EJB
    private ClienteServicio cliente;
    
    @EJB
    private CuentaServicio cuentaServicio;
    
    @EJB
    private SesionServicio servicioServicio;
    
    private List<Cuenta> cuentasCliente;
    
    private List<Cuenta> cuentas;
    
    @PostConstruct
    public void init(){
    this.cuentasCliente=this.cuentaServicio.obtenerConsolidado(this.servicioServicio.obtenerSesion().getIdCliente());
    this.cuentas=this.cuentaServicio.obtenercuentas();
    }
    
}
