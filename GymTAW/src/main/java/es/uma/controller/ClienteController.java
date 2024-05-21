package es.uma.controller;

import es.uma.dao.*;
import es.uma.entity.*;
import es.uma.ui.FeedbackSerieForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    protected FeedbackejercicioserieRepository feedbackejercicioserieRepository;
    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;

    @GetMapping("/")
    public String doMostrarInicio(){
        return "/cliente/cliente_inicio";
    }

    @GetMapping("/mostrarEntrenamientos")
    public String doMostrarEntrenamiento(HttpSession session, Model model){
        User userEntity = (User) session.getAttribute("user");

        //Semana 1
        LocalDate fechaInicio = LocalDate.of(2000, 1, 1);

        //OBTENGO EL DIAENTRENAMIENTO
        DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(userEntity.getId(),fechaInicio);
        session.setAttribute("fechaSeleccionada",diaEntrenamiento);

        List<ImplementacionEjercicioRutina> implementaciones = diaEntrenamiento.getRutina().getImplementacionesEjercicioRutina();

        model.addAttribute("implementaciones",implementaciones);

        return "/cliente/cliente_entrenamientos";
    }

    @GetMapping("/ejercicio")
    public String doMostrarEjercicio(@RequestParam("id")String id, @RequestParam(value = "set", required = false) String set, Model model, HttpSession session){
        ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(Integer.parseInt(id)).orElse(null);
        model.addAttribute("implementacion",implementacion);

        String strTo = "/cliente/cliente_ejerciciobody";

        DiaEntrenamiento dia = (DiaEntrenamiento) session.getAttribute("fechaSeleccionada");
        FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYDia(implementacion,dia);
        model.addAttribute("feedback",feedbackEjercicio);

        //MANTENEMOS EL OBJETO UI ACTUALIZADO
        FeedbackSerieForm feedbackSerieForm = new FeedbackSerieForm();
        feedbackSerieForm.setImplementacionId(Integer.parseInt(id));
        feedbackSerieForm.setFeedbackEjercicio(feedbackEjercicio);

        boolean tieneSeries = implementacion.getSets()!=null;

        //EL EJERCICIO TIENE SETS ESTIPULADOS
        if(tieneSeries){
            //CONSIDERO QUE SIEMPRE HABRÁ UNA SERIE EN EL EJERCICIO PARA PONERLO POR DEFECTO EN CASO DE NO SELECCIONAR SERIE EL USUARIO
            if(set == null) {
                set = "1";
            }

            FeedbackEjercicioserie feedbackEjercicioSerie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(feedbackEjercicio,set);
            model.addAttribute("feedbackSerie",feedbackEjercicioSerie);

            feedbackSerieForm.setSerieSeleccionada(set);

            if(feedbackEjercicioSerie!=null){
                String pesoRealizado = feedbackEjercicioSerie.getPesoRealizado();
                String repeticionesRealizadas = feedbackEjercicioSerie.getRepeticionesRealizadas();
                String tiempoRealizado = feedbackEjercicioSerie.getTiempoRealizado();
                String metrosRealizado = feedbackEjercicioSerie.getMetrosRealizado();
                String kilocaloriasRealizado = feedbackEjercicioSerie.getKilocaloriasRealizado();

                if(pesoRealizado!=null) feedbackSerieForm.setPesoRealizado(pesoRealizado);
                if(repeticionesRealizadas!=null) feedbackSerieForm.setRepeticionesRealizadas(repeticionesRealizadas);
                if(tiempoRealizado!=null) feedbackSerieForm.setMinutosRealizados(Integer.parseInt(tiempoRealizado)/60);
                if(tiempoRealizado!=null) feedbackSerieForm.setSegundosRealizados(Integer.parseInt(tiempoRealizado)%60);
                if(metrosRealizado!=null) feedbackSerieForm.setMetrosRealizado(metrosRealizado);
                if(kilocaloriasRealizado!=null) feedbackSerieForm.setKilocaloriasRealizado(kilocaloriasRealizado);
            }

        }else{
           
        }

        model.addAttribute("feedbackSerieForm",feedbackSerieForm);

        //CASO EJERCICIO CROSSTRAINING
        if(implementacion.getEjercicio().getTipo().getId()!=1){
            strTo = tieneSeries ? "/cliente/cliente_ejerciciocrossSet" : "/cliente/cliente_ejerciciocross";
        }

        return strTo;
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
            DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(userEntity.getId(),fechaInicio);
            session.setAttribute("fechaSeleccionada",diaEntrenamiento.getId());

            //Obtengo la especifiacion de los ejercicios de las rutinas
            List<ImplementacionEjercicioRutina> implementaciones = diaEntrenamiento.getRutina().getImplementacionesEjercicioRutina();

            //Añado las especificaciones al modelo
            model.addAttribute("implementaciones",implementaciones);
        }
        return strTo;
    }


    @PostMapping("/guardarFeedbackEjercicio")
    public String doGuardarFeedbackEjercicio(@RequestParam("realizado") Byte realizado,
                                             @RequestParam(required = false, value = "seriesRealizadas")Integer seriesRealizadas,
                                             @RequestParam("implementacion") Integer implementacionId,
                                             @RequestParam("feedbackEjercicio") Integer feedbackEjercicioId){
        ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(implementacionId).orElse(null);
        FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.findById(feedbackEjercicioId).orElse(null);

        if (implementacion != null && feedbackEjercicio!=null) {
            feedbackEjercicio.setRealizado(realizado);

            if(seriesRealizadas!=null){
                feedbackEjercicio.setSeguimientoSetsDone("" + seriesRealizadas);

                //POR SIMPLIFICAR, SI SE MODIFICA EL NUMERO DE SETS REALIZADAS EL CLIENTE DEBERÁ VOLVER A RELLENAR EL FEEDBACK DE ESTAS NUEVAS SERIES
                List<FeedbackEjercicioserie> feedbackAnterior = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicio(feedbackEjercicio);

                //SI HABIA FEEDBACK LO BORRAMOS
                if(feedbackAnterior!=null){
                    for(FeedbackEjercicioserie f : feedbackAnterior){
                        feedbackejercicioserieRepository.delete(f);
                    }
                }

                //PREMARAMOS EL FEEBACK DE LAS NUEVAS SERIES REALIZADAS PARA QUE POSTERIORMENTE EL CLIENTE LAS RELLENE
                for(int i = 1; i<= seriesRealizadas; i++){
                    FeedbackEjercicioserie feedbackEjercicioSerie = new FeedbackEjercicioserie();
                    feedbackEjercicioSerie.setSerie("" + i);
                    feedbackEjercicioSerie.setFeedbackEjercicio(feedbackEjercicio);

                    feedbackejercicioserieRepository.save(feedbackEjercicioSerie);
                }
            }

            feedbackejercicioRepository.save(feedbackEjercicio);
        }

        return "redirect:/cliente/ejercicio?id=" + implementacionId;
    }


    @PostMapping("/guardarFeedbackSerieBody")
    public String doGuardarFeedbackSerieBody(@ModelAttribute("feedbackSerieForm") FeedbackSerieForm feedbackSerieForm){
        ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(feedbackSerieForm.getImplementacionId()).orElse(null);

        if (implementacion != null) {
            FeedbackEjercicioserie feedbackEjercicioserie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(
                    feedbackSerieForm.getFeedbackEjercicio(),feedbackSerieForm.getSerieSeleccionada());

            feedbackEjercicioserie.setPesoRealizado(feedbackSerieForm.getPesoRealizado());
            feedbackEjercicioserie.setRepeticionesRealizadas(feedbackSerieForm.getRepeticionesRealizadas());

            feedbackejercicioserieRepository.save(feedbackEjercicioserie);
        }

        return "redirect:/cliente/ejercicio?id=" + feedbackSerieForm.getImplementacionId() + "&set=" + feedbackSerieForm.getSerieSeleccionada();
    }

    @PostMapping("/guardarFeedbackSerieCross")
    public String doGuardarFeedbackSerieCross(@ModelAttribute("feedbackSerieForm") FeedbackSerieForm feedbackSerieForm){
        ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(feedbackSerieForm.getImplementacionId()).orElse(null);

        if (implementacion != null) {
            FeedbackEjercicioserie feedbackEjercicioserie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(
                    feedbackSerieForm.getFeedbackEjercicio(),feedbackSerieForm.getSerieSeleccionada());

            feedbackEjercicioserie.setPesoRealizado(feedbackSerieForm.getPesoRealizado());
            feedbackEjercicioserie.setRepeticionesRealizadas(feedbackSerieForm.getRepeticionesRealizadas());

            String segundosTotal = "" + (feedbackSerieForm.getMinutosRealizados() * 60 + feedbackSerieForm.getSegundosRealizados());
            feedbackEjercicioserie.setTiempoRealizado(segundosTotal);

            feedbackEjercicioserie.setKilocaloriasRealizado(feedbackSerieForm.getKilocaloriasRealizado());
            feedbackEjercicioserie.setMetrosRealizado(feedbackSerieForm.getMetrosRealizado());

            feedbackejercicioserieRepository.save(feedbackEjercicioserie);
        }

        return "redirect:/cliente/ejercicio?id=" + feedbackSerieForm.getImplementacionId() + "&set=" + feedbackSerieForm.getSerieSeleccionada();
    }

    @PostMapping("/guardarFeedbackCross")
    public String doGuardarFeedbackCross(@ModelAttribute("feedbackSerieForm") FeedbackSerieForm feedbackSerieForm){
        ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(feedbackSerieForm.getImplementacionId()).orElse(null);

        if (implementacion != null) {
            FeedbackEjercicioserie feedbackEjercicioserie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(
                    feedbackSerieForm.getFeedbackEjercicio(),feedbackSerieForm.getSerieSeleccionada());

            feedbackEjercicioserie.setPesoRealizado(feedbackSerieForm.getPesoRealizado());
            feedbackEjercicioserie.setRepeticionesRealizadas(feedbackSerieForm.getRepeticionesRealizadas());

            String segundosTotal = "" + (feedbackSerieForm.getMinutosRealizados() * 60 + feedbackSerieForm.getSegundosRealizados());
            feedbackEjercicioserie.setTiempoRealizado(segundosTotal);

            feedbackEjercicioserie.setKilocaloriasRealizado(feedbackSerieForm.getKilocaloriasRealizado());
            feedbackEjercicioserie.setMetrosRealizado(feedbackSerieForm.getMetrosRealizado());

            feedbackejercicioserieRepository.save(feedbackEjercicioserie);
        }

        return "redirect:/cliente/ejercicio?id=" + feedbackSerieForm.getImplementacionId() + "&set=" + feedbackSerieForm.getSerieSeleccionada();
    }

    @PostMapping("/seleccionarSerie")
    public String doSeleccionarSerie(@RequestParam ("set") String set,
                                     @RequestParam("implementacion") Integer implementacionId){

        return "redirect:/cliente/ejercicio?id=" + implementacionId + "&set=" + set;
    }

    @GetMapping("/irInicio")
    public String doIrInicio(){
        return "redirect:/cliente/";
    }

}
