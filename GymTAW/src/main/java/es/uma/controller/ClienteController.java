package es.uma.controller;

import es.uma.dao.*;
import es.uma.entity.*;
import es.uma.ui.FeedbackSerieForm;
import es.uma.ui.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController extends BaseController{

    @Autowired
    protected ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    protected FeedbackejercicioRepository feedbackejercicioRepository;
    @Autowired
    protected FeedbackejercicioserieRepository feedbackejercicioserieRepository;
    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;
    @Autowired
    protected DiaDietaRepository diaDietaRepository;
    @Autowired
    protected ComidaRepository comidaRepository;
    @Autowired
    protected CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;
    @Autowired
    protected PlatosRepository platosRepository;

    //NOTA: LOS EJERCICIOS DE BODY SIEMPRE TENDRÁN SERIES, COMO MÍNIMO UNA, PERO LOS EJERCICIOS DE CROSSFIT PUEDEN TENER O NO TENER SERIES ESTIPULADAS.
    //AUNQUE LA INTERPRETACIÓN DE NO TENER UNA SERIE SEA EQUIVALENTE A TENER UNA SERIE, HACERLO ASÍ LO HACE MÁS SIMPLE Y COHERENTE, YA QUE EN BODY
    //EL USUARIO PUEDE OPTAR POR HACER MAS SERIES DE LAS ESTIPULADAS PERO CUANDO EN CROSS TE MANDAN HACER POR EJEMPLO UNOS MINUTOS DE UN EJERCICIO,
    //SIMPLEMENTE DICES CUANTO HAS HECHO Y NO SI HAS HECHO UNA SERIE MAS DE ESOS MINUTOS (TIENE MÁS COHERENCIA)

    @GetMapping("/")
    public String doMostrarInicio(){
        return "/cliente/cliente_inicio";
    }

    @GetMapping("/mostrarEntrenamientos")
    public String doMostrarEntrenamiento(HttpSession session, Model model){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            User userEntity = (User) session.getAttribute("user");

            //Semana 1
            LocalDate fechaInicio = LocalDate.of(2000, 1, 1);

            //OBTENGO EL DIAENTRENAMIENTO
            DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(userEntity.getId(),fechaInicio);
            session.setAttribute("fechaSeleccionada",diaEntrenamiento.getFecha());

            //OBTENGO LOS EJERCICIOS DE LA RUTINA ASIGNADA ESE DIA
            List<ImplementacionEjercicioRutina> implementaciones = diaEntrenamiento.getRutina().getImplementacionesEjercicioRutina();

            model.addAttribute("implementaciones",implementaciones);
            model.addAttribute("diaEntrenamiento",diaEntrenamiento);

            dir = "/cliente/cliente_entrenamientos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/ejercicio")
    public String doMostrarEjercicio(@RequestParam("id")String id, @RequestParam(value = "set", required = false) String set, Model model, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            //OBTENEMOS LA IMPLEMENTACION
            ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(Integer.parseInt(id)).orElse(null);
            model.addAttribute("implementacion",implementacion);

            String strTo = "/cliente/cliente_ejerciciobody";

            //OBTENEMOS EL DIA SELECCIONADO Y EL FEEDBACK DE ESTE EJERCICIO Y AÑADIMOS AL MODELO ESTE ÚLTIMO
            User userEntity = (User) session.getAttribute("user");
            LocalDate dia = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(userEntity.getId(),dia);
            //CREAMOS EL FEEDBACK DEL EJERCICIO
            FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYDia(implementacion,diaEntrenamiento);
            if(feedbackEjercicio==null){
                feedbackEjercicio = new FeedbackEjercicio();
                feedbackEjercicio.setDiaEntrenamiento(diaEntrenamiento);
                feedbackEjercicio.setImplementacion(implementacion);
                feedbackEjercicio.setRealizado((byte) 0);
                feedbackejercicioRepository.save(feedbackEjercicio);
            }
            model.addAttribute("feedback",feedbackEjercicio);

            //MANTENEMOS EL OBJETO UI ACTUALIZADO
            FeedbackSerieForm feedbackSerieForm = new FeedbackSerieForm();
            feedbackSerieForm.setImplementacionId(Integer.parseInt(id));
            feedbackSerieForm.setFeedbackEjercicio(feedbackEjercicio);

            //COMPROBAMOS SI EL EJERCICIO TIENE SERIES ESTIPULADAS
            boolean tieneSeries = implementacion.getSets()!=null;

            //EL EJERCICIO TIENE SERIES ESTIPULADAS
            if(tieneSeries){
                //CONSIDERO QUE AL HABER SERIES ESTIPULADAS SIEMPRE HABRÁ COMO MÍNIMO UNA SERIE ESTIPULADA, POR TANTO EN CASO DE QUE EL USUARIO NO HAYA SELECCIONADO
                //SERIE TODAVÍA SE PONE LA SERIE SELECCIONADA A 1
                if(set == null) {
                    set = "1";
                }

                //OBTENGO EL FEEDBACK DE ESE EJERCICIO EN ESA SERIE CONCRETA Y LO AÑADO AL MODELO
                FeedbackEjercicioserie feedbackEjercicioSerie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(feedbackEjercicio,set);
                model.addAttribute("feedbackSerie",feedbackEjercicioSerie);

                //MANTENEMOS EL OBJETO UI ACTUALIZADO
                feedbackSerieForm.setSerieSeleccionada(set);

                //OBTENGO LOS DATOS ALMACENADOS EN EL FEEDBACK DE LA SERIE Y METO EN EL OBJETO UI LOS QUE PROCEDAN
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
            //EL EJERCICIO NO TIENE SERIES ESTIPULADAS
            }else{

                //OBTENGO LOS DATOS ALMACENADOS EN EL FEEDBACK DEL EJERCICIO Y METO EN EL OBJETO UI LOS QUE PROCEDAN
                if(feedbackEjercicio!=null){
                    String pesoRealizado = feedbackEjercicio.getSeguimientoPesoDone();
                    String tiempoRealizado = feedbackEjercicio.getSeguimientoTiempoDone();
                    String metrosRealizado = feedbackEjercicio.getSeguimientoMetrosDone();
                    String kilocaloriasRealizado = feedbackEjercicio.getSeguimientoKilocaloriasDone();

                    if(pesoRealizado!=null) feedbackSerieForm.setPesoRealizado(pesoRealizado);
                    if(tiempoRealizado!=null) feedbackSerieForm.setMinutosRealizados(Integer.parseInt(tiempoRealizado)/60);
                    if(tiempoRealizado!=null) feedbackSerieForm.setSegundosRealizados(Integer.parseInt(tiempoRealizado)%60);
                    if(metrosRealizado!=null) feedbackSerieForm.setMetrosRealizado(metrosRealizado);
                    if(kilocaloriasRealizado!=null) feedbackSerieForm.setKilocaloriasRealizado(kilocaloriasRealizado);
                }

            }

            //AÑADO AL MODELO EL OBJETO UI
            model.addAttribute("feedbackSerieForm",feedbackSerieForm);

            //EN CASO DE SER DE CROSSTRAINING TENDRA DIFERENTES FORMULARIOS
            if(implementacion.getEjercicio().getTipo().getId()!=1){
                strTo = tieneSeries ? "/cliente/cliente_ejerciciocrossSet" : "/cliente/cliente_ejerciciocross";
            }

            dir = strTo;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    @PostMapping("/filtrarFechaEntrenamiento")
    public String doFiltrarFechaEntrenamiento(@RequestParam("filtroDia") String filtroDia,
                                              Model model, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            String strTo = "/cliente/cliente_entrenamientos";

            //SI NO HAY FILTRO REDIRECCIONAR PARA MOSTRAR EL DIA DE HOY
            if(filtroDia.isEmpty()){
                strTo = "redirect:/cliente/";
            }else{
                //ACTUALIZO CON EL DIA DEL FILTRO
                User userEntity = (User) session.getAttribute("user");
                int dia = Integer.parseInt(filtroDia);

                //Semana 1
                LocalDate fechaInicio = LocalDate.of(2000, 1, dia);
                session.setAttribute("fechaSeleccionada",fechaInicio);

                //Obtengo las rutinas del dia seleccionado
                DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(userEntity.getId(),fechaInicio);
                List<ImplementacionEjercicioRutina> implementaciones;
                if(diaEntrenamiento != null){
                    //Obtengo la especifiacion de los ejercicios de las rutinas
                    implementaciones = diaEntrenamiento.getRutina().getImplementacionesEjercicioRutina();
                }else{
                    //No hay implementaciones para ese dia
                    implementaciones = new ArrayList<>();
                }
                model.addAttribute("implementaciones",implementaciones);
                model.addAttribute("diaEntrenamiento",diaEntrenamiento);
            }

            dir = strTo;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarFechaDieta")
    public String doFiltrarFechaDieta(@RequestParam(required = false, value = "filtroDia") String filtroDia,
                                      @RequestParam(required = false, value = "filtroSemana") String filtroSemana,
                                      Model model, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            String strTo = "/cliente/cliente_dietas";

            //SI NO HAY FILTRO REDIRECCIONAR PARA MOSTRAR EL DIA DE HOY
            if(filtroDia == null && filtroSemana == null){
                strTo = "redirect:/cliente/";
            }else{
                if(filtroDia == null) filtroDia = "1";
                if(filtroSemana == null) filtroSemana = "0";
                //ACTUALIZO CON EL DIA DEL FILTRO
                User userEntity = (User) session.getAttribute("user");
                int dia = Integer.parseInt(filtroDia);
                int semana = Integer.parseInt(filtroSemana);

                //Semana y dia seleccionado
                LocalDate fechaInicio = LocalDate.of(2000, 1, dia + (semana*7));
                session.setAttribute("fechaSeleccionada",fechaInicio);

                //Obtengo las rutinas del dia seleccionado
                DiaDieta diaDieta = diaDietaRepository.diaDietaConcretoCliente(userEntity,fechaInicio);
                List<Comida> comidas;
                if(diaDieta != null){
                    //OBTENGO LAS COMIDAS ASIGNADAS A ESE DIA
                    comidas = comidaRepository.findByDiaDieta(diaDieta);
                }else{
                    comidas = new ArrayList<>();
                }
                model.addAttribute("diaDieta",diaDieta);
                model.addAttribute("comidas",comidas);
            }
            dir = strTo;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //ESTE POST GUARDA EL FEEDBACK COMÚN A LOS EJERCICIOS BODY Y CROSS, QUE ES EL BOOLEAN REALIZADO Y LAS SERIES REALIZADAS
    //TENEMOS EN CUENTA TAMBIÉN CASO DE CROSS QUE NO TIENE SERIES.
    @PostMapping("/guardarFeedbackEjercicio")
    public String doGuardarFeedbackEjercicio(@RequestParam(required = false, value = "realizado") Byte realizado,
                                             @RequestParam(required = false, value = "seriesRealizadas")Integer seriesRealizadas,
                                             @RequestParam("implementacion") Integer implementacionId,
                                             @RequestParam("feedbackEjercicio") Integer feedbackEjercicioId,
                                             HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            //OBTENEMOS EL FEEDBACK DEL EJERCICIO
            FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.findById(feedbackEjercicioId).orElse(null);

            if (feedbackEjercicio!=null) {
                if(realizado == null) realizado = 0;
                //ACTUALIZAMOS EL CAMPO REALIZADO
                feedbackEjercicio.setRealizado(realizado);

                //EN CASO DE TENER SERIES
                if(seriesRealizadas!=null && seriesRealizadas>0){
                    //ESTABLECEMOS LAS SERIES REALIZADAS
                    feedbackEjercicio.setSeguimientoSetsDone("" + seriesRealizadas);

                    //POR SIMPLIFICAR, SI SE MODIFICA EL NUMERO DE SETS REALIZADAS EL CLIENTE DEBERÁ VOLVER A RELLENAR EL FEEDBACK DE ESTAS NUEVAS SERIES
                    List<FeedbackEjercicioserie> feedbackAnterior = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicio(feedbackEjercicio);

                    //SI HABIA FEEDBACK ANTERIOR LO BORRAMOS PARA DAR PASO AL NUEVO
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

                //GUARDAMOS LOS CAMBIOS REALIZADOS
                feedbackejercicioRepository.save(feedbackEjercicio);
            }

            dir = "redirect:/cliente/ejercicio?id=" + implementacionId;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //LOS EJERCICIOS DE BODY TENDRÁN SIEMPRE SERIES (MINIMO UNA), REPETICIONES Y PESO (KG).
    @PostMapping("/guardarFeedbackSerieBody")
    public String doGuardarFeedbackSerieBody(@ModelAttribute("feedbackSerieForm") FeedbackSerieForm feedbackSerieForm, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            //ACTUALIZAMOS EL FEEDBACK DEL EJERCICIO EN LA SERIE SELECCIONADA
            FeedbackEjercicioserie feedbackEjercicioserie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(
                    feedbackSerieForm.getFeedbackEjercicio(),feedbackSerieForm.getSerieSeleccionada());

            feedbackEjercicioserie.setPesoRealizado(feedbackSerieForm.getPesoRealizado());
            feedbackEjercicioserie.setRepeticionesRealizadas(feedbackSerieForm.getRepeticionesRealizadas());

            //GUARDAMOS LOS CAMBIOS EN EL FEEDBACK DE LA SERIE SELECCIONADA DEL EJERCICIO
            feedbackejercicioserieRepository.save(feedbackEjercicioserie);

            dir = "redirect:/cliente/ejercicio?id=" + feedbackSerieForm.getImplementacionId() + "&set=" + feedbackSerieForm.getSerieSeleccionada();
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //LOS EJERCICIOS DE BODY CON SERIE PUEDEN TENER O NO TENER CUALQUIERA DE ATRIBUTOS -REALIZADO
    @PostMapping("/guardarFeedbackSerieCross")
    public String doGuardarFeedbackSerieCross(@ModelAttribute("feedbackSerieForm") FeedbackSerieForm feedbackSerieForm, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            FeedbackEjercicioserie feedbackEjercicioserie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(
                    feedbackSerieForm.getFeedbackEjercicio(),feedbackSerieForm.getSerieSeleccionada());

            //OBTENEMOS LO QUE EL USUARIO HA PODIDO RELLENAR (CUANDO EL CAMPO NO PROCEDE SE LE DESACTIVA EL INPUT)
            String pesoRealizado = feedbackSerieForm.getPesoRealizado();
            String repeticionesRealizadas = feedbackSerieForm.getRepeticionesRealizadas();
            Integer minutosRealizados = feedbackSerieForm.getMinutosRealizados();
            Integer segundosRealizados = feedbackSerieForm.getSegundosRealizados();
            String kilocaloriasRealizadas = feedbackSerieForm.getKilocaloriasRealizado();
            String metrosRealizados = feedbackSerieForm.getMetrosRealizado();

            //EN FUNCION DE LO QUE HAYAMOS RECIBIDO ACTUALIZAMOS LA BBDD EN CONSONANCIA.
            if(pesoRealizado!=null) feedbackEjercicioserie.setPesoRealizado(pesoRealizado);
            if(repeticionesRealizadas!=null) feedbackEjercicioserie.setRepeticionesRealizadas(repeticionesRealizadas);

            if(!(minutosRealizados==null && segundosRealizados==null)){
                if(minutosRealizados==null) minutosRealizados = 0;
                if(segundosRealizados==null) segundosRealizados = 0;
                String segundosTotal = "" + (minutosRealizados * 60 + segundosRealizados);
                feedbackEjercicioserie.setTiempoRealizado(segundosTotal);
            }

            if(kilocaloriasRealizadas!=null)feedbackEjercicioserie.setKilocaloriasRealizado(kilocaloriasRealizadas);
            if(metrosRealizados!=null) feedbackEjercicioserie.setMetrosRealizado(metrosRealizados);

            //GUARDAMOS LOS CAMBIOS REALIZADOS EN EL FEEDBACK DE LA SERIE SELECCIONADA DEL EJERCICIO
            feedbackejercicioserieRepository.save(feedbackEjercicioserie);

            dir = "redirect:/cliente/ejercicio?id=" + feedbackSerieForm.getImplementacionId() + "&set=" + feedbackSerieForm.getSerieSeleccionada();
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //CASO EJERCICIO CROSSFIT SIN SERIE, SU SEGUIMIENTO SE GUARDA EN EL PROPIO FEEDBACK DEL EJERCICIO (SIN SER EL FEEDBACK DE LA SERIE)
    @PostMapping("/guardarFeedbackCross")
    public String doGuardarFeedbackCross(@ModelAttribute("feedbackSerieForm") FeedbackSerieForm feedbackSerieForm, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            ImplementacionEjercicioRutina implementacion = implementacionEjercicioRutinaRepository.findById(feedbackSerieForm.getImplementacionId()).orElse(null);

            User userEntity = (User) session.getAttribute("user");
            LocalDate dia = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(userEntity.getId(),dia);

            if (implementacion != null && dia != null) {
                //OBTENEMOS EL FEEDBACK DEL EJERCICIO
                FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYDia(implementacion,diaEntrenamiento);

                //OBTENEMOS LO QUE EL USUARIO HA PODIDO RELLENAR (CUANDO EL CAMPO NO PROCEDE SE LE DESACTIVA EL INPUT)
                String pesoRealizado = feedbackSerieForm.getPesoRealizado();
                Integer minutosRealizados = feedbackSerieForm.getMinutosRealizados();
                Integer segundosRealizados = feedbackSerieForm.getSegundosRealizados();
                String kilocaloriasRealizadas = feedbackSerieForm.getKilocaloriasRealizado();
                String metrosRealizados = feedbackSerieForm.getMetrosRealizado();

                //EN FUNCION DE LO QUE HAYAMOS RECIBIDO ACTUALIZAMOS LA BBDD EN CONSONANCIA.
                if(pesoRealizado!=null) feedbackEjercicio.setSeguimientoPesoDone(pesoRealizado);

                if(!(minutosRealizados==null && segundosRealizados==null)){
                    if(minutosRealizados==null) minutosRealizados = 0;
                    if(segundosRealizados==null) segundosRealizados = 0;
                    String segundosTotal = "" + (minutosRealizados * 60 + segundosRealizados);
                    feedbackEjercicio.setSeguimientoTiempoDone(segundosTotal);
                }

                if(kilocaloriasRealizadas!=null)feedbackEjercicio.setSeguimientoKilocaloriasDone(kilocaloriasRealizadas);
                if(metrosRealizados!=null) feedbackEjercicio.setSeguimientoMetrosDone(metrosRealizados);

                //GUARDAMOS LOS CAMBIOS REALIZADOS EN EL FEEDBACK DEL EJERCICIO
                feedbackejercicioRepository.save(feedbackEjercicio);
            }

            dir = "redirect:/cliente/ejercicio?id=" + feedbackSerieForm.getImplementacionId();
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/seleccionarSerie")
    public String doSeleccionarSerie(@RequestParam ("set") String set,
                                     @RequestParam("implementacion") Integer implementacionId,
                                     HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            dir = "redirect:/cliente/ejercicio?id=" + implementacionId + "&set=" + set;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("mostrarDietas")
    public String doMostrarDietas(Model model,HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            User userEntity = (User) session.getAttribute("user");

            //Semana 1
            LocalDate fechaInicio = LocalDate.of(2000, 1, 1);

            //OBTENGO EL DIAEDIETA
            DiaDieta diaDieta = diaDietaRepository.diaDietaConcretoCliente(userEntity,fechaInicio);
            session.setAttribute("fechaSeleccionada",diaDieta.getFecha());

            //OBTENGO LAS COMIDAS ASIGNADAS A ESE DIA
            List<Comida> comidas = comidaRepository.findByDiaDieta(diaDieta);

            model.addAttribute("comidas",comidas);
            model.addAttribute("diaDieta",diaDieta);

            dir = "/cliente/cliente_dietas";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("seleccionarComida")
    public String doSeleccionarComida(@RequestParam("id") String id, @RequestParam(required = false, value = "platoSeleccionado") String platoSeleccionado,
                                      @RequestParam(required = false,value = "cantidadSeleccionada") String cantidadSeleccionada,Model model, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            Comida comida = comidaRepository.findById(Integer.parseInt(id)).orElse(null);

            List<Plato> platos = cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida);
            Plato platoSeleccionadoEntity;
            if(platoSeleccionado == null){
                platoSeleccionadoEntity = platos.getFirst();
            }else{
                platoSeleccionadoEntity = platosRepository.findById(Integer.parseInt(platoSeleccionado)).orElse(null);
            }
            List<CantidadIngredientePlatoComida> cantidades = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(platoSeleccionadoEntity,comida);
            CantidadIngredientePlatoComida cantidadSeleccionadaEntity;
            if(cantidadSeleccionada == null){
                cantidadSeleccionadaEntity = cantidades.getFirst();
            }else{
                cantidadSeleccionadaEntity = cantidadIngredientePlatoComidaRepository.findById(Integer.parseInt(cantidadSeleccionada)).orElse(null);
            }

            model.addAttribute("comida", comida);
            model.addAttribute("platos",platos);
            model.addAttribute("cantidades",cantidades);
            model.addAttribute("platoSeleccionado",platoSeleccionadoEntity);
            model.addAttribute("cantidadSeleccionada",cantidadSeleccionadaEntity);

            dir = "cliente/cliente_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("seleccionarPlato")
    public String doSeleccionarPlato(@RequestParam("platoSeleccionado") String platoSeleccionado, @RequestParam("comidaId") Integer comidaId, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            dir = "redirect:/cliente/seleccionarComida?id=" + comidaId + "&platoSeleccionado=" + platoSeleccionado;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("seleccionarIngrediente")
    public String doSeleccionarIngrediente(@RequestParam("platoId") Integer platoId, @RequestParam("comidaId") Integer comidaId, @RequestParam("cantidadSeleccionada") Integer cantidadSeleccionada, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            dir = "redirect:/cliente/seleccionarComida?id=" + comidaId + "&platoSeleccionado=" + platoId + "&cantidadSeleccionada=" + cantidadSeleccionada;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("guardarCantidadConsumidaIngrediente")
    public String doGuardarCantidadConsumidaIngrediente(@RequestParam("platoId") Integer platoId, @RequestParam("comidaId") Integer comidaId, @RequestParam("cantidadID") Integer cantidadID,
                                                        @RequestParam("cantidadConsumida") Integer cantidadConsumida, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            CantidadIngredientePlatoComida cantidad = cantidadIngredientePlatoComidaRepository.findById(cantidadID).orElse(null);
            if(cantidad!=null) {
                cantidad.setCantidadConsumida(cantidadConsumida);
                cantidadIngredientePlatoComidaRepository.save(cantidad);
            }
            dir = "redirect:/cliente/seleccionarComida?id=" + comidaId + "&platoSeleccionado=" + platoId + "&cantidadSeleccionada=" + cantidadID;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("guardarFeedbackComida")
    public String doGuardarFeedbackComida(@RequestParam("comidaId") Integer comidaId, @RequestParam(required = false, value = "realizado") Byte realizado, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            Comida comida = comidaRepository.findById(comidaId).orElse(null);

            if(comida!=null){
                if(realizado==null) realizado = 0;
                comida.setRealizado(realizado);
                comidaRepository.save(comida);
            }

            dir = "redirect:/cliente/seleccionarComida?id=" + comida.getId();
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("guardarSeguimientoDieta")
    public String doGuardarSeguimientoDieta(@RequestParam("seguimientoDieta") String seguimientoDieta,HttpSession session, Model model){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            User userEntity = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaDieta diaDieta = diaDietaRepository.diaDietaConcretoCliente(userEntity,fecha);

            if(diaDieta!=null){
                diaDieta.setSeguimiento(seguimientoDieta);
                diaDietaRepository.save(diaDieta);

                //OBTENGO LAS COMIDAS ASIGNADAS A ESE DIA
                List<Comida> comidas = comidaRepository.findByDiaDieta(diaDieta);

                model.addAttribute("comidas",comidas);
                model.addAttribute("diaDieta",diaDieta);

            }
            dir = "/cliente/cliente_dietas";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("guardarSeguimientoEntrenamiento")
    public String doGuardarSeguimientoEntrenamiento(@RequestParam("seguimientoDieta") String seguimientoDieta,HttpSession session, Model model){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            User userEntity = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(userEntity.getId(),fecha);

            if(diaEntrenamiento!=null){
                diaEntrenamiento.setSeguimiento(seguimientoDieta);
                diaEntrenamientoRepository.save(diaEntrenamiento);

                //OBTENGO LOS EJERCICIOS DE LA RUTINA ASIGNADA ESE DIA
                List<ImplementacionEjercicioRutina> implementaciones = diaEntrenamiento.getRutina().getImplementacionesEjercicioRutina();

                model.addAttribute("implementaciones",implementaciones);
                model.addAttribute("diaEntrenamiento",diaEntrenamiento);
            }

            dir = "/cliente/cliente_entrenamientos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarDesempeño")
    public String doMostrarDesempelo(){
        return "";
    }

    @GetMapping("/irInicio")
    public String doIrInicio(HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            dir = "redirect:/cliente/";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/irAPerfil")
    public String doIrAPerfil(Model model,HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)) {
            User userEntity = (User) session.getAttribute("user");
            Usuario usuario = new Usuario();
            setUser(usuario,userEntity);

            model.addAttribute("usuario",usuario);
            model.addAttribute("rolid",rol.getId());

            dir ="/cliente/vistaPerfilCliente";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    private void setUser(Usuario usuario,User user){
        usuario.setId(user.getId());
        usuario.setUsername(user.getUsername());
        usuario.setNombre(user.getNombre());
        usuario.setApellidos(user.getApellidos());
        usuario.setTelefono(user.getTelefono());
        usuario.setPeso(user.getPeso());
        usuario.setAltura(user.getAltura());
        usuario.setFechaNacimiento(String.valueOf(user.getFechaNacimiento()));
        usuario.setDescripcionPersonal(user.getDescripcionPersonal());
    }

}