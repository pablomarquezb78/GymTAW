package es.uma.controller;

import es.uma.dao.PlatosRepository;
import es.uma.dao.RegistroRepository;
import es.uma.dao.UserRepository;
import es.uma.dto.UserDTO;
import es.uma.dto.UserRolDTO;
import es.uma.entity.Registro;
import es.uma.service.PlatoService;
import es.uma.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.uma.entity.User;
import es.uma.entity.UserRol;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

//@author: Todos

@Controller
public class LoginController extends  BaseController {

    @Autowired
    private UserService userService;

    final String admin = "admin";
    final String cliente = "cliente";
    final String bodybuilder = "bodybuilder";
    final String crosstrainer = "crosstrainer";
    final String dietista = "dietista";


    @GetMapping("/")
    public String doLoad(Model model, HttpSession session) {
        String webRedirect = "login";
        if (estaAutenticado(session)) {
            UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
            switch (userRolDTO.getRolUsuario()) {
                case admin:
                    webRedirect = "redirect:/admin/";
                    break;
                case cliente:

                    break;
                case bodybuilder:
                    webRedirect = "redirect:/entrenamientos/";
                    break;
                case crosstrainer:
                    webRedirect = "redirect:/entrenamientos/";
                    break;
                case dietista:
                    webRedirect = "redirect:/dietista/platos";
                    break;
                default:
                    webRedirect = "loginTest";
            }

        }
        return webRedirect;
    }

    @PostMapping("/autentica")
    public String doLogin(@RequestParam("usuario_login") String username
            , @RequestParam("password_login") String password
            , HttpSession session
            , Model model) {
        UserDTO userDTO = userService.findUserByUsernameAndPassword(username, password);
        if (userDTO == null) {
            model.addAttribute("error", "Credenciales no encontradas");
            return "redirect:/";
        } else {
            session.setAttribute("user", userDTO);
            UserRolDTO userRolDTO = userDTO.getRol();
            session.setAttribute("rol", userRolDTO);

            String webRedirect = "loginTest";
            switch (userRolDTO.getRolUsuario()) {
                case admin:
                    webRedirect = "redirect:/admin/";
                    break;
                case cliente:

                    webRedirect = "redirect:/cliente/";
                    break;
                case bodybuilder:
                    webRedirect = "redirect:/entrenamientos/";
                    break;
                case crosstrainer:
                    webRedirect = "redirect:/entrenamientos/";
                    break;
                case dietista:
                    webRedirect = "redirect:/dietista/platos";
                    break;
                default:
                    webRedirect = "loginTest";
            }
            return webRedirect;
        }
    }

    @GetMapping("/cancelar")
    public String doCancelar(){
        return "redirect:/";
    }

    @GetMapping("/cerrarSesion")
    public String doCerrarSesion(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
