package es.uma.controller;

import es.uma.dao.*;
import es.uma.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RutinaRepository rutinaRepository;

    @Autowired
    protected ImplementacionEjercicioRutinaRepository ejercicioRutinaRepository;

    @Autowired
    protected RegistroRepository registroRepository;

    @Autowired
    protected UserRolRepository rolRepository;

    @Autowired
    protected PlatosRepository platosRepository;

    @Autowired
    protected IngredienteRepository ingredienteRepository;

    @Autowired
    protected CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;

    @GetMapping("/")
    public String doWelcome(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("clientes", this.userRepository.listarClientes().size());
            model.addAttribute("entrenadores", this.userRepository.listarEntrenadores().size());
            model.addAttribute("dietistas", this.userRepository.listarDietistas().size());
            model.addAttribute("ejercicios", this.ejercicioRutinaRepository.findAll().size());
            model.addAttribute("platos", this.platosRepository.findAll().size());
            dir = "admin/inicioAdmin";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/autenticarUsuarios")
    public String doAutenticarUsuarios(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("peticiones", this.registroRepository.findAll());
            dir = "admin/registro";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/autenticar")
    public String doAutenticar(@RequestParam("id") Integer id, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            User newUser = new User();
            Registro registro = this.registroRepository.getById(id);
            UserRol newRol = this.rolRepository.getById(registro.getRol());

            newUser.setUsername(registro.getUsername());
            newUser.setNombre(registro.getNombre());
            newUser.setPassword(registro.getPassword());
            newUser.setRol(newRol);
            newUser.setFechaNacimiento(registro.getFechaNacimiento());
            newUser.setTelefono(registro.getTelefono());

            this.userRepository.save(newUser);
            this.registroRepository.deleteById(id);
            dir = "redirect:/admin/autenticarUsuarios";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarPeticion")
    public String doBorrarPeticion(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/autenticarUsuarios";
            this.registroRepository.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarUsuarios")
    public String doUsuarios(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("usuarios", this.userRepository.findAll());
            dir = "admin/usuarios";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarUsuario")
    public String doBorrarUsuario(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarUsuarios";
            this.userRepository.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/asignarCliente")
    public String doAsignar(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/asignar";
            List<User> clientes = this.userRepository.listarClientes();
            model.addAttribute("clientes", clientes);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarEjercicios")
    public String doEjercicios(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/ejercicios";
            List<ImplementacionEjercicioRutina> ejercicios = this.ejercicioRutinaRepository.findAll();
            model.addAttribute("ejercicios", ejercicios);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarEjercicio")
    public String doBorrarEjercicio(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarEjercicios";
            this.ejercicioRutinaRepository.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarPlatos")
    public String doPlatos(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/platos";
            List<Plato> platos = this.platosRepository.findAll();
            List<Ingrediente> ingredientes = this.ingredienteRepository.findAll();
            List<CantidadIngredientePlatoComida> cantidad = this.cantidadIngredientePlatoComidaRepository.findAll();
            model.addAttribute("platos", platos);
            model.addAttribute("ingredientes", ingredientes);
            model.addAttribute("cantidad", cantidad);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarPlato")
    public String doBorrarPlato(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarPlatos";
            this.platosRepository.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }
}