/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.eBanking.servicio;

import com.espe.distribuidas.eBanking.dao.UsuarioDAO;
import com.espe.distribuidas.eBanking.modelo.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author david
 */
@LocalBean
@Stateless
public class UsuarioServicio {
    
    @EJB
    private UsuarioDAO usuarioDAO;
    
    public List<Usuario> obtenerTodosLosUsuarios(){
        return this.usuarioDAO.find().asList();
    }
    
    public Usuario obtenerUsuarioPorNombreUsuario(String nu){
        return this.usuarioDAO.findOne("nombreUsuario", nu);
    }
    
    public void insertarUsuario(Usuario u){
        this.usuarioDAO.save(u);
    }
    
    
}
