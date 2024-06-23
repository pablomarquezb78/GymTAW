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

//@authors: Pablo Miguel Aguilar Blanco & Adrián Fernández Vera & Antonio Salvador Gámez Zafra
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

    
    //@author: Pablo Miguel Aguilar Blanco
    @GetMapping("/mostrarEjercicios")
    public String doEjercicios(Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");

        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "ejercicios";
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

    //@author: Pablo Miguel Aguilar Blanco
    @GetMapping("/editarEjercicio")
    public String doEditarEjercicio(@RequestParam("id") Integer id, Model model, HttpSession session, EjercicioUI ejercicioUI) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "crearEjercicio";
            model.addAttribute("tipos", tipoEjercicioService.getAll());
            model.addAttribute("ejercicioUI", ejercicioService.setEjercicioUI(id, ejercicioUI));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //@author: Pablo Miguel Aguilar Blanco
    @GetMapping("/borrarEjercicio")
    public String doBorrarEjercicio(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "redirect:/comun/mostrarEjercicios";
            ejercicioService.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
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

    @GetMapping("/editarPerfil")
    public String doEditarPerfilPropio(HttpSession session,Model model){

        String strTo = "/editarperfilpropio";

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

    @PostMapping("/guardarPerfil")
    public String doGuardarPerfilPropio(HttpSession session,@ModelAttribute("usuario") Usuario usuario,Model model){

        String strTo = "redirect:/comun/editarPerfil";

        UserDTO user = (UserDTO) session.getAttribute("user");
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");

        if(!estaAutenticado(session)) {
            strTo = "redirect:/";
        }else {
            userService.guardarCambiosPerfil(usuario);
            session.setAttribute("user",userService.getById(usuario.getId()));
        }
        return strTo;
    }


    //@author: Pablo Miguel Aguilar Blanco
    @GetMapping("/crearNuevoEjercicio")
    public String doCrearNuevoEjercicio(Model model, HttpSession session, EjercicioUI ejercicioUI) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "crearEjercicio";
            model.addAttribute("tipos", tipoEjercicioService.getAll());
            model.addAttribute("ejercicioUI", ejercicioUI);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //@author: Pablo Miguel Aguilar Blanco
    @PostMapping("/guardarEjercicio")
    public String doGuardarEjercicio(@ModelAttribute EjercicioUI ejercicioUI, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
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

    //@author: Pablo Miguel Aguilar Blanco
    @PostMapping("/filtrarEjercicios")
    public String doFiltrarEjercicios(Model model, HttpSession session, @ModelAttribute EjercicioUI ejercicioUI) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
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
                dir = "ejercicios";
            }
            model.addAttribute("ejercicio", ejercicioUI);
            model.addAttribute("tipos", tipoEjercicioService.getAll());

        } else {
            dir = "redirect:/";
        }
        return dir;
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
                ImplementacionEjercicioRutinaDTO imp = implementacionEjercicioRutinaService.getByID(id);

                if(imp!=null){
                    //asignarImplementacionUI(implementacion,imp);
                    implementacionEjercicioRutinaService.asignarImplementacionDTOaImplementacionUI(implementacion,imp);
                    implementacion.setId(id);
                }
            }


            if(iddia!=null) implementacion.setIdDia(iddia);


            model.addAttribute("implementacion",implementacion);


            List<EjercicioDTO> ejercicios = ejercicioService.getEjerciciosDeTipoDeEjercicio(implementacion.getIdfiltrado());
            model.addAttribute("ejercicios",ejercicios);



            List<TipoEjercicioDTO> tipos = tipoEjercicioService.getAll();
            model.addAttribute("tipos",tipos);

            Boolean editable = true;
            model.addAttribute("editable",editable);
        }


        return strTo;

    }


    //@author: Pablo Miguel Aguilar Blanco
    @GetMapping("/verImplementacionesAsociadas")
    public String doVerImplementacionesAsociadas(@RequestParam("id") Integer id, HttpSession session, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "mostrarImplementaciones";
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

    //@author: Pablo Miguel Aguilar Blanco
    @GetMapping("/crearImplementacion")
    public String doCrearImplementacion(@RequestParam("id") Integer id, HttpSession session, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
            dir = "crearImplementacion";

            Implementacion implementacion = new Implementacion();
            implementacion.setEjercicio(ejercicioService.getById(id));


            model.addAttribute("tipos",tipoEjercicioService.getAll());
            model.addAttribute("rutinas", rutinaService.getAllRutinas());
            model.addAttribute("ejercicios", ejercicioService.getAllExercises());
            model.addAttribute("implementacion", implementacion);
            model.addAttribute("rol", rol);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    //Este guardar es para entrenador
    @PostMapping("/guardarImplementacionTrainer")
    public String doGuardarImplementacionTrainer(@ModelAttribute("implementacion") Implementacion implementacion,
                                          @RequestParam("idejercicioseleccionado") Integer idej,HttpSession sesion){

        String dir;
        UserRolDTO rol = (UserRolDTO) sesion.getAttribute("rol");

        if (esEntrenador(rol)) {
            implementacion.setEjercicio(ejercicioService.getById(idej));
            implementacionEjercicioRutinaService.guardarImplementacionUI(implementacion);
            dir ="redirect:/entrenamientos/crearrutina?idrutina="+implementacion.getRutina();

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //Este guardar es para admin
    //@author: Pablo Miguel Aguilar Blanco
    @PostMapping("/guardarImplementacion")
    public String doGuardarImplementacion(@ModelAttribute("implementacion") Implementacion implementacion,
                                          @RequestParam("idejercicioseleccionado") Integer idej,HttpSession session){

        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");

        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
                implementacion.setEjercicio(ejercicioService.getById(idej));
                implementacionEjercicioRutinaService.guardarImplementacionUI(implementacion);
                dir ="redirect:/comun/verImplementacionesAsociadas?id="+idej;

        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    //@author: Pablo Miguel Aguilar Blanco
    @GetMapping("/borrarImplementacion")
    public String doBorrarImplementacion(@RequestParam("idEjercicio") Integer idEjercicio, @RequestParam("idImplementacion") Integer idImplementacion, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) ||esEntrenador(rol)) {
            dir = "redirect:/comun/verImplementacionesAsociadas?id="+idEjercicio;
            implementacionEjercicioRutinaService.deleteById(idImplementacion);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //@author: Pablo Miguel Aguilar Blanco
    @PostMapping("/filtrarImplementaciones")
    public String doFiltradoImplementaciones(@RequestParam("id") Integer id, HttpSession session, Model model, @ModelAttribute Implementacion implementacion){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol) || esEntrenador(rol)) {
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
                dir = "mostrarImplementaciones";
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

}
