package es.uma.controller;

import es.uma.dao.AsignacionPlatoIngredienteDietistacreadorRepositoy;
import es.uma.dto.*;
import es.uma.service.*;
import es.uma.ui.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private PlatoService platoService;
    @Autowired
    private RegistroService registroService;
    @Autowired
    private UserRolService userRolService;
    @Autowired
    private AsignacionClienteEntrenadorService asignacionClienteEntrenadorService;
    @Autowired
    private AsignacionClienteDietistaService asignacionClienteDietistaService;
    @Autowired
    private CantidadIngredientePlatoComidaService cantidadIngredientePlatoComidaService;
    @Autowired
    private TipoComidaService tipoComidaService;
    @Autowired
    private DiaDietaService diaDietaService;
    @Autowired
    private ComidaService comidaService;
    @Autowired
    private RutinaService rutinaService;
    @Autowired
    private AsignacionPlatoIngredienteDietistacreadorRepositoy asignacionPlatoIngredienteDietistacreadorRepositoy;
    @Autowired
    private AsignacionPlatoIngredienteDietistaCreadorService asignacionPlatoIngredienteDietistaCreadorService;
    @Autowired
    private DiaEntrenamientoService diaEntrenamientoService;

    @GetMapping("/")
    public String doWelcome(Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("clientes", userService.getAllCustomers().size());
            model.addAttribute("entrenadores", userService.getAllTrainers().size());
            model.addAttribute("dietistas", userService.getAllDietistas().size());
            model.addAttribute("ejercicios", ejercicioService.getAllExercises().size());
            model.addAttribute("platos", platoService.getAllDishes().size());
            dir = "admin/inicioAdmin";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/autenticarUsuarios")
    public String doAutenticarUsuarios(Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("peticiones", registroService.getAllRegisters());
            dir = "admin/registro";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/autenticar")
    public String doAutenticar(@RequestParam("id") Integer id, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            userService.createNewUser(id);
            registroService.deleteRegisterById(id);
            dir = "redirect:/admin/autenticarUsuarios";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarPeticion")
    public String doBorrarPeticion(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/autenticarUsuarios";
            registroService.deleteRegisterById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarUsuarios")
    public String doUsuarios(Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("usuario", new Usuario());
            model.addAttribute("roles", userRolService.getAllRoles());
            model.addAttribute("usuarios", userService.getAllUsers());
            dir = "admin/usuarios";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarUsuarios")
    public String doFiltrarUsuarios(Model model, HttpSession session, @ModelAttribute Usuario usuario) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("roles", userRolService.getAllRoles());
            if(usuario.estaVacio()){
                dir = "redirect:/admin/mostrarUsuarios";
            }else{
                if(usuario.getRol() == null && usuario.getFechaNacimiento().isEmpty()){
                    model.addAttribute("usuarios", userService.filterUsers(usuario.getNombre(), usuario.getApellidos()));
                }else if (usuario.getRol() == null && !usuario.getFechaNacimiento().isEmpty()){
                    model.addAttribute("usuarios", userService.filterUsersWithDate(usuario.getNombre(), usuario.getApellidos(), convertirStringALocalDate(usuario.getFechaNacimiento())));
                }else if (usuario.getRol() != null && usuario.getFechaNacimiento().isEmpty()){
                    model.addAttribute("usuarios", userService.filterUsersWithRol(usuario.getNombre(), usuario.getApellidos(), usuario.getRol()));
                }else{
                    model.addAttribute("usuarios", userService.filterUsersWithRolAndDate(usuario.getNombre(), usuario.getApellidos(), convertirStringALocalDate(usuario.getFechaNacimiento()), usuario.getRol()));
                }
                dir = "admin/usuarios";
            }
            model.addAttribute("usuario", usuario);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearNuevoUsuario")
    public String doCrearUsuario(Model model, HttpSession session, Usuario usuario) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("roles", userRolService.getAllRoles());
            model.addAttribute("usuario", usuario);
            dir = "admin/crearUsuario";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarUsuario")
    public String doGuardarUsuario(@ModelAttribute Usuario usuario, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(usuario.getId() == null){
                userService.saveUser(usuario);
            }else{
                userService.editUser(usuario);
            }
            dir = "redirect:/admin/mostrarUsuarios";

        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    @GetMapping("/editarUsuario")
    public String doEditarUsuario(@RequestParam("id") Integer id, HttpSession session, Usuario usuario, Model model){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "/admin/crearUsuario";

            //REFACTORIZAR
            UserDTO editUser = userService.getById(id);
            usuario =  userService.setUsuario(usuario, editUser);

            List<UserRolDTO> roles = userRolService.getAllRoles();

            model.addAttribute("roles", roles);
            model.addAttribute("usuario", usuario);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    @GetMapping("/borrarUsuario")
    public String doBorrarUsuario(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarUsuarios";
            UserDTO user = userService.getById(id);
            String rolUser = user.getRol().getRolUsuario();
            if (rolUser.equals("dietista")) {
                asignacionClienteDietistaService.deleteByDietist(id);
                asignacionPlatoIngredienteDietistaCreadorService.deleteByDietist(id);
                for(ComidaDTO comidaDTO : comidaService.getByDietist(id)){
                    cantidadIngredientePlatoComidaService.deleteByFood(comidaDTO);
                }
                for(DiaDietaDTO diaDietaDTO : diaDietaService.getByDietist(id)){
                    comidaService.deleteByDiaDieta(diaDietaDTO);
                }
                diaDietaService.deleteByDietist(id);

            } else if (rolUser.equals("bodybuilder") || rolUser.equals("crosstrainer")) {
                asignacionClienteEntrenadorService.deleteByTrainer(id);
                rutinaService.deleteByTrainer(id);
            } else if (rolUser.equals("cliente")) {
                asignacionClienteDietistaService.deleteByCustomer(id);
                asignacionClienteEntrenadorService.deleteByCustomer(id);

                for(ComidaDTO comidaDTO : comidaService.getByCustomer(id)){
                    cantidadIngredientePlatoComidaService.deleteByFood(comidaDTO);
                }
                for(DiaDietaDTO diaDietaDTO : diaDietaService.getByCustomer(id)){
                    comidaService.deleteByDiaDieta(diaDietaDTO);
                }
                diaDietaService.deleteByCustomer(id);

                for(DiaEntrenamientoDTO diaEntrenamientoDTO : diaEntrenamientoService.getByCustomer(id)){
                    diaEntrenamientoService.borrarDiaID(diaEntrenamientoDTO.getId());
                }
            }
            userService.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/asignarCliente")
    public String doAsignar(Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/asignar";
            model.addAttribute("clientes", userService.getAllCustomers());
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/asignarEntrenador")
    public String doAsignarEntrenador(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        //Cargar todos los entrenadores que no esten asignados al cliente
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/asignacionEntrenador";


            List<AsignacionClienteEntrenadorDTO> ace = asignacionClienteEntrenadorService.findByCustomer(id);
            List<UserDTO> entrenadoresAsociados = userService.asociatedTrainers(ace);
            List<UserDTO> entrenadores = userService.noAsociatedTrainers(entrenadoresAsociados);

            model.addAttribute("entrenadores", entrenadores);
            model.addAttribute("cliente", userService.getById(id));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/anyadirAsignacionEntrenador")
    public String doAnyadirAsignacionEntrenador(@RequestParam("id") Integer id, @RequestParam("idCliente") Integer idCliente, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/asignarEntrenador?id="+id+"&idCliente="+idCliente;
            asignacionClienteEntrenadorService.addTrainer(id, idCliente);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/asignarDietista")
    public String doAsignarDietista(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/asignacionDietista";

            
            List<AsignacionClienteDietistaDTO> acd = asignacionClienteDietistaService.findByCustomer(id);
            List<UserDTO> dietistasAsociados = userService.asociatedDietist(acd);
            List<UserDTO> dietistas = userService.noAsociatedDietist(dietistasAsociados);

            model.addAttribute("dietistas", dietistas);
            model.addAttribute("cliente",userService.getById(id));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/anyadirAsignacionDietista")
    public String doAnyadirAsignacionDietista(@RequestParam("id") Integer id, @RequestParam("idCliente") Integer idCliente, Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/asignarDietista?id="+id+"&idCliente="+idCliente;
            asignacionClienteDietistaService.addDietist(id, idCliente);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/eliminarAsignaciones")
    public String doEliminarAsignaciones(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/eliminarAsignacion";
            List<AsignacionClienteDietistaDTO> asignacionesDietista = asignacionClienteDietistaService.findByCustomer(id);
            List<AsignacionClienteEntrenadorDTO> asignacionesEntrenador = asignacionClienteEntrenadorService.findByCustomer(id);
            model.addAttribute("asignacionesEntrenador", asignacionesEntrenador);
            model.addAttribute("asignacionesDietista", asignacionesDietista);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/eliminarAsignacionEntrenador")
    public String doEliminarAsignacionEntrenador(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            Integer clienteID = asignacionClienteEntrenadorService.deleteAsociation(id);
            dir = "redirect:/admin/eliminarAsignaciones?id="+clienteID;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/eliminarAsignacionDietista")
    public String doEliminarAsignacionDietista(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            Integer clienteID = asignacionClienteDietistaService.deleteAsociation(id);
            dir = "redirect:/admin/eliminarAsignaciones?id="+clienteID;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    @GetMapping("/mostrarPlatos")
    public String doPlatos(Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/platos";
            List<PlatoDTO> platos = platoService.getAllDishes();
            model.addAttribute("plato", new PlatoUI());
            model.addAttribute("platos", platos);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarPlatos")
    public String doFiltrarPlatos(Model model, HttpSession session, @ModelAttribute PlatoUI platoUI) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(platoUI.estaVacio()){
                dir = "redirect:/admin/mostrarPlatos";
            }else {
                model.addAttribute("platos", platoService.dishesFilter(platoUI.getNombre(), platoUI.getTiempoDePreparacion(), platoUI.getReceta()));
                dir = "admin/platos";
            }
            model.addAttribute("plato", platoUI);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearNuevoPlato")
    public String doCrearNuevoPlato(Model model, HttpSession session, PlatoUI platoUI) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/crearPlato";
            model.addAttribute("platoUI", platoUI);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/anyadirPlato")
    public String doAnyadirPlato(@ModelAttribute PlatoUI platoUI, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(platoUI.getId() == null){
                dir = "redirect:/admin/mostrarPlatos";
                platoService.addDish(platoUI);

            }else{
                dir = "redirect:/admin/mostrarPlatos";
                platoService.editDish(platoUI);
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarPlato")
    public String doEditarPlato(@RequestParam("id") Integer id, Model model, HttpSession session, PlatoUI platoUI) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/crearPlato";
            model.addAttribute("platoUI", platoService.setPlatoUI(id, platoUI));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    @GetMapping("/borrarPlato")
    public String doBorrarPlato(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarPlatos";
            platoService.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }



    @GetMapping("/verComidasAsociadas")
    public String doVerComidasAsociadas(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/mostrarComidas";

            model.addAttribute("comidas", cantidadIngredientePlatoComidaService.getByDish(id));
            model.addAttribute("cantidadPlatoComida", new CantidadPlatoComida());
            model.addAttribute("tiposComida", tipoComidaService.getAll());
            model.addAttribute("plato", platoService.getById(id));
            model.addAttribute("ingredientes", cantidadIngredientePlatoComidaService.getIngredientsByDish(id));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarComidas")
    public String doFiltrarComidas(@RequestParam("id") Integer id, Model model, HttpSession session, @ModelAttribute CantidadPlatoComida cantidadPlatoComida) {
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(cantidadPlatoComida.estaVacio()){
                dir = "redirect:/admin/verComidasAsociadas?id="+id;
            }else{
                PlatoDTO plato = platoService.getById(id);
                if(cantidadPlatoComida.getCantidad() == null){
                    model.addAttribute("comidas", cantidadIngredientePlatoComidaService.dishFilter(plato, cantidadPlatoComida.getNombreCliente(), cantidadPlatoComida.getNombreDietista(), cantidadPlatoComida.getTipoComida()));
                }else{
                    model.addAttribute("comidas", cantidadIngredientePlatoComidaService.dishFilterWithCuantity(plato, cantidadPlatoComida.getNombreCliente(), cantidadPlatoComida.getNombreDietista(), cantidadPlatoComida.getTipoComida(), cantidadPlatoComida.getCantidad()));
                }
                model.addAttribute("cantidadPlatoComida", cantidadPlatoComida);
                model.addAttribute("tiposComida", tipoComidaService.getAll());
                model.addAttribute("ingredientes", cantidadIngredientePlatoComidaService.getIngredientsByDish(id));
                model.addAttribute("plato", plato);
                dir = "admin/mostrarComidas";
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearNuevaComida")
    public String doCrearNuevaComida(@RequestParam("idPlato") Integer idPlato, Model model, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/crearComida";
            AsignacionPlatoComida asignacionPlatoComida = new AsignacionPlatoComida();
            asignacionPlatoComida.setIdPlato(idPlato);

            model.addAttribute("asignacionPlatoComida",asignacionPlatoComida);
            model.addAttribute("clientes", userService.getAllCustomers());
            model.addAttribute("dietistas", userService.getAllDietistas());
            model.addAttribute("tiposComida", tipoComidaService.getAll());
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarComida")
    public String doCrearNuevaComida(@RequestParam("idPlato") Integer idPlato, @RequestParam("idComida") Integer idComida, Model model, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/crearComida";
            AsignacionPlatoComida asignacionPlatoComida = new AsignacionPlatoComida();
            model.addAttribute("asignacionPlatoComida", cantidadIngredientePlatoComidaService.setAsignacionPlatoComida( asignacionPlatoComida, idComida, idPlato));
            model.addAttribute("clientes", userService.getAllCustomers());
            model.addAttribute("dietistas", userService.getAllDietistas());
            model.addAttribute("tiposComida", tipoComidaService.getAll());
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarComida")
    public String doGuardarComida(@ModelAttribute AsignacionPlatoComida asignacionPlatoComida, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(asignacionPlatoComida.getIdComida() == null){
                dir = "redirect:/admin/verComidasAsociadas?id="+asignacionPlatoComida.getIdPlato();
                cantidadIngredientePlatoComidaService.saveFood(asignacionPlatoComida);

            }else{
                dir = "redirect:/admin/verComidasAsociadas?id="+asignacionPlatoComida.getIdPlato();
                cantidadIngredientePlatoComidaService.editFood(asignacionPlatoComida);
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarComida")
    public String doBorrarComida(@RequestParam("idPlato") Integer idPlato, @RequestParam("idComida") Integer idComida, HttpSession session){
        String dir;
        UserRolDTO rol = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/verComidasAsociadas?id="+idPlato;
            cantidadIngredientePlatoComidaService.deleteById(idComida);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    private LocalDate convertirStringALocalDate(String fechaStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(fechaStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

}