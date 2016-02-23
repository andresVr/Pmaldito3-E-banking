/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.eBanking.servicio.SesionServicio;
import com.espe.distribuidas.model.Cuenta;
import com.espe.distribuidas.model.Movimiento;
import com.espe.distribuidas.servicio.CuentaServicio;
import com.espe.distribuidas.servicio.MovimientoServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class ConsultaBean implements Serializable{
    @EJB 
    private SesionServicio sesionServicio;
    
    @EJB 
    private CuentaServicio cuentaServicio;
    
    @EJB
    private MovimientoServicio movimientoServicio;
    
    private List<Cuenta> cuentas;
    
    private List<Movimiento>movimientos;
    
    private Cuenta cuenta;
    
    @PostConstruct
    public void init(){
    this.cuentas=cuentaServicio.obtenerConsolidado(sesionServicio.obtenerSesion().idCliente);
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    public void onRowSelect(SelectEvent event) {
      this.movimientos=this.movimientoServicio.consultarMovimientosCuenta(this.cuenta.getNumeroCuenta());
    }
    
}
