package es.uma.controller;

import es.uma.dao.*;
import es.uma.entity.Ejercicio;
import es.uma.entity.TipoEjercicio;
import es.uma.entity.UserRol;
import es.uma.ui.EjercicioUI;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comun")
public class ComunController extends BaseController{

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RutinaRepository rutinaRepository;
    @Autowired
    protected EjercicioRepository ejercicioRepository;
    @Autowired
    protected ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    protected TipoEjercicioRepository tipoEjercicioRepository;
    @Autowired
    protected RegistroRepository registroRepository;
    @Autowired
    protected UserRolRepository rolRepository;
    @Autowired
    protected AsignacionClienteEntrenadorRepository asignacionClienteEntrenadorRepository;
    @Autowired
    protected AsignacionClienteDietistaRepository asignacionClienteDietistaRepository;
    @Autowired
    protected PlatosRepository platosRepository;
    @Autowired
    protected CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;
    @Autowired
    protected TipoComidaRepository tipoComidaRepository;


    @GetMapping("/mostrarEjercicios")
    public String doEjercicios(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "admin/ejercicios";
            List<Ejercicio> ejercicios = this.ejercicioRepository.findAll();
            List<TipoEjercicio> tipos = this.tipoEjercicioRepository.findAll();
            model.addAttribute("ejercicios", ejercicios);
            model.addAttribute("tipos", tipos);
            model.addAttribute("rol", rol);

            model.addAttribute("ejercicio", new EjercicioUI());
        } else {
            dir = "redirect:/";
        }
        return dir;
    }



}
