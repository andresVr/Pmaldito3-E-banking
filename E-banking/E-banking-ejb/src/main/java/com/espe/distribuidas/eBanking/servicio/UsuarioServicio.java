/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.eBanking.servicio;

import com.espe.distribuidas.eBanking.dao.UsuarioDAO;
import com.espe.distribuidas.eBanking.modelo.Usuario;
import com.espe.distribuidas.eBanking.persistence.PersistenceManager;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author david
 */
@LocalBean
@Stateless
public class UsuarioServicio {

    private PersistenceManager manger = new PersistenceManager();

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public List<Usuario> obtenerTodosLosUsuarios() {
        return this.usuarioDAO.find().asList();
    }

    public Usuario obtenerUsuarioPorNombreUsuario(String nu) {
        return this.usuarioDAO.findOne("nombreUsuario", nu);
    }

    public void insertarUsuario(Usuario u) {
        this.usuarioDAO.save(u);
    }

    /**
     *
     * @param usuario
     * @param password
     * @return
     */
    public Usuario buscarPorUsuarioPassword(String usuario, String password) {
        Usuario usu = new Usuario();
        usu.setNombreUsuario(usuario);
        usu.setClave(password);
        Query q =this.manger.context().createQuery(Usuario.class).filter("nombreUsuario", usu.getNombreUsuario()).filter("clave", usu.getClave());
        return (Usuario) this.usuarioDAO.find(q).get();
    }

}
