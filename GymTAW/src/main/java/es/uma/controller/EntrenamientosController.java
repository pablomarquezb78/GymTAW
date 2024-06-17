package es.uma.controller;


import es.uma.dao.*;
import es.uma.entity.*;
import es.uma.ui.AsociacionRutina;
import es.uma.ui.EjercicioUI;
import es.uma.ui.Implementacion;
import es.uma.ui.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/entrenamientos")
public class EntrenamientosController extends BaseController{

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;
    @Autowired
    private EjercicioRepository ejercicioRepository;
    @Autowired
    private RutinaRepository rutinaRepository;
    @Autowired
    private ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    private TipoEjercicioRepository tipoEjercicioRepository;


    @GetMapping("/")
    public String doListar (Model model, HttpSession sesion){
        String strTo = "/crosstrainer/entrenador_listadoclientes";
        UserRol rol = (UserRol) sesion.getAttribute("rol");
        if(estaAutenticado(sesion) == true && esEntrenador(rol) ){
            List<User> lista = userRepository.clientesAsociadosConEntrenador((User) sesion.getAttribute("user"));
            model.addAttribute("lista",lista);
        }else {
            strTo= "redirect:/";
        }

        return strTo;
    }


    @GetMapping("/versemana")
    public String doVerSemanaEntrenamientos (@RequestParam("id") Integer idcliente,Model model, HttpSession session){


        List<DiaEntrenamiento> diaEntrenamientos = (List<DiaEntrenamiento>) diaEntrenamientoRepository.diasEntrenamientosdeCliente(idcliente);

        model.addAttribute("idcliente",idcliente);
        model.addAttribute("diasEntrenamientos", diaEntrenamientos);

        return "/crosstrainer/entrenador_semana";
    }

    @PostMapping("/nuevo-entrenamiento")
    public String doCreateEntrenamiento(@RequestParam("id") Integer id, Model model, HttpSession session){
        String strTo =  "/crosstrainer/entrenador_crear_entrenamiento";

        UserRol rol = (UserRol) session.getAttribute("rol");

        if(estaAutenticado(session) == true && esEntrenador(rol) ) {
            model.addAttribute("idcliente", id);
        }else{
            strTo= "redirect:/";
        }

        return strTo;
    }

    @GetMapping("/crearrutina")
    public String doCrearRutina(@RequestParam("idrutina") Integer idrutina,Model model){
        String strTo = "/crosstrainer/entrenador_crear_rutina";

        Rutina rutina = rutinaRepository.getById(idrutina);

        List<ImplementacionEjercicioRutina> lista = implementacionEjercicioRutinaRepository.encontrarImplementacionesPorRutinas(rutina);
        if(lista==null){
            lista = new ArrayList<>();
        }

        model.addAttribute("rutina",rutina);
        model.addAttribute("implementaciones",lista);

        return strTo;
    }

    @PostMapping("/guardarimplementacionrutina")
    public String doGuardarImplementacionRutina(@ModelAttribute("implementacion") Implementacion implementacion,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/crearrutina?idrutina=" + implementacion.getRutina().getId();

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutina imp;
            if(implementacion.getId()!=null){
                imp = this.implementacionEjercicioRutinaRepository.findById(implementacion.getId()).orElse(null);
                asignarImplementacionReal(imp,implementacion);
            }else{
                imp = new ImplementacionEjercicioRutina();
                imp.setRutina(implementacion.getRutina());

                asignarImplementacionReal(imp,implementacion);
            }

            this.implementacionEjercicioRutinaRepository.save(imp);
        }


        return strTo;
    }

    @PostMapping("/cambiarnombrerutina")
    public String doCambiarNombreRutina(@RequestParam("nombre") String nombre,@RequestParam("idrutina") Integer idrutina,
                                               Model model,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/crearrutina?idrutina=" + idrutina;

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            if(nombre!=""){
                Rutina rutina = rutinaRepository.getById(idrutina);
                rutina.setNombre(nombre);
                rutinaRepository.save(rutina);
            }
        }


        return strTo;
    }

    @PostMapping("/borrarimplementacionderutina")
    public String doBorrarImplementacionRutina(@RequestParam("id") Integer id,@RequestParam("idrutina") Integer idrutina,
                                         Model model,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/crearrutina?idrutina=" + idrutina;

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutina imp = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
            implementacionEjercicioRutinaRepository.delete(imp);
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

            Rutina r = rutinaRepository.getById(idrutina);
            implementacion.setRutina(r);

            model.addAttribute("implementacion",implementacion);

            List<Ejercicio> ejercicios = ejercicioRepository.findAll();
            model.addAttribute("ejercicios",ejercicios);

            Boolean editable = false;
            model.addAttribute("editable",editable);

        }


        return strTo;
    }

    @PostMapping("/entrenador-rutina")
    public String doLoadFecha(@RequestParam("accion") String accion, @RequestParam("id") Integer idcliente, @RequestParam("fecha") String fecha,Model model, HttpSession session){

       String url = "redirect:/";
       User user = (User) session.getAttribute("user");
//       LocalDate fechaConvertida = convertirStringALocalDate(fecha);

       if(accion.equals("crear")){
            Rutina rutina = new Rutina();
            User entrenador = (User) session.getAttribute("user");
            rutina.setEntrenador(entrenador);

           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           LocalDate fechanueva = LocalDate.parse(fecha, formatter);

           // Convertimos LocalDate to Instant
           Instant fechaCreacion = fechanueva.atStartOfDay(ZoneOffset.UTC).toInstant();

            rutina.setFechaCreacion(Instant.from(fechaCreacion));
            rutina.setNombre("Rutina de " + entrenador.getNombre() +"("+ fecha + ")");
            rutinaRepository.save(rutina);

            return "redirect:/entrenamientos/crearrutina?idrutina=" + rutina.getId();

       }

       if(accion.equals("asociar")){
           List<Rutina> rutinas = rutinaRepository.listarRutinasUsuario(user.getId());

           AsociacionRutina asociacionRutina = new AsociacionRutina();
           asociacionRutina.setIdCliente(idcliente);
           asociacionRutina.setIdTrainer(user.getId());
           asociacionRutina.setFecha(fecha);

           model.addAttribute("rutinas",rutinas);
           model.addAttribute("asociacionRutina",asociacionRutina);

           return "/crosstrainer/entrenador_asociar_rutina";
       }

       return url;

    }

    @PostMapping("/asociar-rutina")
    public String doAsociarRutina(AsociacionRutina asociacionRutina, Model model, HttpSession session){

        LocalDate fechaConvertida = convertirStringALocalDate(asociacionRutina.getFecha());

        Rutina rutina = rutinaRepository.getById(asociacionRutina.getIdRutina());

        User user = userRepository.getById(asociacionRutina.getIdCliente());

        DiaEntrenamiento diaEntrenamiento = new DiaEntrenamiento();
        diaEntrenamiento.setRutina(rutina);
        diaEntrenamiento.setCliente(user);
        diaEntrenamiento.setFecha(fechaConvertida);

        diaEntrenamientoRepository.save(diaEntrenamiento);

        return "redirect:/entrenamientos/versemana?id="+asociacionRutina.getIdCliente();
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

/*
    @GetMapping("/versemana")
    public String doVerSemanaEntrenamientos (@RequestParam("id") Integer idcliente,Model model, HttpSession session){
        String strTo = "/crosstrainer/entrenador_semana";

        if(!estaAutenticado(session)){
            strTo = "redicrect:/";
        }else{
            User cliente = (User) userRepository.findById(idcliente).orElse(null);
            List<DiaEntrenamiento> dias =  diaEntrenamientoRepository.diasEntrenamientosdeCliente(cliente);
            model.addAttribute("dias",dias);
            model.addAttribute("cliente",cliente);
        }


        return strTo;
    }
    */

    @PostMapping("/borrardia")
    public String doBorrarDiaEntrenamiento (@RequestParam("id") Integer id,HttpSession session){

        DiaEntrenamiento dia = (DiaEntrenamiento) diaEntrenamientoRepository.findById(id).orElse(null);

        String strTo = "redirect:/entrenamientos/versemana?id=" + dia.getCliente().getId();


        if(!estaAutenticado(session)){
            strTo = "redicrect:/";
        }else{
            diaEntrenamientoRepository.deleteById(id);
        }
        return strTo;
    }

    @PostMapping("/creardia")
    public String doCrearDia(@RequestParam("clienteid") Integer clienteid,HttpSession session){
        String strTo = "redirect:/entrenamientos/versemana?id=" + clienteid;
        if(!estaAutenticado(session)){
            strTo = "redirect:/";
        }else{
            User cliente = (User) userRepository.findById(clienteid).orElse(null);
            DiaEntrenamiento dia = new DiaEntrenamiento();
            dia.setFecha(LocalDate.now());
            dia.setCliente(cliente);

            Rutina r = new Rutina();
            User trainer = (User) session.getAttribute("user");
            r.setEntrenador(trainer);
            r.setFechaCreacion(Instant.now());
            rutinaRepository.save(r);
            dia.setRutina(r);


            this.diaEntrenamientoRepository.save(dia);
        }

        return strTo;
    }

    @GetMapping("/editardia")
    public String doEditarDia(@RequestParam("iddia")Integer idDia,Model model,HttpSession session){
        String strTo = "/crosstrainer/entrenador_rutina";

        if(!estaAutenticado(session)) {
            strTo = "redirect:/";
        }else{
            DiaEntrenamiento dia = diaEntrenamientoRepository.findById(idDia).orElse(null);


            int idRutina = dia.getRutina().getId();
            Rutina rutina = rutinaRepository.findById(idRutina).orElse(null);
            List<ImplementacionEjercicioRutina> implementaciones = implementacionEjercicioRutinaRepository.encontrarImplementacionesPorRutinas(rutina);
            model.addAttribute("implementaciones",implementaciones);
            model.addAttribute("iddia",idDia);
        }


        return strTo;
    }

    private void asignarImplementacionUI(Implementacion implementacion, ImplementacionEjercicioRutina imp){
        implementacion.setId(imp.getId());
        implementacion.setEjercicio(imp.getEjercicio());
        if(imp.getRutina()!=null) implementacion.setRutina(imp.getRutina());
        implementacion.setSets(imp.getSets());
        implementacion.setRepeticiones(imp.getRepeticiones());
        implementacion.setPeso(imp.getPeso());
        implementacion.setTiempo(imp.getTiempo());
        implementacion.setKilocalorias(imp.getKilocalorias());
        implementacion.setMetros(imp.getMetros());
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

    @GetMapping("/verperfil")
    public String doVerPerfil(HttpSession session,Model model){

        String strTo = "/crosstrainer/perfil";

        if(!estaAutenticado(session)) {
            strTo = "redirect:/";
        }else {

            User user = (User) session.getAttribute("user");
            Usuario usuario = new Usuario();

            setUser(usuario,user);

            model.addAttribute("usuario",usuario);

        }



        return strTo;
    }


    @PostMapping("/filtrartipo")
    public String doFiltrarImplementacion(@RequestParam(value = "id", required = false) Integer id,@RequestParam("iddia") Integer iddia,
                                          Model model,HttpSession sesion,@ModelAttribute("implementacion") Implementacion implementacion){

        String strTo = "crearImplementacion";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{

            if(id!=null){
                ImplementacionEjercicioRutina imp = implementacionEjercicioRutinaRepository.findById(id).orElse(null);


                if(imp!=null){
                    asignarImplementacionUI(implementacion,imp);
                    implementacion.setId(id);

                }
            }



            implementacion.setIdDia(iddia);

            model.addAttribute("implementacion",implementacion);

            List<Ejercicio> ejercicios = ejercicioRepository.filtrarEjercicioSoloDeTipo(implementacion.getTipofiltrado());
            model.addAttribute("ejercicios",ejercicios);

            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            model.addAttribute("tipos",tipos);

            Boolean editable = true;
            model.addAttribute("editable",editable);


        }


        return strTo;

    }

    @GetMapping("/editarimplementacion")
    public String doEditarImplementacion(@RequestParam("id") Integer id,@RequestParam("iddia") Integer iddia,
                                         Model model,HttpSession sesion){
        String strTo = "crearImplementacion";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutina imp = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
            //model.addAttribute("imp", imp);

            Implementacion implementacion = new Implementacion();
            asignarImplementacionUI(implementacion,imp);

            implementacion.setId(id);
            implementacion.setIdDia(iddia);

            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            model.addAttribute("tipos",tipos);

            model.addAttribute("implementacion",implementacion);


            List<Ejercicio> ejercicios = ejercicioRepository.findAll();
            model.addAttribute("ejercicios",ejercicios);

            Boolean editable = true;
            model.addAttribute("editable",editable);


        }


        return strTo;
    }

    @PostMapping("/borrarimplementacion")
    public String doBorrarImplementacion(@RequestParam("id") Integer id,@RequestParam("iddia") Integer iddia,
                                         Model model,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/editardia?iddia=" + iddia;

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutina imp = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
            //model.addAttribute("imp", imp);

           implementacionEjercicioRutinaRepository.delete(imp);
        }


        return strTo;
    }

    private void asignarImplementacionReal(ImplementacionEjercicioRutina implementacion, Implementacion imp){
        implementacion.setEjercicio(imp.getEjercicio());
        implementacion.setSets(imp.getSets());
        implementacion.setRepeticiones(imp.getRepeticiones());
        implementacion.setPeso(imp.getPeso());
        implementacion.setTiempo(imp.getTiempo());
        implementacion.setKilocalorias(imp.getKilocalorias());
        implementacion.setMetros(imp.getMetros());
    }

    @PostMapping("/guardarimplementacion")
    public String doGuardarImplementacion(@ModelAttribute("implementacion") Implementacion implementacion,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/editardia?iddia=" + implementacion.getIdDia();

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutina imp;
            if(implementacion.getId()!=null){
                imp = this.implementacionEjercicioRutinaRepository.findById(implementacion.getId()).orElse(null);
                asignarImplementacionReal(imp,implementacion);
            }else{
                imp = new ImplementacionEjercicioRutina();
                DiaEntrenamiento dia = diaEntrenamientoRepository.getById(implementacion.getIdDia());
                imp.setRutina(dia.getRutina());

                asignarImplementacionReal(imp,implementacion);
            }

            this.implementacionEjercicioRutinaRepository.save(imp);
        }


        return strTo;
    }

    @GetMapping("/crear-ejercicio")
    public String doCrearEjercicio(HttpSession sesion, Model model){


        UserRol userRol = (UserRol) sesion.getAttribute("rol");

        EjercicioUI ejercicioUI = new EjercicioUI();
        ejercicioUI.setTrainerEjercicio(userRol.getRolUsuario());
        List<TipoEjercicio> tipoEjercicios = tipoEjercicioRepository.findAll();
        model.addAttribute("tipos",tipoEjercicios);
        model.addAttribute("ejercicioUI",ejercicioUI);

        return "crearEjercicio";
    }

    @PostMapping("/guardar-ejercicio")
    public String doGuardarEjercicio(@ModelAttribute ("ejercicioUI") EjercicioUI ejercicioUI,HttpSession sesion){

        Ejercicio nuevoEjercicio = new Ejercicio();
        nuevoEjercicio.setNombre(ejercicioUI.getNombre());
        nuevoEjercicio.setDescripcion(ejercicioUI.getDescripcion());
        nuevoEjercicio.setEnlaceVideo(ejercicioUI.getEnlaceVideo());
        TipoEjercicio tipoEjercicio = tipoEjercicioRepository.getById(ejercicioUI.getIdTipo());
        nuevoEjercicio.setTipo(tipoEjercicio);

        ejercicioRepository.save(nuevoEjercicio);
        return "redirect:/";
    }

    @GetMapping("/crearimplementacion")
    public String doCrearImplementacion(@RequestParam("id") Integer id,@RequestParam("iddia") Integer iddia,
                                        Model model,HttpSession sesion){
        String strTo = "/crearImplementacion";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{

            Implementacion implementacion = new Implementacion();

            implementacion.setIdDia(iddia);

            model.addAttribute("implementacion",implementacion);

            List<Ejercicio> ejercicios = ejercicioRepository.findAll();
            model.addAttribute("ejercicios",ejercicios);

            Boolean editable = false;
            model.addAttribute("editable",editable);

            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            model.addAttribute("tipos",tipos);
        }


        return strTo;
    }


}