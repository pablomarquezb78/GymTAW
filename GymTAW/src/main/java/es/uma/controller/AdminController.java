package es.uma.controller;

import es.uma.dao.*;
import es.uma.dto.UserDTO;
import es.uma.dto.UserRolDTO;
import es.uma.entity.*;
import es.uma.service.*;
import es.uma.ui.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

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
    private ComidaRepository comidaRepository;
    @Autowired
    private DiaDietaRepository diaDietaRepository;
    @Autowired
    private IngredienteRepository ingredienteRepository;

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

    @GetMapping("/")
    public String doWelcome(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            List<UserRolDTO> roles = userRolService.getAllRoles();
            model.addAttribute("roles", roles);
            if(usuario.estaVacio()){
                dir = "redirect:/admin/mostrarUsuarios";
            }else{
                if(usuario.getRol() == null && usuario.getFechaNacimiento().isEmpty()){
                    model.addAttribute("usuarios", userService.filterUsers(usuario.getNombre(), usuario.getApellidos()));
                }else if (usuario.getRol() == null && !usuario.getFechaNacimiento().isEmpty()){
                    model.addAttribute("usuarios", userService.filterUsersWithDate(usuario.getNombre(), usuario.getApellidos(), convertirStringALocalDate(usuario.getFechaNacimiento())));
                }else{
                    model.addAttribute("usuarios", userService.filterUsersWithRol(usuario.getNombre(), usuario.getApellidos(), convertirStringALocalDate(usuario.getFechaNacimiento()), usuario.getRol()));
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            List<UserRolDTO> roles = userRolService.getAllRoles();
            model.addAttribute("roles", roles);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "/admin/crearUsuario";
            UserDTO user = userService.getById(id);
            usuario =  userService.setUsuario(usuario, user);

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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarUsuarios";
            userService.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/asignarCliente")
    public String doAsignar(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/asignar";
            List<UserDTO> clientes = userService.getAllCustomers();
            model.addAttribute("clientes", clientes);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/asignarEntrenador")
    public String doAsignarEntrenador(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        //Cargar todos los entrenadores que no esten asignados al cliente
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/asignacionEntrenador";
            List<AsignacionClienteEntrenador> ace = asignacionClienteEntrenadorRepository.buscarPorCliente(id);
            List<User> entrenadoresAsociados = new ArrayList<>();
            for(AsignacionClienteEntrenador asignacion : ace){
                entrenadoresAsociados.add(asignacion.getEntrenador());
            }
            List<User> entrenadores = userRepository.entrenadoresNoAsociadosAlCliente(entrenadoresAsociados);
            model.addAttribute("entrenadores", entrenadores);
            model.addAttribute("cliente", userRepository.findById(id).orElse(null));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/anyadirAsignacionEntrenador")
    public String doAnyadirAsignacionEntrenador(@RequestParam("id") Integer id, @RequestParam("idCliente") Integer idCliente, Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/asignarEntrenador?id="+id+"&idCliente="+idCliente;
            AsignacionClienteEntrenador ace = new AsignacionClienteEntrenador();
            ace.setEntrenador(userRepository.findById(id).orElse(null));
            ace.setCliente(userRepository.findById(idCliente).orElse(null));

            asignacionClienteEntrenadorRepository.save(ace);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/asignarDietista")
    public String doAsignarDietista(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/asignacionDietista";
            List<AsignacionClienteDietista> acd = asignacionClienteDietistaRepository.buscarPorCliente(id);
            List<User> dietistasAsociados = new ArrayList<>();
            for(AsignacionClienteDietista asignacion : acd){
                dietistasAsociados.add(asignacion.getDietista());
            }
            List<User> dietistas = userRepository.dietistasNoAsociadosAlCliente(dietistasAsociados);
            model.addAttribute("dietistas", dietistas);
            model.addAttribute("cliente", userRepository.findById(id).orElse(null));
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/anyadirAsignacionDietista")
    public String doAnyadirAsignacionDietista(@RequestParam("id") Integer id, @RequestParam("idCliente") Integer idCliente, Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/asignarDietista?id="+id+"&idCliente="+idCliente;
            AsignacionClienteDietista acd = new AsignacionClienteDietista();
            acd.setDietista(userRepository.findById(id).orElse(null));
            acd.setCliente(userRepository.findById(idCliente).orElse(null));

            asignacionClienteDietistaRepository.save(acd);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/eliminarAsignaciones")
    public String doEliminarAsignaciones(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/eliminarAsignacion";
            List<AsignacionClienteDietista> asignacionesDietista = asignacionClienteDietistaRepository.buscarPorCliente(id);
            List<AsignacionClienteEntrenador> asignacionesEntrenador = asignacionClienteEntrenadorRepository.buscarPorCliente(id);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            AsignacionClienteEntrenador ace = asignacionClienteEntrenadorRepository.findById(id).orElse(null);
            Integer clienteID = ace.getCliente().getId();
            asignacionClienteEntrenadorRepository.delete(ace);
            dir = "redirect:/admin/eliminarAsignaciones?id="+clienteID;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/eliminarAsignacionDietista")
    public String doEliminarAsignacionDietista(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            AsignacionClienteDietista acd = asignacionClienteDietistaRepository.findById(id).orElse(null);
            Integer clienteID = acd.getCliente().getId();
            asignacionClienteDietistaRepository.delete(acd);
            dir = "redirect:/admin/eliminarAsignaciones?id="+clienteID;
        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    @GetMapping("/mostrarPlatos")
    public String doPlatos(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/platos";
            List<Plato> platos = this.platosRepository.findAll();
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(platoUI.estaVacio()){
                dir = "redirect:/admin/mostrarPlatos";
            }else {
                model.addAttribute("platos", this.platosRepository.filtrarPlatos(platoUI.getNombre(), platoUI.getTiempoDePreparacion(), platoUI.getReceta()));
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
        UserRol rol = (UserRol) session.getAttribute("rol");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(platoUI.getId() == null){
                dir = "redirect:/admin/mostrarPlatos";
                Plato nuevoPlato = new Plato();
                nuevoPlato.setNombre(platoUI.getNombre());
                nuevoPlato.setTiempoDePreparacion(platoUI.getTiempoDePreparacion());
                nuevoPlato.setEnlaceReceta(platoUI.getEnlaceReceta());
                nuevoPlato.setReceta(platoUI.getReceta());

                platosRepository.save(nuevoPlato);
            }else{
                dir = "redirect:/admin/mostrarPlatos";
                Plato plato = platosRepository.findById(platoUI.getId()).orElse(null);
                plato.setNombre(platoUI.getNombre());
                plato.setEnlaceReceta(platoUI.getEnlaceReceta());
                plato.setReceta(platoUI.getReceta());
                plato.setTiempoDePreparacion(platoUI.getTiempoDePreparacion());

                platosRepository.save(plato);
            }

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarPlato")
    public String doEditarPlato(@RequestParam("id") Integer id, Model model, HttpSession session, PlatoUI platoUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/crearPlato";
            Plato plato = platosRepository.findById(id).orElse(null);
            platoUI.setNombre(plato.getNombre());
            platoUI.setEnlaceReceta(plato.getEnlaceReceta());
            platoUI.setReceta(plato.getReceta());
            platoUI.setTiempoDePreparacion(plato.getTiempoDePreparacion());

            model.addAttribute("platoUI", platoUI);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }


    @GetMapping("/borrarPlato")
    public String doBorrarPlato(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarPlatos";
            this.platosRepository.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }



    @GetMapping("/verComidasAsociadas")
    public String doVerComidasAsociadas(@RequestParam("id") Integer id, Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/mostrarComidas";
            List<CantidadIngredientePlatoComida> comidas = cantidadIngredientePlatoComidaRepository.buscarPorPlato(id);
            List<Ingrediente> ingredientes = cantidadIngredientePlatoComidaRepository.buscarIngredientesPorPlato(id);
            model.addAttribute("comidas", comidas);
            model.addAttribute("cantidadPlatoComida", new CantidadPlatoComida());
            model.addAttribute("tiposComida", tipoComidaRepository.findAll());
            model.addAttribute("plato", platosRepository.findById(id).orElse(null));
            model.addAttribute("ingredientes", ingredientes);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/filtrarComidas")
    public String doFiltrarComidas(@RequestParam("id") Integer id, Model model, HttpSession session, @ModelAttribute CantidadPlatoComida cantidadPlatoComida) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(cantidadPlatoComida.estaVacio()){
                dir = "redirect:/admin/verComidasAsociadas?id="+id;
            }else{
                Plato plato = platosRepository.findById(id).orElse(null);
                if(cantidadPlatoComida.getCantidad() == null){
                    List<CantidadIngredientePlatoComida> comidas = cantidadIngredientePlatoComidaRepository.filtrarPlatos(plato, cantidadPlatoComida.getNombreCliente(), cantidadPlatoComida.getNombreDietista(), cantidadPlatoComida.getTipoComida());
                    model.addAttribute("comidas", comidas);
                }else{
                    List<CantidadIngredientePlatoComida> comidas = cantidadIngredientePlatoComidaRepository.filtrarPlatosConCantidad(plato, cantidadPlatoComida.getNombreCliente(), cantidadPlatoComida.getNombreDietista(), cantidadPlatoComida.getTipoComida(), cantidadPlatoComida.getCantidad());
                    model.addAttribute("comidas", comidas);

                }
                List<Ingrediente> ingredientes = cantidadIngredientePlatoComidaRepository.buscarIngredientesPorPlato(id);
                model.addAttribute("cantidadPlatoComida", cantidadPlatoComida);
                model.addAttribute("tiposComida", tipoComidaRepository.findAll());
                model.addAttribute("ingredientes", ingredientes);
                model.addAttribute("plato",plato);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/crearComida";
            AsignacionPlatoComida asignacionPlatoComida = new AsignacionPlatoComida();
            asignacionPlatoComida.setIdPlato(idPlato);
            List<User> clientes = userRepository.listarClientes();
            List<User> dietistas = userRepository.listarDietistas();
            model.addAttribute("asignacionPlatoComida",asignacionPlatoComida);
            model.addAttribute("ingredientes",  ingredienteRepository.findAll());
            model.addAttribute("clientes", userRepository.listarClientes());
            model.addAttribute("dietistas", userRepository.listarDietistas());
            model.addAttribute("tiposComida", tipoComidaRepository.findAll());
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarComida")
    public String doCrearNuevaComida(@RequestParam("idPlato") Integer idPlato, @RequestParam("idComida") Integer idComida, Model model, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/crearComida";
            AsignacionPlatoComida asignacionPlatoComida = new AsignacionPlatoComida();
            CantidadIngredientePlatoComida apidc = cantidadIngredientePlatoComidaRepository.findById(idComida).orElse(null);
            asignacionPlatoComida.setIdCliente(apidc.getComida().getDiaDieta().getCliente().getId());
            asignacionPlatoComida.setIdCliente(apidc.getComida().getDiaDieta().getDietista().getId());
            asignacionPlatoComida.setCantidad(apidc.getCantidad());
            asignacionPlatoComida.setTipoComida(apidc.getComida().getTipoComida());
            asignacionPlatoComida.setIdComida(apidc.getId());
            asignacionPlatoComida.setIdPlato(apidc.getPlato().getId());
            asignacionPlatoComida.setFecha(apidc.getComida().getDiaDieta().getFecha().toString());
            asignacionPlatoComida.setIdComida(apidc.getId());
            List<Ingrediente> ingredientes = new ArrayList<>();
            for(Ingrediente ingrediente : cantidadIngredientePlatoComidaRepository.buscarIngredientesPorPlato(idPlato)){
                ingredientes.add(ingrediente);
            }
            asignacionPlatoComida.setIngredientes(ingredientes);

            model.addAttribute("asignacionPlatoComida",asignacionPlatoComida);
            model.addAttribute("clientes", userRepository.listarClientes());
            model.addAttribute("dietistas", userRepository.listarDietistas());
            model.addAttribute("tiposComida", tipoComidaRepository.findAll());
            model.addAttribute("ingredientes",  ingredienteRepository.findAll());
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarComida")
    public String doGuardarComida(@ModelAttribute AsignacionPlatoComida asignacionPlatoComida, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            if(asignacionPlatoComida.getIdComida() == null){
                dir = "redirect:/admin/verComidasAsociadas?id="+asignacionPlatoComida.getIdPlato();
                CantidadIngredientePlatoComida cipc = new CantidadIngredientePlatoComida();
                Plato plato = platosRepository.findById(asignacionPlatoComida.getIdPlato()).orElse(null);
                Comida comida = new Comida();
                DiaDieta diaDieta = new DiaDieta();

                diaDieta.setCliente(userRepository.findById(asignacionPlatoComida.getIdCliente()).orElse(null));
                diaDieta.setDietista(userRepository.findById(asignacionPlatoComida.getIdDietista()).orElse(null));
                diaDieta.setFecha( LocalDate.parse(asignacionPlatoComida.getFecha()));
                diaDietaRepository.save(diaDieta);

                comida.setTipoComida(asignacionPlatoComida.getTipoComida());
                comida.setDiaDieta(diaDieta);
                comidaRepository.save(comida);

                cipc.setPlato(plato);
                cipc.setCantidad(asignacionPlatoComida.getCantidad());
                cipc.setComida(comida);
                cantidadIngredientePlatoComidaRepository.save(cipc);

            }else{
                dir = "redirect:/admin/verComidasAsociadas?id="+asignacionPlatoComida.getIdPlato();
                CantidadIngredientePlatoComida cipc = cantidadIngredientePlatoComidaRepository.getById(asignacionPlatoComida.getIdComida());
                Comida comida = cipc.getComida();
                DiaDieta diaDieta = comida.getDiaDieta();

                diaDieta.setCliente(userRepository.findById(asignacionPlatoComida.getIdCliente()).orElse(null));
                diaDieta.setDietista(userRepository.findById(asignacionPlatoComida.getIdDietista()).orElse(null));
                diaDieta.setFecha( LocalDate.parse(asignacionPlatoComida.getFecha()));
                diaDietaRepository.save(diaDieta);

                comida.setTipoComida(asignacionPlatoComida.getTipoComida());
                comida.setDiaDieta(diaDieta);
                comidaRepository.save(comida);

                cipc.setCantidad(asignacionPlatoComida.getCantidad());
                cipc.setComida(comida);
                cantidadIngredientePlatoComidaRepository.save(cipc);
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarComida")
    public String doBorrarComida(@RequestParam("idPlato") Integer idPlato, @RequestParam("idComida") Integer idComida, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/verComidasAsociadas?id="+idPlato;
            this.cantidadIngredientePlatoComidaRepository.deleteById(idComida);

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