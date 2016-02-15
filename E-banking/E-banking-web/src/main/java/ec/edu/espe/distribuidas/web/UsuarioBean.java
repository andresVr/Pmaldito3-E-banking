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

import com.espe.distribuidas.eBanking.modelo.Usuario;
import com.espe.distribuidas.eBanking.servicio.UsuarioServicio;
import com.espe.distribuidas.servicio.CuentaServicio;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.SelectEvent;

/**
 * Clase UsuarioBean que maneja listUsuarios.xhtml.
 *
 * @author R&R S.A.
 */
@ViewScoped
@ManagedBean
public class UsuarioBean extends BaseBean implements Serializable {

    @EJB
    private UsuarioServicio usuarioServicio;

    @EJB
    private CuentaServicio cuentaServicio;

    /**
     * variable tipo lista de usuarios para setar a una tabla del formulario.
     */
    private List<Usuario> usuarios;

    /**
     * variable tipo usuario para las operaciones del CRUD.
     */
    private Usuario usuario;

    /**
     * variable tipo epleado.
     */
    private Usuario usuarioSelected;

    /**
     * variable tipo boolean para estados del formulario.
     */
    private Boolean disabled = true;

    /**
     * variable tipo String para los titulos del formulario.
     */
    private String title = "";

    /**
     * variable tipo boolean para inhabilitar datos del formulario.
     */
    private Boolean disabledCampoModificar = false;

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        this.usuarios = this.usuarioServicio.obtenerTodosLosUsuarios();
        this.usuario = new Usuario();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.nuevo();
        this.usuario = new Usuario();
        this.setTitle("Ingresar Usuario");
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.usuario = new Usuario();
        this.setTitle("Modificar Usuario");
        try {
            BeanUtils.copyProperties(this.usuario, this.usuarioSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
    public void eliminar() {
        this.usuario = new Usuario();
        try {
            BeanUtils.copyProperties(this.usuario, this.usuarioSelected);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.usuarios.remove(this.usuario);
            this.setUsuarioSelected(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo sobreescrito de la clase base permite setear en blanco y por
     * defecto los valores del formulario.
     */
    @Override
    public void cancelar() {
        super.cancelar();
        this.setUsuarioSelected(null);

    }

    /**
     * metodo que recibe el evento de seleccion de una fila de la tabla de
     * clientes.
     *
     * @param event evento de tipo seleccion activado al seleccionar un registro
     * de una tabla.
     */
    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
        this.disabledCampoModificar = true;
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (this.cuentaServicio.validarUsuario(this.usuario.getCodigoCliente()) == true) {
            if (this.cuentaServicio.relacionClienteCuenta(this.usuario.getCodigoCliente(), this.usuario.getNumeroCuenta()) == true) {
                try {
                    Usuario usuario = (Usuario) ((HttpServletRequest) context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                    this.usuario.setActivo(1);
                    this.usuario.setMontoMaximo(300);
                    this.usuarioServicio.insertarUsuario(this.usuario);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el cliente: " + this.usuario.getNombreUsuario() + " " + this.usuario.getCorreo(), null));
                } catch (Exception e) {

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error no existe relacion cliente-cuenta", null));
                this.usuario=null;
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error en el registro-usuario no existe", null));
            this.usuario=null;
        }
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptarNuevo() {
        super.nuevo();
        this.usuario = new Usuario();
    }

    /**
     * permite ingresar un usuario en la BDD.
     */
    public void registrarUsuario() {
        try {
            usuarioServicio.insertarUsuario(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Correcto"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error en el Registro"));
        }
    }

    /**
     * metodo get de titulo.
     *
     * @return retirna un string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * metodo set de titulo.
     *
     * @param title ingresa un string.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * metodo get de disabled.
     *
     * @return retirna un boolean.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * metodo set de disabled.
     *
     * @param disabled ingresa un boolean.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * metodo get de la lista de usuarios.
     *
     * @return retorna una lista de usuarios.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * metodo set de usuarios.
     *
     * @param usuarios ingresa una lista de usuarios.
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * metodo get de usuario.
     *
     * @return retorna un tipo usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * metodo set de usuario.
     *
     * @param usuario recibe un tipo usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * metodo get del usuario seleccionado.
     *
     * @return retorna un objeto tipo usuario.
     */
    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    /**
     * metodo set del usuario seleccionado.
     *
     * @param usuarioSelected retorna un tipo usuario.
     */
    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    /**
     * metodo get de desabilitar campos.
     *
     * @return retorna un boolean.
     */
    public Boolean getDisabledCampoModificar() {
        return disabledCampoModificar;
    }

    /**
     * metodo set de desabilitar campos.
     *
     * @param disabledCampoModificar acepta un boolean.
     */
    public void setDisabledCampoModificar(Boolean disabledCampoModificar) {
        this.disabledCampoModificar = disabledCampoModificar;
    }

}
