/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.eBanking.modelo.Telefonia;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.json.stream.JsonParser.Event;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class TelefoniaBean {

    Telefonia telefonia;
    String respuesta;

    @PostConstruct
    public void init() {
        telefonia = new Telefonia();
        respuesta = new String();
    }

    public Telefonia getTelefonia() {
        return telefonia;

    }

    public void onChange(Event event) {
        System.out.println(this.telefonia.getResultado());
        this.respuesta=this.telefonia.getResultado();
    }

    public void setTelefonia(Telefonia telefonia) {
        this.telefonia = telefonia;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public void ejecutar() {
        System.out.println(this.telefonia.getResultado());
    }

}
