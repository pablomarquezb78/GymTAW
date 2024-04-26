package es.uma.controller;

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
public class LoginController {

    @Autowired
    protected UserRepository userRepository;

    @GetMapping("/")
    public String doLoad (Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("usuario_login") String username
                        , @RequestParam("password_login") String password
                        , HttpSession session
                        , Model model) {
        User userEntity = this.userRepository.findByUsernamePassword(username, password);
        if (userEntity == null) {
            model.addAttribute("error", "Credenciales no encontradas");
            return "redirect:/login";
        }
        else
        {
            session.setAttribute("user", userEntity);
            UserRol userRolEntity = userEntity.getRol();
            session.setAttribute("rol", userRolEntity);
            return "loginTest";
        }
    }

}
