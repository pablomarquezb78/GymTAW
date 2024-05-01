package es.uma.controller;

import es.uma.dao.UserRepository;
import es.uma.entity.User;
import es.uma.entity.UserRol;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    protected UserRepository userRepository;

    @GetMapping("/")
    public String doWelcome(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/inicioAdmin";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/mostrarUsuarios")
    public String doUsuarios(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/usuarios";
            List<User> usuarios = this.userRepository.findAll();
            model.addAttribute("usuarios", usuarios);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }
}