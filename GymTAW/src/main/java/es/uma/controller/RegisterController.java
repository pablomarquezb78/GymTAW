package es.uma.controller;

import es.uma.dao.RegistroRepository;
import es.uma.dao.UserRepository;
import es.uma.entity.Registro;
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
    protected UserRepository userRepository;

    @Autowired
    protected RegistroRepository registroRepository;

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
        Registro peticion = new Registro();
        peticion.setUsername(username);
        peticion.setPassword(password);
        peticion.setNombre(nombre);
        peticion.setApellidos(apellidos);

        LocalDate fecha = LocalDate.parse(nacimiento);

        peticion.setFechaNacimiento(fecha);
        peticion.setTelefono(telefono);
        peticion.setRol(rol);

        this.registroRepository.save(peticion);

        return "redirect:/";
    }
}
