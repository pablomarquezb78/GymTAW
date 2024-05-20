package es.uma.controller;

import es.uma.dao.EjercicioRepository;
import es.uma.dao.FeedbackejercicioRepository;
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
    @Autowired
    protected FeedbackejercicioRepository feedbackejercicioRepository;

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
    public String doMostrarEjercicio(@RequestParam("id")String id, @RequestParam(value = "set", required = false) String set, Model model){
        ImplementacionEjercicioRutina i = implementacionEjercicioRutinaRepository.findById(Integer.parseInt(id)).orElse(null);

        //CONSIDERO QUE SIEMPRE HABRÁ UNA SERIE EN EL EJERCICIO
        if(set == null) {
            set = "1";
        }

        FeedbackEjercicio feedback = feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYSet(Integer.parseInt(id),set);
        model.addAttribute("feedback",feedback);

        model.addAttribute("implementacion",i);

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

    @PostMapping("/guardarFeedbackEjercicio")
    public String doGuardarFeedbackEjercicio(@RequestParam("realizado") Byte realizado,
                                             @RequestParam("seriesRealizadas")Integer seriesRealizadas,
                                             @RequestParam("implementacion") Integer implementacionId){
        ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(implementacionId).orElse(null);

        if (implementacion != null) {
            implementacion.setRealizado(realizado);
            implementacion.setSeguimientoSetsDone("" + seriesRealizadas);

            //POR SIMPLIFICAR, SI SE MODIFICA EL NUMERO DE SETS REALIZADAS EL CLIENTE DEBERÁ VOLVER A RELLENAR EL FEEDBACK DE ESTAS NUEVAS SERIES
            List<FeedbackEjercicio> feedbackAnterior = feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacion(implementacionId);

            //SI HABIA FEEDBACK LO BORRAMOS
            if(feedbackAnterior!=null){
                for(FeedbackEjercicio f : feedbackAnterior){
                    feedbackejercicioRepository.delete(f);
                }
            }

            //PREMARAMOS EL FEEBACK DE LAS NUEVAS SERIES REALIZADAS PARA QUE POSTERIORMENTE EL CLIENTE LAS RELLENE
            for(int i = 1; i<= seriesRealizadas; i++){
                FeedbackEjercicio feedbackEjercicio = new FeedbackEjercicio();
                feedbackEjercicio.setSerie("" + i);
                feedbackEjercicio.setImplementacionEjercicioRutina(implementacion);

                feedbackejercicioRepository.save(feedbackEjercicio);
            }

            implementacionEjercicioRutinaRepository.save(implementacion);
        }

        return "redirect:/cliente/ejercicio?id=" + implementacionId;
    }

    @PostMapping("/guardarFeedbackSerie")
    public String doGuardarFeedbackSerie(@RequestParam("repeticionesRealizadas") String repeticionesRealizadas,
                                             @RequestParam("pesoRealizado")String pesoRealizado,
                                             @RequestParam("implementacion") Integer implementacionId,
                                             @RequestParam("serieSeleccionada") String serieSeleccionada){
        ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(implementacionId).orElse(null);

        if (implementacion != null) {
            FeedbackEjercicio feedback = feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYSet(implementacionId,serieSeleccionada);

            feedback.setPesoRealizado(pesoRealizado);
            feedback.setRepeticionesRealizadas(repeticionesRealizadas);

            feedbackejercicioRepository.save(feedback);
        }

        return "redirect:/cliente/ejercicio?id=" + implementacionId + "&set=" + serieSeleccionada;
    }

    @PostMapping("/seleccionarSerie")
    public String doSeleccionarSerie(@RequestParam ("set") String set,
                                     @RequestParam("implementacion") Integer implementacionId,Model model){

        return "redirect:/cliente/ejercicio?id=" + implementacionId + "&set=" + set;
    }

    @GetMapping("/irInicio")
    public String doIrInicio(){
        return "redirect:/cliente/";
    }

}
