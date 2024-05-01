package es.uma.controller;

import es.uma.dao.PlatosRepository;
import es.uma.dao.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.uma.entity.User;
import es.uma.entity.UserRol;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends  BaseController {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PlatosRepository platosRepository;

    final String admin = "admin";
    final String cliente = "cliente";
    final String bodybuilder = "bodybuilder";
    final String crosstrainer = "crosstrainer";
    final String dietista = "dietista";

    @GetMapping("/")
    public String doLoad (Model model,HttpSession session) {
        String webRedirect= "login";
        if (estaAutenticado(session)) {
            UserRol userRolEntity =(UserRol) session.getAttribute("rol");
            User userEntity = (User) session.getAttribute("user");
            switch(userRolEntity.getRolUsuario())
            {
                case admin:

                    break;
                case cliente:

                    break;
                case bodybuilder:

                    break;
                case crosstrainer:
                    webRedirect = "redirect:/entrenamientos/";
                    break;
                case dietista:
                    model.addAttribute("panel", "main");
                    model.addAttribute("listaPlatos", platosRepository.getPlatosFromDietista(userEntity));
                    webRedirect = "redirect:/mostrarPlato";
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
        User userEntity = this.userRepository.findByUsernamePassword(username, password);
        if (userEntity == null) {
            model.addAttribute("error", "Credenciales no encontradas");
            return "redirect:/";
        }
        else
        {
            session.setAttribute("user", userEntity);
            UserRol userRolEntity = userEntity.getRol();
            session.setAttribute("rol", userRolEntity);

            String webRedirect = "loginTest";
            switch(userRolEntity.getRolUsuario())
            {
                case admin:

                break;
                case cliente:

                    webRedirect = "redirect:/cliente/";
                break;
                case bodybuilder:
                case crosstrainer:
                    webRedirect = "redirect:/trainer/";
                break;
                case dietista:
                    model.addAttribute("panel", "main");
                    model.addAttribute("listaPlatos", platosRepository.getPlatosFromDietista(userEntity));
                    webRedirect = "redirect:/dietista/mostrarPlato";
                break;
                default:
                    webRedirect = "loginTest";
            }
            return webRedirect;
        }
    }
}
