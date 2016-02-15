/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.eBanking.servicio;

import com.espe.distribuidas.eBanking.dao.SesionDAO;
import com.espe.distribuidas.eBanking.modelo.Sesion;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andres Vr
 */
@LocalBean
@Stateless
public class SesionServicio {

    private SesionDAO sesionDAO = new SesionDAO();

    public void insertar(Sesion sesion) {
        this.sesionDAO.save(sesion);
    }
    public Sesion obtenerSesion(){
    List<Sesion> sesiones=this.sesionDAO.find().asList();
    return sesiones.get(sesiones.size());
    }
}
