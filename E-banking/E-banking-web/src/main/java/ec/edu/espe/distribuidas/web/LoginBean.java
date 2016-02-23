/*
 * R&R S.A.
 * Sistema: Spotlights&Wires
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *  
 *  
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.eBanking.modelo.Sesion;
import com.espe.distribuidas.eBanking.modelo.Usuario;
import com.espe.distribuidas.eBanking.persistence.PersistenceManager;
import com.espe.distribuidas.eBanking.servicio.SesionServicio;
import com.espe.distribuidas.eBanking.servicio.UsuarioServicio;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Clase LoginBean que maneja index.xhtml.
 *
 * @author R&R S.A.
 */
@ViewScoped
@ManagedBean
public class LoginBean implements Serializable {

    @EJB
    private UsuarioServicio usuarioServicio;

    @EJB
    private SesionServicio sesionServicio;
    /**
     * variable de tipo usuario.
     */
    private Usuario usuario;

    PersistenceManager manger = new PersistenceManager();

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        usuario = new Usuario();
    }

    /**
     * metodo que valida el inicio de sesion a la aplicacion.
     *
     * @return retorna un string con el argumento de redirección a la pagina
     * despues de iniciar sesion.
     */
    public String iniciarSesion() {
        Usuario usuariotmp;
        String redireccion = null;
        Sesion sesion=new Sesion();
        try {
            usuariotmp = usuarioServicio.buscarPorUsuarioPassword(usuario.getNombreUsuario(), usuario.getClave());
            if (usuariotmp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Bienvenido"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                redireccion = "/views/actionConsolidado?faces-redirect=true";
                sesion.setIdCliente(usuariotmp.getCodigoCliente());
                sesion.setFechaSesion(new Date());
                this.sesionServicio.insertar(sesion);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Credenciales Incorrectas"));

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Credenciales Incorrectas"));

        }
        return redireccion;
    }

    /**
     * metodo get del objeto usuario.
     *
     * @return retorna un tipo usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * metodo set de usuario.
     *
     * @param usuario acepta un objeto de tipo usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void cerrarSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

}
