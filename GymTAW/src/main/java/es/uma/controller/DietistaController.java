package es.uma.controller;

import es.uma.dao.UserRepository;
import es.uma.entity.User;
import es.uma.entity.UserRol;
import es.uma.ui.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dietista")
public class DietistaController extends BaseController{

    private final UserRepository userRepository;

    public DietistaController(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @GetMapping("/mostrarPerfil")
    public String doLoadProfile(HttpSession session,
                             Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User dietista = (User) session.getAttribute("user");
            model.addAttribute("dietista", dietista);
            dir = "dietista/dietista_perfil";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarPerfil")
    public String doEditProfile(HttpSession session,
                                Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User dietistaUser = (User) session.getAttribute("user");
            Usuario dietista = new Usuario();
            dietista.setNombre(dietistaUser.getNombre());
            dietista.setApellidos(dietistaUser.getApellidos());
            dietista.setAltura(dietistaUser.getAltura());
            dietista.setFechaNacimiento(dietistaUser.getFechaNacimiento().toString());
            dietista.setPeso(dietistaUser.getPeso());
            dietista.setDescripcionPersonal(dietistaUser.getDescripcionPersonal());
            model.addAttribute("dietista", dietista);

            dir = "dietista/dietista_editarPerfil";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarPerfil")
    public String doSaveProfile(@ModelAttribute("dietista") Usuario dietista, HttpSession session,
                                Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User dietistaUser = (User) session.getAttribute("user");
            dietistaUser.setNombre(dietista.getNombre());
            dietistaUser.setApellidos(dietista.getApellidos());
            dietistaUser.setAltura(dietista.getAltura());
            dietistaUser.setPeso(dietista.getPeso());
            dietistaUser.setDescripcionPersonal(dietista.getDescripcionPersonal());
            userRepository.save(dietistaUser);

            dir = "redirect:/dietista/mostrarPerfil";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

}
