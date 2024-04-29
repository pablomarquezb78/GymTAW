package es.uma.controller;

import es.uma.dao.ImplementacionEjercicioRutinaRepository;
import es.uma.dao.RutinaRepository;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import es.uma.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    protected RutinaRepository rutinaRepository;
    @Autowired
    protected ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;

    @GetMapping("/")
    public String doMostrarInicio(){
        return "/cliente/cliente_inicio";
    }

    @GetMapping("/mostrarEntrenamientos")
    public String doMostrarEntrenamiento(HttpSession session, Model model){
        User userEntity = (User) session.getAttribute("user");

        //Semana 1
        LocalDate fechaInicio = LocalDate.of(2000, 1, 1);
        LocalDate fechaFin = LocalDate.of(2000, 1, 7);

        //Obtengo las rutinas de esta semana
        List<Rutina> rutinas = rutinaRepository.encontrarRutinasPorClienteYFechas(userEntity,fechaInicio,fechaFin);
        Rutina r = rutinas.getFirst();

        //Obtengo la especifiacion de los ejercicios de las rutinas
        List<ImplementacionEjercicioRutina> implementaciones = implementacionEjercicioRutinaRepository.
                encontrarImplementacionesPorRutinas(r);

        //Añado las especificaciones al modelo
        model.addAttribute("implementaciones", implementaciones);

        return "/cliente/cliente_entrenamientos";
    }

    @GetMapping("/irInicio")
    public String doIrInicio(){
        return "redirect:/cliente/";
    }

}
