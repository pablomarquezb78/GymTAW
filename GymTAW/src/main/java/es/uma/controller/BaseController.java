package es.uma.controller;

import es.uma.dto.UserRolDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.TipoEjercicio;
import es.uma.entity.UserRol;
import es.uma.ui.EjercicioUI;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class BaseController {


    protected boolean estaAutenticado(HttpSession session) {
        return session.getAttribute("user") != null;
    }
    protected boolean esAdmin(UserRolDTO rol){return rol.getRolUsuario().equals("admin");}
    protected boolean esDietista(UserRolDTO rol) {return  rol.getRolUsuario().equals("dietista");}
    protected boolean esEntrenador(UserRolDTO rol){return rol.getRolUsuario().equals("crosstrainer") || rol.getRolUsuario().equals("bodybuilder");}
    protected boolean esCliente(UserRolDTO rol) { return  rol.getRolUsuario().equals("cliente");}

}