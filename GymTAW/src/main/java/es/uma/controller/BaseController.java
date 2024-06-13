package es.uma.controller;

import es.uma.entity.UserRol;
import jakarta.servlet.http.HttpSession;

public class BaseController {

    protected boolean estaAutenticado(HttpSession session) {
        return session.getAttribute("user") != null;
    }
    protected boolean esAdmin(UserRol rol){return rol.getRolUsuario().equals("admin");}
    protected boolean esEntrenador(UserRol rol){return rol.getRolUsuario().equals("crosstrainer") || rol.getRolUsuario().equals("bodybuilder");}
    protected boolean esDietista(UserRol rol) {return  rol.getRolUsuario().equals("dietista");}


}
