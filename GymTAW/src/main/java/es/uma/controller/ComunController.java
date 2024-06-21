package es.uma.controller;

import es.uma.dao.*;
import es.uma.dto.*;
import es.uma.entity.*;
import es.uma.service.*;
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
    @Autowired
    private RutinaService rutinaService;
    @Autowired
    private ImplementacionEjercicioRutinaService implementacionEjercicioRutinaService;
    @Autowired
    private UserRolService userRolService;
    @Autowired
    private UserService userService;

    @GetMapping("/mostrarEjercicios")
    public String doEjercicios(Model model, HttpSession session) {
        String dir;
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
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
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
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

        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");

        if(!estaAutenticado(session)) {
            strTo = "redirect:/";
        }else {
            Usuario usuario = new Usuario();
            userService.setUserVerPerfilCliente(usuario,userService.convertDtoToEntity(user));
            model.addAttribute("usuario",usuario);
            model.addAttribute("rolid",rol.getId());
        }



        return strTo;
    }

    @GetMapping("/borrarEjercicio")
    public String doBorrarEjercicio(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
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
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
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
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
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
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        model.addAttribute("rol", rol);
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
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
        //implementacion.setEjercicio(imp.getEjercicio());
        if(imp.getRutina()!=null) implementacion.setRutina(imp.getRutina().getId());
        implementacion.setSets(imp.getSets());
        implementacion.setRepeticiones(imp.getRepeticiones());
        implementacion.setPeso(imp.getPeso());
        implementacion.setTiempo(imp.getTiempo());
        implementacion.setKilocalorias(imp.getKilocalorias());
        implementacion.setMetros(imp.getMetros());
    }

    //todo
    @PostMapping("/filtrartipo")
    public String doFiltrarImplementacion(@RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "iddia", required = false) Integer iddia,
                                          Model model,HttpSession sesion,@ModelAttribute("implementacion") Implementacion implementacion){

        String strTo = "crearImplementacion";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{

            if(id!=null){
                //ImplementacionEjercicioRutina imp = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
                ImplementacionEjercicioRutinaDTO imp = implementacionEjercicioRutinaService.getByID(id);

                if(imp!=null){
                    //asignarImplementacionUI(implementacion,imp);
                    implementacionEjercicioRutinaService.asignarImplementacionDTOaImplementacionUI(implementacion,imp);
                    implementacion.setId(id);
                }
            }


            if(iddia!=null) implementacion.setIdDia(iddia);


            model.addAttribute("implementacion",implementacion);

            //Descomentar:
            //List<Ejercicio> ejercicios = ejercicioRepository.filtrarEjercicioSoloDeTipo(implementacion.getTipofiltrado());
            //model.addAttribute("ejercicios",ejercicios);

            List<EjercicioDTO> ejercicios = ejercicioService.getEjerciciosDeTipoDeEjercicio(implementacion.getIdfiltrado());
            model.addAttribute("ejercicios",ejercicios);

            //List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            //model.addAttribute("tipos",tipos);

            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();
            model.addAttribute("tipos",tipos);

            Boolean editable = true;
            model.addAttribute("editable",editable);
        }


        return strTo;

    }


    @GetMapping("/verImplementacionesAsociadas")
    public String doVerImplementacionesAsociadas(@RequestParam("id") Integer id, HttpSession session, Model model){
        String dir;
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
            dir = "admin/mostrarImplementaciones";
            EjercicioDTO ejercicio = ejercicioService.getById(id);
            model.addAttribute("ejercicio", ejercicio);
            model.addAttribute("implementaciones", implementacionEjercicioRutinaService.findByExercise(ejercicio));
            model.addAttribute("rutinas", rutinaService.getAllRutinas());
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
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
            dir = "crearImplementacion";
            model.addAttribute("ejercicios", ejercicioRepository.findAll());
            List<Rutina> rutinas = rutinaRepository.findAll();
            Implementacion implementacion = new Implementacion();
            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            //implementacion.setEjercicio(ejercicioRepository.findById(id).orElse(null));
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
        //implementacion.setEjercicio(imp.getEjercicio());
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
    public String doGuardarImplementacion(@ModelAttribute("implementacion") Implementacion implementacion,
                                          @RequestParam("idejercicioseleccionado") Integer idej,HttpSession sesion){

        String dir;
        UserRol rol = (UserRol) sesion.getAttribute("rol");

        if (estaAutenticado(sesion) && esAdmin(rol)) {

                implementacion.setEjercicio(ejercicioService.getById(idej));
                implementacionEjercicioRutinaService.guardarImplementacionUI(implementacion);

            dir ="redirect:/comun/verImplementacionesAsociadas?id="+idej;

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarImplementacion")
    public String doEditarImplementacion(@RequestParam("id") Integer id, HttpSession session, Model model, Implementacion implementacion){
        String dir;
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
            dir = "crearImplementacion";
            ImplementacionEjercicioRutina ier = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
            List<Rutina> rutinas = rutinaRepository.findAll();
            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();

            implementacion.setId(ier.getId());
            //implementacion.setEjercicio(ier.getEjercicio());
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
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
            dir = "redirect:/comun/verImplementacionesAsociadas?id="+idEjercicio;
            implementacionEjercicioRutinaService.deleteById(idImplementacion);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarImplementaciones")
    public String doFiltradoImplementaciones(@RequestParam("id") Integer id, HttpSession session, Model model, @ModelAttribute Implementacion implementacion){
        String dir;
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (userService.checkAdminLogged(user,rol) || userService.checkTrainerLogged(user,rol)) {
            if(implementacion.estaVacio()){
                dir = "redirect:/comun/verImplementacionesAsociadas?id="+id;
            }else{
                EjercicioDTO ejercicio = ejercicioService.getById(id);

                model.addAttribute("ejercicio", ejercicio);
                model.addAttribute("rutinas", rutinaService.getAllRutinas());
                model.addAttribute("implementaciones", implementacionEjercicioRutinaService.filterImplementations(ejercicio, implementacion.getRutina(), implementacion.getSets(), implementacion.getRepeticiones(),
                        implementacion.getPeso(), implementacion.getTiempo(), implementacion.getMetros(), implementacion.getKilocalorias()));
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
