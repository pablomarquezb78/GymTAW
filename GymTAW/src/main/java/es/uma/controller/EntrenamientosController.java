package es.uma.controller;


import es.uma.dao.*;
import es.uma.dto.*;
import es.uma.entity.*;
import es.uma.service.*;
import es.uma.ui.AsociacionRutina;
import es.uma.ui.EjercicioUI;
import es.uma.ui.Implementacion;
import es.uma.ui.Usuario;
import es.uma.ui.TipoEjercicioUI;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/entrenamientos")
public class EntrenamientosController extends BaseController{


    @Autowired
    private UserService userService;
    @Autowired
    private DiaEntrenamientoService diaEntrenamientoService;
    @Autowired
    private RutinaService rutinaService;
    @Autowired
    private ImplementacionEjercicioRutinaService implementacionEjercicioRutinaService;
    @Autowired
    private TipoEjercicioService tipoEjercicioService;
    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private UserRolService userRolService;
    @Autowired
    private FeedbackEjercicioService feedbackEjercicioService;
    @Autowired
    private FeedbackEjercicioSerieService feedbackEjercicioSerieService;

    //DONE
    @GetMapping("/")
    public String doListar (Model model, HttpSession sesion){
        String strTo = "/crosstrainer/entrenador_listadoclientes";
        UserRolDTO rol = (UserRolDTO) sesion.getAttribute("rol");
        UserDTO userDTO = (UserDTO) sesion.getAttribute("user");

        if(estaAutenticado(sesion) && esEntrenador(rol)){
            List<UserDTO> lista = userService.getClientesDeEntrenador(userService.convertDtoToEntity(userDTO));

            model.addAttribute("lista",lista);
        }
        else {
            strTo= "redirect:/";
        }

        return strTo;
    }

    //DONE
    @PostMapping("/filtrarClientes")
    public String doFiltrarClientes (Model model, HttpSession sesion,@RequestParam("nombre") String nombre){
        String strTo = "/crosstrainer/entrenador_listadoclientes";
        UserRolDTO rol = (UserRolDTO) sesion.getAttribute("rol");
        UserDTO userdto = (UserDTO) sesion.getAttribute("user");

        if(estaAutenticado(sesion) && esEntrenador(rol) ){

            List<UserDTO> lista = userService.getClientesDeEntrenadorYNombre(userService.convertDtoToEntity(userdto),nombre);

            model.addAttribute("lista",lista);
        }else {
            strTo= "redirect:/";
        }

        return strTo;
    }


    @GetMapping("/versemana")
    public String doVerSemanaEntrenamientos (@RequestParam("id") Integer idcliente,Model model, HttpSession session){
        String strTo = "/crosstrainer/entrenador_semana";

        if(estaAutenticado(session)){
            List<DiaEntrenamientoDTO> dias = diaEntrenamientoService.getDiasDeClienteID(idcliente);

            model.addAttribute("idcliente",idcliente);
            model.addAttribute("diasEntrenamientos", dias);
        }else{
            strTo= "redirect:/";
        }

        return strTo;
    }


    @PostMapping("/nuevo-entrenamiento")
    public String doCreateEntrenamiento(@RequestParam("id") Integer id, Model model, HttpSession session){
        String strTo =  "/crosstrainer/entrenador_crear_entrenamiento";

        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");

        if(estaAutenticado(session) && esEntrenador(rol) ) {
            model.addAttribute("idcliente", id);
        }else{
            strTo= "redirect:/";
        }

        return strTo;
    }

    //DONE
    @GetMapping("/crearrutina")
    public String doCrearRutina(@RequestParam("idrutina") Integer idrutina,Model model){
        String strTo = "/crosstrainer/entrenador_crear_rutina";


        RutinaDTO rutina = rutinaService.getRutinaByID(idrutina);

        List<ImplementacionEjercicioRutinaDTO> lista = implementacionEjercicioRutinaService.getImplementacionByRutina(rutina.getId());

        if(lista==null) lista = new ArrayList<>();

        model.addAttribute("rutina",rutina);
        model.addAttribute("implementaciones",lista);

        return strTo;
    }

    @PostMapping("/guardarimplementacionrutina")
    public String doGuardarImplementacionRutina(@ModelAttribute("implementacion") Implementacion implementacion,@RequestParam("idejercicioelegido") Integer idej,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/crearrutina?idrutina=" + implementacion.getRutina();

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            implementacion.setEjercicio(ejercicioService.getById(idej));
            implementacionEjercicioRutinaService.guardarNuevaImplementacion(implementacion);
        }

        return strTo;
    }

    //DONE
    @PostMapping("/cambiarnombrerutina")
    public String doCambiarNombreRutina(@RequestParam("nombre") String nombre,@RequestParam("idrutina") Integer idrutina,
                                               Model model,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/crearrutina?idrutina=" + idrutina;

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            if(nombre!="") rutinaService.setNombreRutina(idrutina,nombre);
        }

        return strTo;
    }

    //Este es el bueno
    @PostMapping("/borrarimplementacionderutina")
    public String doBorrarImplementacionRutina(@RequestParam("id") Integer id,@RequestParam("idrutina") Integer idrutina,
                                         Model model,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/crearrutina?idrutina=" + idrutina;

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutinaDTO imp = implementacionEjercicioRutinaService.getByID(id);
            implementacionEjercicioRutinaService.deleteById(imp.getId());
        }


        return strTo;
    }

    @GetMapping("/crearimplementacionrutina")
    public String doCrearImplementacionRutina(@RequestParam("idrutina") Integer idrutina,HttpSession sesion,Model model){
        String strTo = "/crosstrainer/entrenador_implementacion_rutina";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{

            Implementacion implementacion = new Implementacion();

            RutinaDTO r = rutinaService.getRutinaByID(idrutina);
            implementacion.setRutina(r.getId());
            model.addAttribute("implementacion",implementacion);

            List<EjercicioDTO> ejercicios = ejercicioService.getAllExercises();
            model.addAttribute("ejercicios",ejercicios);

            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();
            model.addAttribute("tipos",tipos);

            Boolean editable = false;
            model.addAttribute("editable",editable);
        }


        return strTo;
    }

    @PostMapping("/filtrarejerciciopornombre")
    public String doFiltrarEjercicioPorNombre(@ModelAttribute("implementacion") Implementacion implementacion
            ,HttpSession sesion,Model model){
        String strTo = "/crosstrainer/entrenador_implementacion_rutina";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{

            model.addAttribute("implementacion",implementacion);

            List<EjercicioDTO> ejercicios = ejercicioService.getEjerciciosPorNombre(implementacion.getNombrefiltrado());
            model.addAttribute("ejercicios",ejercicios);

            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();
            model.addAttribute("tipos",tipos);

            Boolean editable = true;
            model.addAttribute("editable",editable);
        }


        return strTo;
    }

    //DONE
    @GetMapping("/entrenador-rutina")
    public String doLoadFecha(@RequestParam("id") Integer idcliente,Model model, HttpSession session){

            String strTo = "/crosstrainer/entrenador_asociar_rutina";
            UserDTO user = (UserDTO) session.getAttribute("user");

            if(!estaAutenticado(session)){
                strTo = "redirect:/";
            }else {

                AsociacionRutina asociacionRutina = new AsociacionRutina();
                asociacionRutina.setIdCliente(idcliente);
                asociacionRutina.setIdTrainer(user.getId());

                List<RutinaDTO> rutinas = rutinaService.getAllRutinas();

                model.addAttribute("asociacionRutina",asociacionRutina);
                model.addAttribute("rutinas", rutinas);
            }

           return strTo;

    }




    @GetMapping("/entrenador-crearrutina")
    public String entrenadorCrearRutina(Model model, HttpSession session){

            UserDTO entrenador = (UserDTO) session.getAttribute("user");
            RutinaDTO rutina = rutinaService.crearRutina(entrenador);

            return "redirect:/entrenamientos/crearrutina?idrutina=" + rutina.getId();

    }


    @PostMapping("/asociar-rutina")
    public String doAsociarRutina(AsociacionRutina asociacionRutina, Model model, HttpSession session){

        LocalDate fechaConvertida = convertirStringALocalDate(asociacionRutina.getFecha());

        DiaEntrenamientoDTO existeDia = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(asociacionRutina.getIdCliente(), fechaConvertida);

        RutinaDTO rutina = rutinaService.getRutinaByID(asociacionRutina.getIdRutina());
        UserDTO user = userService.getById(asociacionRutina.getIdCliente());


        if(existeDia == null){

            DiaEntrenamientoDTO diaEntrenamiento = new DiaEntrenamientoDTO();
            diaEntrenamiento.setRutina(rutina);
            diaEntrenamiento.setCliente(user);
            diaEntrenamiento.setFecha(fechaConvertida);

            diaEntrenamientoService.guardarDiaEntrenamiento(diaEntrenamiento);

            return "redirect:/entrenamientos/versemana?id="+asociacionRutina.getIdCliente();

        }else{

            AsociacionRutina asociacionRutinaAux = new AsociacionRutina();
            asociacionRutinaAux.setIdCliente(user.getId());
            asociacionRutinaAux.setIdTrainer(rutina.getEntrenador().getId());

            List<RutinaDTO> rutinas = rutinaService.getAllRutinas();

            model.addAttribute("asociacionRutina",asociacionRutina);
            model.addAttribute("rutinas", rutinas);
            model.addAttribute("fallo",true);

            return "/crosstrainer/entrenador_asociar_rutina";
        }

    }


    private LocalDate convertirStringALocalDate(String fechaStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(fechaStr, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String doActualizarLoadFecha(Integer id, LocalDate fechaConvertida){

        return "redirect:/entrenamientos/versemana?id="+id;

    }



    //todo
    @PostMapping("/borrardia")
    public String doBorrarDiaEntrenamiento (@RequestParam("id") Integer id,HttpSession session){

        DiaEntrenamientoDTO dia = diaEntrenamientoService.getbyID(id);

        String strTo = "redirect:/entrenamientos/versemana?id=" + dia.getCliente().getId();

        if(!estaAutenticado(session)){
            strTo = "redicrect:/";
        }else{
            diaEntrenamientoService.borrarDiaID(id);
        }
        return strTo;

    }



    @GetMapping("/verperfilcliente")
    public String doVerPerfilCliente(@RequestParam("idcliente") Integer id,HttpSession session,Model model){

        String strTo = "/perfil";

        if(!estaAutenticado(session)) {
            strTo = "redirect:/";
        }else {

            UserDTO user = userService.getById(id);
            Usuario usuario = new Usuario();
            userService.setUserVerPerfilCliente(usuario,userService.convertDtoToEntity(user));

            model.addAttribute("usuario",usuario);
            UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
            model.addAttribute("rolid",rol.getId());
        }



        return strTo;
    }


    @GetMapping("/editarimplementaciondefinitiva")
    public String doEditarImplementacionDefinitiva(@RequestParam("id") Integer id,
                                         Model model,HttpSession sesion){
        String strTo = "crearImplementacion";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutinaDTO imp = implementacionEjercicioRutinaService.getByID(id);

            Implementacion implementacion = new Implementacion();
            //asignarImplementacionUI(implementacion,imp);
            implementacionEjercicioRutinaService.asignarImplementacionDTOaImplementacionUI(implementacion,imp);
            implementacion.setId(id);
            String tiempo = imp.getTiempo();
            String cal = imp.getKilocalorias();

            implementacion.setAuxValue(0);

            model.addAttribute("implementacion",implementacion);

            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();
            model.addAttribute("tipos",tipos);

            List<EjercicioDTO> ejercicios = ejercicioService.getAllExercises();
            model.addAttribute("ejercicios",ejercicios);

            Boolean editable = true;


            model.addAttribute("editable",editable);
        }


        return strTo;
    }


    @GetMapping("/editarimplementacionentrenamientos")
    public String doEditarImplementacionEntrenamientos(@RequestParam("id") Integer id,
                                                   Model model,HttpSession sesion){
        String strTo = "crearImplementacion";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutinaDTO imp = implementacionEjercicioRutinaService.getByID(id);

            Implementacion implementacion = new Implementacion();
            //asignarImplementacionUI(implementacion,imp);
            implementacionEjercicioRutinaService.asignarImplementacionDTOaImplementacionUI(implementacion,imp);
            implementacion.setId(id);
            String tiempo = imp.getTiempo();
            String cal = imp.getKilocalorias();
            implementacion.setAuxValue(1);

            model.addAttribute("implementacion",implementacion);

            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();
            model.addAttribute("tipos",tipos);

            List<EjercicioDTO> ejercicios = ejercicioService.getAllExercises();
            model.addAttribute("ejercicios",ejercicios);

            Boolean editable = true;
            model.addAttribute("editable",editable);
        }


        return strTo;
    }







    private void asignarImplementacionReal(ImplementacionEjercicioRutina implementacion, Implementacion imp){
        //implementacion.setEjercicio(imp.getEjercicio());
        implementacion.setSets(imp.getSets());
        implementacion.setRepeticiones(imp.getRepeticiones());
        implementacion.setPeso(imp.getPeso());
        implementacion.setTiempo(imp.getTiempo());
        implementacion.setKilocalorias(imp.getKilocalorias());
        implementacion.setMetros(imp.getMetros());
    }

    @PostMapping("/guardarimplementacion")
    public String doGuardarImplementacion(@ModelAttribute("implementacion") Implementacion implementacion,
                    @RequestParam("idejercicioseleccionado") Integer idej,HttpSession sesion){

        String strTo = "redirect:/entrenamientos/crearrutina?idrutina=" + implementacion.getRutina();
        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            implementacion.setEjercicio(ejercicioService.getById(idej));
            implementacionEjercicioRutinaService.guardarImplementacionUI(implementacion);
        }


        return strTo;
    }


    @GetMapping("/crear-ejercicio")
    public String doCrearEjercicio(HttpSession sesion, Model model){

        UserRolDTO userRol = (UserRolDTO) sesion.getAttribute("rol");

        EjercicioUI ejercicioUI = new EjercicioUI();
        ejercicioUI.setTrainerEjercicio(userRol.getRolUsuario());
        List<TipoEjercicioDTO> tipoEjercicios = tipoEjercicioService.getAll();
        model.addAttribute("tipos",tipoEjercicios);
        model.addAttribute("ejercicioUI",ejercicioUI);

        return "crearEjercicio";
    }

    @PostMapping("/guardar-ejercicio")
    public String doGuardarEjercicio(@ModelAttribute ("ejercicioUI") EjercicioUI ejercicioUI,HttpSession sesion){

        ejercicioService.crearEjercicioDeUI(ejercicioUI);

        return "redirect:/";
    }

    @GetMapping("/mostrarTiposEjercicio")
    public String mostrarTiposEjercicio(HttpSession session, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {

            List<TipoEjercicioDTO> tiposEjercicio = tipoEjercicioService.getAll();

            model.addAttribute("tiposEjercicio", tiposEjercicio);
            //model.addAttribute("rol", rol);
            dir = "crosstrainer/tipos_ejercicio";

        } else {
            dir = "redirect:/";
        }
        return dir;

    }


    @GetMapping("/crear-tipo")
    public String doCrearTipo(HttpSession sesion, Model model){

        TipoEjercicioUI tipoEjercicioUI = new TipoEjercicioUI();

        tipoEjercicioUI.setIdTipoEjercicio(-1);
        model.addAttribute("tipoEjercicio",tipoEjercicioUI);

        return "crosstrainer/crearTipo";
    }

    @GetMapping("/editarTipo")
    public String editarTipo(HttpSession session, Model model, @RequestParam("id") Integer id){

        String dir = "crosstrainer/crearTipo";
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {


            TipoEjercicioDTO tipo = tipoEjercicioService.getById(id);
            TipoEjercicioUI tipoEjercicio = new TipoEjercicioUI();
            tipoEjercicio.setIdTipoEjercicio(tipo.getId());
            tipoEjercicio.setNombreTipoEjercicio(tipo.getTipoDeEjercicio());

            model.addAttribute("tipoEjercicio", tipoEjercicio);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarTipo")
    public String borrarTipo(HttpSession session, Model model, @RequestParam("id") Integer id){


        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {

            tipoEjercicioService.borrarTipoID(id);

           dir = "redirect:/entrenamientos/mostrarTiposEjercicio";

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardar-tipo-ejercicio")
    public String doGuardarTipoEjercicio(@ModelAttribute("tipoEjercicio") TipoEjercicioUI tipoEjercicioUI, Model model, HttpSession session){

        TipoEjercicioDTO nuevoTipoEjercicio;

        if(tipoEjercicioUI.getIdTipoEjercicio() == -1){
            nuevoTipoEjercicio = new TipoEjercicioDTO();
        }else{

            nuevoTipoEjercicio = tipoEjercicioService.getById(tipoEjercicioUI.getIdTipoEjercicio());
        }
        nuevoTipoEjercicio.setTipoDeEjercicio(tipoEjercicioUI.getNombreTipoEjercicio());

        tipoEjercicioService.guardarNuevoEj(nuevoTipoEjercicio);


        return "redirect:/entrenamientos/mostrarTiposEjercicio";
    }



    @GetMapping("/verImplementacionesAsociadasTipo")
    public String verImplementacionesAsociadasTipo(HttpSession session, Model model, @RequestParam("id") Integer id){

        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {

            List<EjercicioDTO> ejercicios = ejercicioService.getEjerciciosDeTipoDeEjercicio(id);
            model.addAttribute("ejercicios",ejercicios);

            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();

            model.addAttribute("tipos", tipos);
            model.addAttribute("rol",rol);
            model.addAttribute("ejercicio", new EjercicioUI());

            return "ejercicios";

        }else{
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarRutinas")
    public String verRutinasCompletas(HttpSession session, Model model){

        String dir = "crosstrainer/rutinas";
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");

        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            List<RutinaDTO> rutinas = rutinaService.getAllRutinas();

            model.addAttribute("rutinas",rutinas);
        }else{
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarRutinas")
    public String filtrarRutinas(HttpSession session,@RequestParam("nombrerutina") String nombre,@RequestParam("pormi") Integer tipofiltrado, Model model){

        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");

        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            UserDTO self = (UserDTO) session.getAttribute("user");

            List<RutinaDTO> rutinas = rutinaService.getRutinasPorNombreYEntrenador(nombre,tipofiltrado,userService.convertDtoToEntity(self));

            model.addAttribute("rutinas",rutinas);
            return "crosstrainer/rutinas";

        }else{
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarRutina")
    public String borrarRutina(HttpSession session, Model model, @RequestParam("idrutina") Integer idRutina){

        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {

            rutinaService.borrarRutinaID(idRutina);

            return "redirect:/entrenamientos/mostrarRutinas";

        }else{
            dir = "redirect:/";
        }
        return dir;
    }

    //@AUTHOR PABLO MÁRQUEZ BENÍTEZ
    @GetMapping("/verDesempenyoEntrenamientosEntrenador")
    public String doVerDesmepenyoEntrenamientos(@RequestParam(required = false, value = "fecha") String fecha, @RequestParam("idUsuario") Integer idUsuario, HttpSession session, Model model) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esEntrenador(rol)) {
            UserDTO userEntity = userService.getById(idUsuario);
            DiaEntrenamientoDTO diaEntrenamiento;
            LocalDate fechanueva;
            if(fecha==null){
                fechanueva = LocalDate.of(2000, 1, 1);
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                fechanueva = LocalDate.parse(fecha, formatter);
            }
            diaEntrenamiento = diaEntrenamientoService.getDiaEntrenamientoDeClienteFecha(userEntity.getId(), fechanueva);

            List<ImplementacionEjercicioRutinaDTO> implementaciones;
            Map<ImplementacionEjercicioRutinaDTO, List<FeedbackEjercicioserieDTO>> implementacionEjercicioRutinaListMap = new HashMap<>();
            List<Pair<ImplementacionEjercicioRutinaDTO, FeedbackEjercicioDTO>> listaPares = new ArrayList<>();

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

            model.addAttribute("listaPares", listaPares);
            model.addAttribute("implementacionEjercicioRutinaListMap", implementacionEjercicioRutinaListMap);
            model.addAttribute("fecha", fechanueva);
            model.addAttribute("rol", rol);

            dir = "cliente/cliente_desempenyoEntrenamientos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

}