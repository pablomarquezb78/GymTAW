package es.uma.controller;

import es.uma.dao.EjercicioRepository;
import es.uma.dao.ImplementacionEjercicioRutinaRepository;
import es.uma.dao.RutinaRepository;
import es.uma.entity.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    protected RutinaRepository rutinaRepository;
    @Autowired
    protected ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    protected EjercicioRepository ejercicioRepository;

    @GetMapping("/")
    public String doMostrarInicio(){
        return "/cliente/cliente_inicio";
    }

    @GetMapping("/mostrarEntrenamientos")
    public String doMostrarEntrenamiento(HttpSession session, Model model){
        User userEntity = (User) session.getAttribute("user");

        //Semana 1
        LocalDate fechaInicio = LocalDate.of(2000, 1, 1);

        //Obtengo las rutinas del primer dia
        Rutina rutina = rutinaRepository.encontrarRutinasPorClienteYFecha(userEntity,fechaInicio);

        //Obtengo la especifiacion de los ejercicios de las rutinas
        List<ImplementacionEjercicioRutina> implementaciones = implementacionEjercicioRutinaRepository.
                encontrarImplementacionesPorRutinas(rutina);

        //Añado las especificaciones al modelo
        model.addAttribute("implementaciones", implementaciones);

        return "/cliente/cliente_entrenamientos";
    }

    @GetMapping("/ejercicio")
    public String doMostrarEjercicio(@RequestParam("id")String id, Model model){
        Ejercicio e = ejercicioRepository.findById(Integer.parseInt(id)).orElse(null);

        model.addAttribute("descripcion", e.getDescripcion());
        model.addAttribute("enlaceVideo",e.getEnlaceVideo());
        model.addAttribute("tipoEjercicio",e.getTipo().getTipoDeEjercicio());

        return "/cliente/cliente_ejercicio";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@RequestParam("filtroDia") String filtroDia,
                            Model model, HttpSession session){

        String strTo = "/cliente/cliente_entrenamientos";

        if(filtroDia.isEmpty()){
            strTo = "redirect:/cliente/";
        }else{
            User userEntity = (User) session.getAttribute("user");
            int dia = Integer.parseInt(filtroDia);

            //Semana 1
            LocalDate fechaInicio = LocalDate.of(2000, 1, dia);

            //Obtengo las rutinas del dia seleccionado
            Rutina rutina = rutinaRepository.encontrarRutinasPorClienteYFecha(userEntity,fechaInicio);

            //Obtengo la especifiacion de los ejercicios de las rutinas
            List<ImplementacionEjercicioRutina> implementaciones = implementacionEjercicioRutinaRepository.
                    encontrarImplementacionesPorRutinas(rutina);

            //Añado las especificaciones al modelo
            model.addAttribute("implementaciones", implementaciones);

        }
        return strTo;
    }

    @GetMapping("/irInicio")
    public String doIrInicio(){
        return "redirect:/cliente/";
    }

}
