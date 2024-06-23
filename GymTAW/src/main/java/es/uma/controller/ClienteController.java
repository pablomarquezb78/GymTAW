package es.uma.controller;

import es.uma.dto.*;
import es.uma.service.*;
import es.uma.ui.FeedbackSerieForm;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//@author: Pablo Márquez Benítez

@Controller
@RequestMapping("/cliente")
public class ClienteController extends BaseController{

    @Autowired
    private DiaEntrenamientoService diaEntrenamientoService;
    @Autowired
    private DiaDietaService diaDietaService;
    @Autowired
    private ImplementacionEjercicioRutinaService implementacionEjercicioRutinaService;
    @Autowired
    private FeedbackEjercicioService feedbackEjercicioService;
    @Autowired
    private FeedbackEjercicioSerieService feedbackEjercicioSerieService;
    @Autowired
    private ComidaService comidaService;
    @Autowired
    private CantidadIngredientePlatoComidaService cantidadIngredientePlatoComidaService;
    @Autowired
    private PlatoService platoService;

    //NOTA: LOS EJERCICIOS DE BODY SIEMPRE TENDRÁN SERIES, COMO MÍNIMO UNA, PERO LOS EJERCICIOS DE CROSSFIT PUEDEN TENER O NO TENER SERIES ESTIPULADAS.
    //AUNQUE LA INTERPRETACIÓN DE NO TENER UNA SERIE SEA EQUIVALENTE A TENER UNA SERIE, HACERLO ASÍ LO HACE MÁS SIMPLE Y COHERENTE, YA QUE EN BODY
    //EL USUARIO PUEDE OPTAR POR HACER MAS SERIES DE LAS ESTIPULADAS PERO CUANDO EN CROSS TE MANDAN HACER POR EJEMPLO UNOS MINUTOS DE UN EJERCICIO,
    //SIMPLEMENTE DICES CUANTO HAS HECHO Y NO SI HAS HECHO UNA SERIE MAS DE ESOS MINUTOS (TIENE MÁS COHERENCIA)

    @GetMapping("/")
    public String doMostrarInicio(HttpSession session, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){

            //Semana 1 dia 1
            LocalDate fechaInicio = LocalDate.of(2000, 1, 1);
            DiaEntrenamientoDTO diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(user.getId(),fechaInicio);
            DiaDietaDTO diaDieta = diaDietaService.getDiaDietaDeClienteFecha(user.getId(),fechaInicio);

            model.addAttribute("nombreUsuario", user.getNombre() + " " + user.getApellidos());
            model.addAttribute("diaDieta",diaDieta);
            model.addAttribute("diaEntrenamiento",diaEntrenamiento);
            model.addAttribute("fechaInicio",fechaInicio);

            dir = "/cliente/cliente_inicio";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarEntrenamientos")
    public String doMostrarEntrenamiento(HttpSession session, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            //SEMANA 1 DIA 1
            LocalDate fechaInicio = LocalDate.of(2000, 1, 1);

            //OBTENGO EL DIAENTRENAMIENTO
            DiaEntrenamientoDTO diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(user.getId(),fechaInicio);
            List<ImplementacionEjercicioRutinaDTO> implementaciones;
            //SI EL DIAENTRENAMIENTO EXISTE PONGO EN LA SESION LA FECHA SELECCIONADA Y OBTENGO LOS EJERCICIOS DE LA RUTINA ASIGNADA A ESE DIA
            if(diaEntrenamiento!=null){
                session.setAttribute("fechaSeleccionada",diaEntrenamiento.getFecha());
                implementaciones = implementacionEjercicioRutinaService.getImplementacionByRutina(diaEntrenamiento.getRutina().getId());
            //SI EL DIAENTRENAMIENTO NO EXISTE LAS IMPLEMENTACIONES SERÁN UNA LISTA VACÍA Y NO SE MOSTRARÁ ENTRENAMIENTO
            }else{
                implementaciones = new ArrayList<>();
            }

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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            //OBTENEMOS LA IMPLEMENTACION
            ImplementacionEjercicioRutinaDTO implementacion = implementacionEjercicioRutinaService.getImplementacionPorId(Integer.parseInt(id));
            model.addAttribute("implementacion",implementacion);

            String strTo = "/cliente/cliente_ejerciciobody";

            //OBTENEMOS EL DIA SELECCIONADO Y EL FEEDBACK DE ESTE EJERCICIO Y AÑADIMOS AL MODELO ESTE ÚLTIMO
            LocalDate dia = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaEntrenamientoDTO diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(user.getId(),dia);
            //CREAMOS EL FEEDBACK DEL EJERCICIO
            FeedbackEjercicioDTO feedbackEjercicio = feedbackEjercicioService.getFeedbackEjercicioPorImplementacionYDia(implementacion,diaEntrenamiento);
            if(feedbackEjercicio==null){
                feedbackEjercicio = feedbackEjercicioService.createFeedbackEjercicio(diaEntrenamiento.getId(),implementacion.getId());
            }
            model.addAttribute("feedback",feedbackEjercicio);

            //MANTENEMOS EL OBJETO UI ACTUALIZADO
            FeedbackSerieForm feedbackSerieForm = new FeedbackSerieForm();
            feedbackSerieForm.setImplementacionId(Integer.parseInt(id));
            feedbackSerieForm.setFeedbackEjercicioId(feedbackEjercicio.getId());

            //COMPROBAMOS SI EL EJERCICIO TIENE SERIES ESTIPULADAS
            boolean tieneSeries = !implementacion.getSets().isEmpty();

            String pesoRealizado = null;
            String tiempoRealizado = null;
            String metrosRealizado = null;
            String kilocaloriasRealizado = null;

            //EL EJERCICIO TIENE SERIES ESTIPULADAS
            if(tieneSeries){
                //CONSIDERO QUE AL HABER SERIES ESTIPULADAS SIEMPRE HABRÁ COMO MÍNIMO UNA SERIE ESTIPULADA, POR TANTO EN CASO DE QUE EL USUARIO NO HAYA SELECCIONADO
                //SERIE TODAVÍA SE PONE LA SERIE SELECCIONADA A 1
                if(set == null) {
                    set = "1";
                }

                //OBTENGO EL FEEDBACK DE ESE EJERCICIO EN ESA SERIE CONCRETA Y LO AÑADO AL MODELO
                FeedbackEjercicioserieDTO feedbackEjercicioSerie = feedbackEjercicioSerieService.getFeedbackPorEjecicioYSerie(feedbackEjercicio.getId(),set);
                model.addAttribute("feedbackSerie",feedbackEjercicioSerie);

                //MANTENEMOS EL OBJETO UI ACTUALIZADO
                feedbackSerieForm.setSerieSeleccionada(set);

                //OBTENGO LOS DATOS ALMACENADOS EN EL FEEDBACK DE LA SERIE Y METO EN EL OBJETO UI LOS QUE PROCEDAN
                if(feedbackEjercicioSerie!=null){
                    pesoRealizado = feedbackEjercicioSerie.getPesoRealizado();
                    tiempoRealizado = feedbackEjercicioSerie.getTiempoRealizado();
                    metrosRealizado = feedbackEjercicioSerie.getMetrosRealizado();
                    kilocaloriasRealizado = feedbackEjercicioSerie.getKilocaloriasRealizado();

                    String repeticionesRealizadas = feedbackEjercicioSerie.getRepeticionesRealizadas();
                    if(repeticionesRealizadas!=null) feedbackSerieForm.setRepeticionesRealizadas(repeticionesRealizadas);
                }
            //EL EJERCICIO NO TIENE SERIES ESTIPULADAS
            }else{
                //OBTENGO LOS DATOS ALMACENADOS EN EL FEEDBACK DEL EJERCICIO Y METO EN EL OBJETO UI LOS QUE PROCEDAN
                pesoRealizado = feedbackEjercicio.getSeguimientoPesoDone();
                tiempoRealizado = feedbackEjercicio.getSeguimientoTiempoDone();
                metrosRealizado = feedbackEjercicio.getSeguimientoMetrosDone();
                kilocaloriasRealizado = feedbackEjercicio.getSeguimientoKilocaloriasDone();
            }

            if(pesoRealizado!=null) feedbackSerieForm.setPesoRealizado(pesoRealizado);
            if(tiempoRealizado!=null) feedbackSerieForm.setMinutosRealizados(Integer.parseInt(tiempoRealizado)/60);
            if(tiempoRealizado!=null) feedbackSerieForm.setSegundosRealizados(Integer.parseInt(tiempoRealizado)%60);
            if(metrosRealizado!=null) feedbackSerieForm.setMetrosRealizado(metrosRealizado);
            if(kilocaloriasRealizado!=null) feedbackSerieForm.setKilocaloriasRealizado(kilocaloriasRealizado);

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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            String strTo = "/cliente/cliente_entrenamientos";

            //SI NO HAY FILTRO REDIRECCIONAR PARA MOSTRAR EL DIA DE HOY
            if(filtroDia.isEmpty()){
                strTo = "redirect:/cliente/";
            }else{
                //ACTUALIZO CON EL DIA DEL FILTRO
                int dia = Integer.parseInt(filtroDia);

                //SEMANA 1
                LocalDate fechaInicio = LocalDate.of(2000, 1, dia);
                //METEMOS EN LA SESION LA FECHA SELECCIONADA
                session.setAttribute("fechaSeleccionada",fechaInicio);

                //Obtengo las rutinas del dia seleccionado
                DiaEntrenamientoDTO diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(user.getId(),fechaInicio);
                List<ImplementacionEjercicioRutinaDTO> implementaciones;
                if(diaEntrenamiento != null){
                    //Obtengo la especifiacion de los ejercicios de las rutinas
                    implementaciones = implementacionEjercicioRutinaService.getImplementacionByRutina(diaEntrenamiento.getRutina().getId());
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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            String strTo = "/cliente/cliente_dietas";

            //SI NO HAY FILTRO REDIRECCIONAR PARA MOSTRAR EL DIA DE HOY
            if(filtroDia == null && filtroSemana == null){
                strTo = "redirect:/cliente/";
            }else{
                if(filtroDia == null) filtroDia = "1";
                if(filtroSemana == null) filtroSemana = "0";
                //ACTUALIZO CON EL DIA DEL FILTRO
                int dia = Integer.parseInt(filtroDia);
                int semana = Integer.parseInt(filtroSemana);

                //SEMANA Y DIA SELECCIONADO
                LocalDate fechaInicio = LocalDate.of(2000, 1, dia + (semana*7));
                //METEMOS EN LA SESION LA FECHA SELECCIONADA
                session.setAttribute("fechaSeleccionada",fechaInicio);

                //Obtengo las rutinas del dia seleccionado
                DiaDietaDTO diaDieta = diaDietaService.getDiaDietaDeClienteFecha(user.getId(),fechaInicio);
                List<ComidaDTO> comidas;
                if(diaDieta != null){
                    //OBTENGO LAS COMIDAS ASIGNADAS A ESE DIA
                    comidas = comidaService.getComidasByDiaDieta(diaDieta.getId());
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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            //OBTENEMOS EL FEEDBACK DEL EJERCICIO
            FeedbackEjercicioDTO feedbackEjercicio = feedbackEjercicioService.getFeedbackEjercicioById(feedbackEjercicioId);

            if (feedbackEjercicio!=null) {
                if(realizado == null) realizado = 0;
                //ACTUALIZAMOS EL CAMPO REALIZADO
                feedbackEjercicio.setRealizado(realizado);

                //EN CASO DE TENER SERIES
                if(seriesRealizadas!=null && seriesRealizadas>0){
                    //ESTABLECEMOS LAS SERIES REALIZADAS
                    feedbackEjercicio.setSeguimientoSetsDone("" + seriesRealizadas);

                    //POR SIMPLIFICAR, SI SE MODIFICA EL NUMERO DE SETS REALIZADAS EL CLIENTE DEBERÁ VOLVER A RELLENAR EL FEEDBACK DE ESTAS NUEVAS SERIES
                    //SI HABIA FEEDBACK ANTERIOR LO BORRAMOS PARA DAR PASO AL NUEVO
                    feedbackEjercicioSerieService.borrarFeedbackEjercicioSerie(feedbackEjercicio.getId());

                    //MODIFICAMOS LOS SETS REALIZADOS
                    if(realizado==0){
                        feedbackEjercicio.setSeguimientoSetsDone(null);
                        seriesRealizadas = 0;
                    }
                    //PREMARAMOS EL FEEBACK DE LAS NUEVAS SERIES REALIZADAS PARA QUE POSTERIORMENTE EL CLIENTE LAS RELLENE
                    for(int i = 1; i<= seriesRealizadas; i++){
                        feedbackEjercicioSerieService.prerararFeedbackEjercicioSeries(i,feedbackEjercicio.getId());
                    }
                }

                //GUARDAMOS LOS CAMBIOS REALIZADOS
                feedbackEjercicioService.guardarFeedbackEjercicio(feedbackEjercicio);
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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            //ACTUALIZAMOS EL FEEDBACK DEL EJERCICIO EN LA SERIE SELECCIONADA
            FeedbackEjercicioserieDTO feedbackEjercicioserie = feedbackEjercicioSerieService.getFeedbackPorEjecicioYSerie(feedbackSerieForm.getFeedbackEjercicioId(), feedbackSerieForm.getSerieSeleccionada());
            feedbackEjercicioserie.setPesoRealizado(feedbackSerieForm.getPesoRealizado());
            feedbackEjercicioserie.setRepeticionesRealizadas(feedbackSerieForm.getRepeticionesRealizadas());

            //GUARDAMOS LOS CAMBIOS EN EL FEEDBACK DE LA SERIE SELECCIONADA DEL EJERCICIO
            feedbackEjercicioSerieService.guardarFeedbackEjercicioSerie(feedbackEjercicioserie);

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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            FeedbackEjercicioserieDTO feedbackEjercicioserie = feedbackEjercicioSerieService.getFeedbackPorEjecicioYSerie(feedbackSerieForm.getFeedbackEjercicioId(),feedbackSerieForm.getSerieSeleccionada());

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
            feedbackEjercicioSerieService.guardarFeedbackEjercicioSerie(feedbackEjercicioserie);

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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            ImplementacionEjercicioRutinaDTO implementacion = implementacionEjercicioRutinaService.getImplementacionPorId(feedbackSerieForm.getImplementacionId());

            LocalDate dia = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaEntrenamientoDTO diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(user.getId(),dia);

            if (implementacion != null && dia != null) {
                //OBTENEMOS EL FEEDBACK DEL EJERCICIO
                FeedbackEjercicioDTO feedbackEjercicio = feedbackEjercicioService.getFeedbackEjercicioPorImplementacionYDia(implementacion,diaEntrenamiento);
                        //feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYDia(implementacion,diaEntrenamiento);

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
                feedbackEjercicioService.guardarFeedbackEjercicio(feedbackEjercicio);
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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            dir = "redirect:/cliente/ejercicio?id=" + implementacionId + "&set=" + set;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("mostrarDietas")
    public String doMostrarDietas(Model model,HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            //Semana 1
            LocalDate fechaInicio = LocalDate.of(2000, 1, 1);

            //OBTENGO EL DIAEDIETA
            DiaDietaDTO diaDieta = diaDietaService.getDiaDietaDeClienteFecha(user.getId(),fechaInicio);
            List<ComidaDTO> comidas;

            //OBTENGO LAS COMIDAS ASIGNADAS A ESE DIA
            //SI EXISTE EL DIADIETA PONGO EN LA SESION LA FECHA SELECCIONADA Y OBTENGO LA LISTA DE COMIDAS
            if(diaDieta!=null){
                session.setAttribute("fechaSeleccionada",diaDieta.getFecha());
                comidas = comidaService.getComidasByDiaDieta(diaDieta.getId());
            //SI NO EXISTE SE PASA LISTA VACIA Y NO MOSTRARA COMIDAS ESE DIA
            }else{
                comidas = new ArrayList<>();
            }

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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            //OBTENEMOS LA COMIDA POR SU ID
            ComidaDTO comida = comidaService.getComidaByID(Integer.parseInt(id));

            //OBTENEMOS LOS PLATOS ASOCIADOS A ESA COMIDA
            List<PlatoDTO> platos = cantidadIngredientePlatoComidaService.getPlatosByComida(comida.getId());
            //COMPROBAMOS SI SE HA SELECCIONADO PLATO, SI NO ES ASI POR DEFECTO SE PUESTRA EL PRIMERO
            PlatoDTO platoSeleccionadoEntity;
            if(platoSeleccionado == null){
                platoSeleccionadoEntity = platos.getFirst();
            }else{
                platoSeleccionadoEntity = platoService.getById(Integer.parseInt(platoSeleccionado));
            }
            //OBTENEMOS LA INFORMACION DE CANTIDAD Y NUTRICION DE LOS INGREDIENTES DEL PLATO SELECCIONADO
            List<CantidadIngredientePlatoComidaDTO> cantidades = cantidadIngredientePlatoComidaService.getCantidadesByPlatoComida(platoSeleccionadoEntity.getId(),comida.getId());
            //COMPROBAMOS SI SE HA SELECCIONADO UN INGREDIENTE, SI NO ES ASI POR DEFECTO SE MUESTRA EL PRIMERO
            CantidadIngredientePlatoComidaDTO cantidadSeleccionadaEntity;
            if(cantidadSeleccionada == null){
                cantidadSeleccionadaEntity = cantidades.getFirst();
            }else{
                cantidadSeleccionadaEntity = cantidadIngredientePlatoComidaService.getById(Integer.parseInt(cantidadSeleccionada));
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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            dir = "redirect:/cliente/seleccionarComida?id=" + comidaId + "&platoSeleccionado=" + platoSeleccionado;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("seleccionarIngrediente")
    public String doSeleccionarIngrediente(@RequestParam("platoId") Integer platoId, @RequestParam("comidaId") Integer comidaId, @RequestParam("cantidadSeleccionada") Integer cantidadSeleccionada, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
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
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            //OBTENEMOS LA CANTIDAD SELECCIONADA PARA GUARDAR EL FEEDBACK DE LA CANTIDAD CONSUMIDA
            CantidadIngredientePlatoComidaDTO cantidad = cantidadIngredientePlatoComidaService.getById(cantidadID);
            if(cantidad!=null) {
                cantidad.setCantidadConsumida(cantidadConsumida);
                cantidadIngredientePlatoComidaService.guardarCantidad(cantidad);
            }
            dir = "redirect:/cliente/seleccionarComida?id=" + comidaId + "&platoSeleccionado=" + platoId + "&cantidadSeleccionada=" + cantidadID;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("guardarFeedbackComida")
    public String doGuardarFeedbackComida(@RequestParam("comidaId") Integer comidaId, @RequestParam(required = false, value = "realizado") Byte realizado,
                                          @RequestParam("platoSeleccionadoID") Integer platoSeleccionadoID,HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            //OBTENEMOS LA COMIDA PARA GUARDAR EL FEEDBACK DE SI EL CLIENTE HA REALIZADO ESA COMIDA
            ComidaDTO comida = comidaService.getComidaByID(comidaId);

            if(comida!=null){
                if(realizado==null) realizado = 0;
                comida.setRealizado(realizado);
                comidaService.guardarComida(comida);
            }

            if(platoSeleccionadoID!=null){
                dir = "redirect:/cliente/seleccionarComida?id=" + comida.getId() + "&platoSeleccionado=" + platoSeleccionadoID;
            }else{
                dir = "redirect:/cliente/seleccionarComida?id=" + comida.getId();
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("guardarSeguimientoDieta")
    public String doGuardarSeguimientoDieta(@RequestParam("seguimientoDieta") String seguimientoDieta,HttpSession session, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            //OBTENEMOS LA FECHA SELECCIONADA Y CON ESTA EL DIADIETA
            LocalDate fecha = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaDietaDTO diaDieta = diaDietaService.getDiaDietaDeClienteFecha(user.getId(),fecha);

            //GUARDAMOS EL SEGUIMIENTO DE LA DIETA
            if(diaDieta!=null){
                diaDieta.setSeguimiento(seguimientoDieta);
                diaDietaService.guardarDiaDieta(diaDieta);

                //OBTENGO LAS COMIDAS ASIGNADAS A ESE DIA
                List<ComidaDTO> comidas = comidaService.getComidasByDiaDieta(diaDieta.getId());

                model.addAttribute("comidas",comidas);
                model.addAttribute("diaDieta",diaDieta);

            }
            //SE VUELVE A MOSTRAR LAS DIETAS DE ESE DIA
            dir = "/cliente/cliente_dietas";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("guardarSeguimientoEntrenamiento")
    public String doGuardarSeguimientoEntrenamiento(@RequestParam("seguimientoDieta") String seguimientoDieta,HttpSession session, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            //OBTENEMOS LA FECHA SELECCIONADA Y CON ESTA EL DIAENTRENAMIENTO
            LocalDate fecha = (LocalDate) session.getAttribute("fechaSeleccionada");
            DiaEntrenamientoDTO diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(user.getId(),fecha);

            //GUARDAMOS EL SEGUIMIENTO DEL ENTRENAMIENTO
            if(diaEntrenamiento!=null){
                diaEntrenamiento.setSeguimiento(seguimientoDieta);
                diaEntrenamientoService.guardarDiaEntrenamiento(diaEntrenamiento);

                //OBTENGO LOS EJERCICIOS DE LA RUTINA ASIGNADA ESE DIA
                List<ImplementacionEjercicioRutinaDTO> implementaciones = implementacionEjercicioRutinaService.getImplementacionByRutina(diaEntrenamiento.getRutina().getId());

                model.addAttribute("implementaciones",implementaciones);
                model.addAttribute("diaEntrenamiento",diaEntrenamiento);
            }
            //SE VUELVE A MOSTRAR LOS ENTRENAMIENTOS DE ESE DIA
            dir = "/cliente/cliente_entrenamientos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarDesempenyo")
    public String doMostrarDesempelo(HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            dir = "cliente/cliente_desempenyo";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/verDesempenyoDietas")
    public String doVerDesempenyoDietas(@RequestParam(required = false, value = "fechaDesempenyoDieta") String fechaDesempenyoDieta, HttpSession session,Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            DiaDietaDTO diaDieta;
            LocalDate fechanueva;
            //OBTENEMOS EL DIA DIETA SEGUN EL FILTRADO, SI NO SE HA FILTRADO SE PONE EL "DÍA DE HOY"
            if(fechaDesempenyoDieta==null){
                fechanueva = LocalDate.of(2000, 1, 1);
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                fechanueva = LocalDate.parse(fechaDesempenyoDieta, formatter);
            }
            diaDieta = diaDietaService.getDiaDietaDeClienteFecha(user.getId(),fechanueva);

            //PREPARAMOS UNA ESTRUCTURA QUE RELACIONA CADA COMIDA CON LA LISTA DE PLATOS Y CADA PLATO CON LA LISTA DE CANTIDADESINGREDIENTE
            Map<ComidaDTO, Map<PlatoDTO, List<CantidadIngredientePlatoComidaDTO>>> comidaPlatosCantidades = new HashMap<>();

            //OBTENEMOS ESA INFORMACION ITERATIVAMENTE Y VAMOS GUARDANDO EN LA ESTRUCTURA
            if(diaDieta!=null){
                List<ComidaDTO> comidas = comidaService.getComidasByDiaDieta(diaDieta.getId());

                for (ComidaDTO c : comidas) {
                    Map<PlatoDTO, List<CantidadIngredientePlatoComidaDTO>> platosIngredientes = new HashMap<>();
                    List<PlatoDTO> platos = cantidadIngredientePlatoComidaService.getPlatosByComida(c.getId());

                    for (PlatoDTO p : platos) {
                        List<CantidadIngredientePlatoComidaDTO> cantidades = cantidadIngredientePlatoComidaService.getCantidadesByPlatoComida(p.getId(),c.getId());
                        platosIngredientes.put(p, cantidades);
                    }

                    comidaPlatosCantidades.put(c, platosIngredientes);
                }
            }

            model.addAttribute("comidaPlatosCantidades",comidaPlatosCantidades);
            model.addAttribute("fecha", fechanueva);

            dir = "cliente/cliente_desempenyoDietas";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/verDesempenyoEntrenamientos")
    public String doVerDesmepenyoEntrenamientos(HttpSession session,Model model,@RequestParam(required = false, value = "fechaDesempenyoEntrenamiento") String fechaDesempenyoEntrenamiento){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (estaAutenticado(session) && esCliente(rol)){
            DiaEntrenamientoDTO diaEntrenamiento;
            LocalDate fechanueva;
            //OBTENEMOS EL DIA ENTRENAMIENTO SEGUN EL FILTRADO, SI NO SE HA FILTRADO SE PONE EL "DÍA DE HOY"
            if(fechaDesempenyoEntrenamiento==null){
                fechanueva = LocalDate.of(2000, 1, 1);
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                fechanueva = LocalDate.parse(fechaDesempenyoEntrenamiento, formatter);
            }
            diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(user.getId(),fechanueva);

            //PREPARAMOS LAS SIGUIENTES ESTRUCTURAS DE DATOS:
            //1.LISTA DE IMPLEMENTACIONES DEL DIA PARA RECORRERLAS
            //2.MAP DE IMPLEMENTACION Y FEEDBACK DE LAS SERIES PARA VER PARA CADA IMPLEMENTACION SI TIENE FEEDBACK EN LAS SERIES O SI TIENE UNA LISTA VACIA (NO TIENE FEEDBACK EN LAS SERIES)
            //3.LISTA DE PARES IMPLEMENTACION-FEEDBACKEJERCICIO PARA LLEVAR A LA JSP TODAS LAS IMPLEMENTACIONES Y SU FEEDBACK ASOCIADO
            List<ImplementacionEjercicioRutinaDTO> implementaciones;
            Map<ImplementacionEjercicioRutinaDTO,List<FeedbackEjercicioserieDTO>> implementacionEjercicioRutinaListMap = new HashMap<>();
            List<Pair<ImplementacionEjercicioRutinaDTO,FeedbackEjercicioDTO>> listaPares = new ArrayList<>();

            //OBTENEMOS ESA INFORMACION ITERATIVAMENTE Y VAMOS GUARDANDO EN LAS ESTRUCTURAS
            if(diaEntrenamiento!=null){
                implementaciones = implementacionEjercicioRutinaService.getImplementacionByRutina(diaEntrenamiento.getRutina().getId());

                for(ImplementacionEjercicioRutinaDTO implementacion : implementaciones){
                    FeedbackEjercicioDTO feedbackEjercicio = feedbackEjercicioService.getFeedbackEjercicioPorImplementacionYDia(implementacion,diaEntrenamiento);
                    List<FeedbackEjercicioserieDTO> fserie = new ArrayList<>();
                    if(feedbackEjercicio!=null){
                        if(feedbackEjercicio.getSeguimientoSetsDone()!=null){
                            for(int serie = 0; serie<Integer.parseInt(feedbackEjercicio.getSeguimientoSetsDone());serie++){
                                FeedbackEjercicioserieDTO feedbackEjercicioserie = feedbackEjercicioSerieService.getFeedbackPorEjecicioYSerie(feedbackEjercicio.getId(), "" + (serie + 1));
                                fserie.add(feedbackEjercicioserie);
                            }
                        }
                    }
                    implementacionEjercicioRutinaListMap.put(implementacion,fserie);
                    listaPares.add(new Pair<>(implementacion,feedbackEjercicio));
                }

            }

            model.addAttribute("listaPares",listaPares);
            model.addAttribute("implementacionEjercicioRutinaListMap",implementacionEjercicioRutinaListMap);
            model.addAttribute("rol", rol);
            model.addAttribute("fecha", fechanueva);

            dir = "cliente/cliente_desempenyoEntrenamientos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarDesempenyoDieta")
    public String doFiltrarDesempenyoDieta(@RequestParam("fechaDesempenyoDieta") String fechaDesempenyoDieta, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            dir = "redirect:/cliente/verDesempenyoDietas?fechaDesempenyoDieta=" + fechaDesempenyoDieta;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarDesempenyoEntrenamiento")
    public String doFiltrarDesmpenyoEntrenamiento(@RequestParam("fechaDesempenyoEntrenamiento") String fechaDesempenyoEntrenamiento, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            dir = "redirect:/cliente/verDesempenyoEntrenamientos?fechaDesempenyoEntrenamiento=" + fechaDesempenyoEntrenamiento;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/irInicio")
    public String doIrInicio(HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esCliente(rol)){
            dir = "redirect:/cliente/";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

}