package es.uma.controller;

import es.uma.dao.RegistroRepository;
import es.uma.dao.UserRepository;
import es.uma.entity.Registro;
import es.uma.service.RegistroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegisterController extends BaseController{


    @Autowired
    protected RegistroService registroService;

    @GetMapping("/registrar")
    public String doRegistro(){
        return "registrar";
    }

    @PostMapping("/peticion")
    public String doPeticion(@RequestParam("usuario") String username, @RequestParam("password") String password
            , @RequestParam("nombre") String nombre
            , @RequestParam("apellidos") String apellidos
            , @RequestParam("fecha_nacimiento") String nacimiento
            , @RequestParam("telefono") Integer telefono
            , @RequestParam("rol") Integer rol
            , HttpSession session) {

        if(username == null || password == null || nombre == null || apellidos == null) {
            return "redirect:/";
        }
        registroService.save(username, password, nombre, apellidos, nacimiento, telefono, rol);

        return "redirect:/";
    }
}
