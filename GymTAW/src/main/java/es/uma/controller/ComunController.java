package es.uma.controller;

import es.uma.dao.*;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.TipoEjercicioDTO;
import es.uma.entity.*;
import es.uma.service.EjercicioService;
import es.uma.service.TipoEjercicioService;
import es.uma.ui.EjercicioUI;
import es.uma.ui.Implementacion;
import es.uma.ui.TipoEjercicioUI;
import es.uma.ui.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;

    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private TipoEjercicioService tipoEjercicioService;

    @GetMapping("/mostrarEjercicios")
    public String doEjercicios(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "admin/ejercicios";
            List<EjercicioDTO> ejercicios = ejercicioService.getAllExercises();
            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();
            model.addAttribute("ejercicios", ejercicios);
            model.addAttribute("tipos", tipos);
            model.addAttribute("rol", rol);
            model.addAttribute("ejercicio", new EjercicioUI());
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarEjercicio")
    public String doEditarEjercicio(@RequestParam("id") Integer id, Model model, HttpSession session, EjercicioUI ejercicioUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)  || esEntrenador(rol)) {
            dir = "crearEjercicio";
            model.addAttribute("tipos", tipoEjercicioService.getAll());
            model.addAttribute("ejercicioUI", ejercicioService.setEjercicioUI(id, ejercicioUI));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    private void setUser(Usuario usuario,User user){
        usuario.setRol(user.getRol().getId());
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
    public String doVerPerfilPropio(HttpSession session,Model model){

        String strTo = "/perfil";

        if(!estaAutenticado(session)) {
            strTo = "redirect:/";
        }else {

            User user = (User) session.getAttribute("user");
            Usuario usuario = new Usuario();

            setUser(usuario,user);

            model.addAttribute("usuario",usuario);
            UserRol rol = (UserRol) session.getAttribute("rol");
            model.addAttribute("rolid",rol.getId());
        }



        return strTo;
    }

    @GetMapping("/borrarEjercicio")
    public String doBorrarEjercicio(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)  || esEntrenador(rol)) {
            dir = "redirect:/comun/mostrarEjercicios";
            ejercicioService.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearNuevoEjercicio")
    public String doCrearNuevoEjercicio(Model model, HttpSession session, EjercicioUI ejercicioUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "crearEjercicio";
            model.addAttribute("tipos", tipoEjercicioService.getAll());
            model.addAttribute("ejercicioUI", ejercicioUI);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarEjercicio")
    public String doGuardarEjercicio(@ModelAttribute EjercicioUI ejercicioUI, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)  || esEntrenador(rol)) {
            if(ejercicioUI.getId() == null){
                ejercicioService.saveExercise(ejercicioUI);

            }else{
                ejercicioService.editExercise(ejercicioUI);
            }
            dir = "redirect:/comun/mostrarEjercicios";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarEjercicios")
    public String doFiltrarEjercicios(Model model, HttpSession session, @ModelAttribute EjercicioUI ejercicioUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        model.addAttribute("rol", rol);
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            if(ejercicioUI.estaVacio()){
                dir = "redirect:/comun/mostrarEjercicios";
            }else {
                if (ejercicioUI.getIdTipo() == null) {
                    model.addAttribute("ejercicios", ejercicioService.filterExercises(ejercicioUI.getNombre(), ejercicioUI.getDescripcion()));
                } else {
                    model.addAttribute("ejercicios", ejercicioService.filterExercisesWithType(ejercicioUI.getNombre(), ejercicioUI.getDescripcion(), ejercicioUI.getIdTipo()));
                }
                dir = "admin/ejercicios";
            }
            model.addAttribute("ejercicio", ejercicioUI);
            model.addAttribute("tipos", tipoEjercicioService.getAll());

        } else {
            dir = "redirect:/";
        }
        return dir;
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

    @PostMapping("/filtrartipo")
    public String doFiltrarImplementacion(@RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "iddia", required = false) Integer iddia,
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


            if(iddia!=null){
                implementacion.setIdDia(iddia);
            }

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


    @GetMapping("/verImplementacionesAsociadas")
    public String doVerImplementacionesAsociadas(@RequestParam("id") Integer id, HttpSession session, Model model){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)  || esEntrenador(rol)) {
            dir = "admin/mostrarImplementaciones";
            Ejercicio ejercicio = ejercicioRepository.findById(id).orElse(null);
            List<ImplementacionEjercicioRutina> implementaciones = implementacionEjercicioRutinaRepository.buscarPorEjercicio(ejercicio);
            List<Rutina> rutinas = rutinaRepository.findAll();
            model.addAttribute("ejercicio", ejercicio);
            model.addAttribute("implementaciones", implementaciones);
            model.addAttribute("rutinas", rutinas);
            model.addAttribute("implementacion", new Implementacion());
            model.addAttribute("rol", rol);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearImplementacion")
    public String doCrearImplementacion(@RequestParam("id") Integer id, HttpSession session, Model model){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "crearImplementacion";
            model.addAttribute("ejercicios", ejercicioRepository.findAll());
            List<Rutina> rutinas = rutinaRepository.findAll();
            Implementacion implementacion = new Implementacion();
            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            implementacion.setEjercicio(ejercicioRepository.findById(id).orElse(null));
            model.addAttribute("tipos",tipos);
            model.addAttribute("rutinas", rutinas);
            model.addAttribute("implementacion", implementacion);
            model.addAttribute("rol", rol);
        } else {
            dir = "redirect:/";
        }
        return dir;
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

    //Este guardar es para entrenador
    @PostMapping("/guardarimplementacion")
    public String doGuardarImplementacionEntrenador(@ModelAttribute("implementacion") Implementacion implementacion,HttpSession sesion){
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

    //Este guardar es para admin
    @PostMapping("/guardarImplementacion")
    public String doGuardarImplementacion(@ModelAttribute Implementacion implementacion, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        String idEjercicio;
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(implementacion.getId() == null){
                ImplementacionEjercicioRutina ier = new ImplementacionEjercicioRutina();
                ier.setKilocalorias(implementacion.getKilocalorias());
                ier.setEjercicio(implementacion.getEjercicio());
                ier.setRutina(implementacion.getRutina());
                ier.setPeso(implementacion.getPeso());
                ier.setMetros(implementacion.getMetros());
                ier.setTiempo(implementacion.getTiempo());
                ier.setSets(implementacion.getSets());
                ier.setRepeticiones(implementacion.getRepeticiones());

                implementacionEjercicioRutinaRepository.save(ier);
                idEjercicio = implementacion.getEjercicio().getId().toString();
            }else{
                ImplementacionEjercicioRutina ier = implementacionEjercicioRutinaRepository.findById(implementacion.getId()).orElse(null);
                ier.setKilocalorias(implementacion.getKilocalorias());
                ier.setPeso(implementacion.getPeso());
                ier.setMetros(implementacion.getMetros());
                ier.setTiempo(implementacion.getTiempo());
                ier.setSets(implementacion.getSets());
                ier.setRepeticiones(implementacion.getRepeticiones());

                implementacionEjercicioRutinaRepository.save(ier);
                idEjercicio = ier.getEjercicio().getId().toString();
            }

            dir = "redirect:/comun/verImplementacionesAsociadas?id="+idEjercicio;

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarImplementacion")
    public String doEditarImplementacion(@RequestParam("id") Integer id, HttpSession session, Model model, Implementacion implementacion){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)  || esEntrenador(rol)) {
            dir = "crearImplementacion";
            ImplementacionEjercicioRutina ier = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
            List<Rutina> rutinas = rutinaRepository.findAll();
            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();

            implementacion.setId(ier.getId());
            implementacion.setEjercicio(ier.getEjercicio());
            implementacion.setKilocalorias(ier.getKilocalorias());
            implementacion.setPeso(ier.getPeso());
            implementacion.setMetros(ier.getMetros());
            implementacion.setTiempo(ier.getTiempo());
            implementacion.setSets(ier.getSets());
            implementacion.setRepeticiones(ier.getRepeticiones());
            model.addAttribute("ejercicios", ejercicioRepository.findAll());
            model.addAttribute("rutinas", rutinas);
            model.addAttribute("tipos",tipos);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarImplementacion")
    public String doBorrarImplementacion(@RequestParam("idEjercicio") Integer idEjercicio, @RequestParam("idImplementacion") Integer idImplementacion, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)  || esEntrenador(rol)) {
            dir = "redirect:/comun/verImplementacionesAsociadas?id="+idEjercicio;
            this.implementacionEjercicioRutinaRepository.deleteById(idImplementacion);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarImplementaciones")
    public String doFiltradoImplementaciones(@RequestParam("id") Integer id, HttpSession session, Model model, @ModelAttribute Implementacion implementacion){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            if(implementacion.estaVacio()){
                dir = "redirect:/comun/verImplementacionesAsociadas?id="+id;
            }else{
                Ejercicio ejercicio = ejercicioRepository.findById(id).orElse(null);
                List<ImplementacionEjercicioRutina> implementaciones = implementacionEjercicioRutinaRepository.filtrarImplementaciones(ejercicio, implementacion.getRutina(), implementacion.getSets(), implementacion.getRepeticiones(),
                        implementacion.getPeso(), implementacion.getTiempo(), implementacion.getMetros(), implementacion.getKilocalorias());
                List<Rutina> rutinas = rutinaRepository.findAll();
                model.addAttribute("ejercicio", ejercicio);
                model.addAttribute("rutinas", rutinas);
                model.addAttribute("implementaciones", implementaciones);
                model.addAttribute("implementacion", implementacion);
                model.addAttribute("rol", rol);
                dir = "admin/mostrarImplementaciones";
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

}
