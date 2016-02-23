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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class TransferenciaBean implements Serializable {

    @EJB
    private ClienteServicio cliente;

    @EJB
    private CuentaServicio cuentaServicio;

    @EJB
    private SesionServicio servicioServicio;

    private List<Cuenta> cuentasCliente;

    private Cuenta cuentaCliente;

    private Cuenta cuentaTransferencia;

    private List<Cuenta> cuentas;

    private BigDecimal valor;

    private Boolean habilitarPanel = false;

    private List listaCuentas;

    @PostConstruct
    public void init() {
        this.cuentasCliente = this.cuentaServicio.obtenerConsolidado(this.servicioServicio.obtenerSesion().getIdCliente());
        this.valor = new BigDecimal(0);
        this.cuentaTransferencia = new Cuenta();
    }

    public void setearlista(List<Cuenta> cuenta, List<String> numerosDeCuentas) {
        for (int i = 0; i < cuenta.size(); i++) {
            numerosDeCuentas.add(cuenta.get(i).getNumeroCuenta());
        }
    }

    public void validarTransferencia() {
        if (this.cuentaCliente.getSaldo().compareTo(this.valor) == 1) {
            this.setHabilitarPanel(true);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "correcto"));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "Saldo Insuficiente"));
        }

    }

    public void ejecutarTransferencia() {
        if (this.cuentaServicio.existeCuenta(cuentaTransferencia) && this.cuentaCliente.getSaldo().compareTo(this.valor) == 1) {
            String salida = this.cuentaServicio.TransferenciaSalida(this.cuentaCliente.getNumeroCuenta(), this.valor.toString(), this.cuentaCliente.getNumeroCuenta(), new Date());

            String entrada = this.cuentaServicio.TransferenciaEntrada(this.cuentaTransferencia.getNumeroCuenta(), this.valor.toString(), new Date());

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Transaccion Exitosa"));
                this.cuentaTransferencia = null;
                this.cuentaCliente = null;
                this.valor = null;
                this.cuentasCliente = this.cuentaServicio.obtenerConsolidado(this.servicioServicio.obtenerSesion().getIdCliente());
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "Cunta inexistente"));
            this.cuentaTransferencia = null;
            this.cuentaCliente = null;
            this.valor = null;
            this.habilitarPanel = false;

        }
    }

    public List<Cuenta> getCuentasCliente() {
        return cuentasCliente;
    }

    public void setCuentasCliente(List<Cuenta> cuentasCliente) {
        this.cuentasCliente = cuentasCliente;
    }

    public Cuenta getCuentaCliente() {
        return cuentaCliente;
    }

    public void setCuentaCliente(Cuenta cuentaCliente) {
        this.cuentaCliente = cuentaCliente;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getHabilitarPanel() {
        return habilitarPanel;
    }

    public void setHabilitarPanel(Boolean habilitarPanel) {
        this.habilitarPanel = habilitarPanel;
    }

    public Cuenta getCuentaTransferencia() {
        return cuentaTransferencia;
    }

    public void setCuentaTransferencia(Cuenta cuentaTransferencia) {
        this.cuentaTransferencia = cuentaTransferencia;
    }

    public List getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

}
