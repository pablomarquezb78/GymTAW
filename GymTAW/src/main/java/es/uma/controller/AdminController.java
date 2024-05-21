package es.uma.controller;

import es.uma.dao.*;
import es.uma.entity.*;
import es.uma.ui.EjercicioUI;
import es.uma.ui.Implementacion;
import es.uma.ui.PlatoUI;
import es.uma.ui.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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

    @GetMapping("/")
    public String doWelcome(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            model.addAttribute("clientes", this.userRepository.listarClientes().size());
            model.addAttribute("entrenadores", this.userRepository.listarEntrenadores().size());
            model.addAttribute("dietistas", this.userRepository.listarDietistas().size());
            model.addAttribute("ejercicios", this.ejercicioRepository.findAll().size());
            model.addAttribute("platos", this.platosRepository.findAll().size());
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
            model.addAttribute("peticiones", this.registroRepository.findAll());
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
            User newUser = new User();
            Registro registro = this.registroRepository.getById(id);
            UserRol newRol = this.rolRepository.getById(registro.getRol());

            newUser.setUsername(registro.getUsername());
            newUser.setNombre(registro.getNombre());
            newUser.setPassword(registro.getPassword());
            newUser.setRol(newRol);
            newUser.setFechaNacimiento(registro.getFechaNacimiento());
            newUser.setTelefono(registro.getTelefono());

            this.userRepository.save(newUser);
            this.registroRepository.deleteById(id);
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
            this.registroRepository.deleteById(id);

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
            model.addAttribute("usuarios", this.userRepository.findAll());
            dir = "admin/usuarios";
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
            List<UserRol> roles = rolRepository.buscarRolesNoAdmin();
            model.addAttribute("roles", roles);
            model.addAttribute("usuario", usuario);
            dir = "admin/crearUsuario";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/anyadirUsuario")
    public String doAnyadirUsuario(@ModelAttribute Usuario usuario, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarUsuarios";
            User nuevoUsuario = new User();
            nuevoUsuario.setUsername(usuario.getUsername());
            nuevoUsuario.setPassword(usuario.getPassword());
            nuevoUsuario.setRol(rolRepository.getById(usuario.getRol()));
            nuevoUsuario.setNombre(usuario.getNombre());
            nuevoUsuario.setPeso(usuario.getPeso());
            nuevoUsuario.setAltura(usuario.getAltura());
            nuevoUsuario.setApellidos(usuario.getApellidos());
            nuevoUsuario.setTelefono(usuario.getTelefono());

            userRepository.save(nuevoUsuario);
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
            User user = userRepository.findById(id).orElse(null);
            usuario.setUsername(user.getUsername());
            usuario.setPassword(user.getPassword());
            usuario.setNombre(user.getNombre());
            usuario.setApellidos(user.getApellidos());
            usuario.setPeso(user.getPeso());
            usuario.setAltura(user.getAltura());
            usuario.setRol(user.getRol().getId());
            usuario.setTelefono(user.getTelefono());
            usuario.setFechaNacimiento(user.getFechaNacimiento());

            List<UserRol> roles = rolRepository.buscarRolesNoAdmin();
            model.addAttribute("roles", roles);
            model.addAttribute("usuario", usuario);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/modificarUsuario")
    public String doModificarUsuario(@ModelAttribute Usuario usuario, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarUsuarios";
            User usuarioAModificar = userRepository.findById(usuario.getId()).orElse(null);
            usuarioAModificar.setUsername(usuario.getUsername());
            usuarioAModificar.setPassword(usuario.getPassword());
            usuarioAModificar.setRol(rolRepository.getById(usuario.getRol()));
            usuarioAModificar.setNombre(usuario.getNombre());
            usuarioAModificar.setPeso(usuario.getPeso());
            usuarioAModificar.setAltura(usuario.getAltura());
            usuarioAModificar.setApellidos(usuario.getApellidos());
            usuarioAModificar.setTelefono(usuario.getTelefono());

            userRepository.save(usuarioAModificar);
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
            this.userRepository.deleteById(id);

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
            List<User> clientes = this.userRepository.listarClientes();
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


    @GetMapping("/mostrarEjercicios")
    public String doEjercicios(Model model, HttpSession session) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/ejercicios";
            List<Ejercicio> ejercicios = this.ejercicioRepository.findAll();
            model.addAttribute("ejercicios", ejercicios);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearNuevoEjercicio")
    public String doCrearNuevoEjercicio(Model model, HttpSession session, EjercicioUI ejercicioUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "crearEjercicio";
            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            model.addAttribute("tipos", tipos);
            model.addAttribute("ejercicioUI", ejercicioUI);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/anyadirEjercicio")
    public String doAnyadirEjercicio(@ModelAttribute EjercicioUI ejercicioUI, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarEjercicios";
            Ejercicio nuevoEjercicio = new Ejercicio();
            nuevoEjercicio.setNombre(ejercicioUI.getNombre());
            nuevoEjercicio.setTipo(tipoEjercicioRepository.findById(ejercicioUI.getIdTipo()).orElse(null));
            nuevoEjercicio.setEnlaceVideo(ejercicioUI.getEnlaceVideo());
            nuevoEjercicio.setDescripcion(ejercicioUI.getDescripcion());

            ejercicioRepository.save(nuevoEjercicio);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarEjercicio")
    public String doEditarEjercicio(@RequestParam("id") Integer id, Model model, HttpSession session, EjercicioUI ejercicioUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "crearEjercicio";
            List<TipoEjercicio> tipos = tipoEjercicioRepository.findAll();
            Ejercicio ejercicio = ejercicioRepository.findById(id).orElse(null);
            ejercicioUI.setNombre(ejercicio.getNombre());
            ejercicioUI.setIdTipo(ejercicio.getTipo().getId());
            ejercicioUI.setEnlaceVideo(ejercicio.getEnlaceVideo());
            ejercicioUI.setDescripcion(ejercicio.getDescripcion());
            model.addAttribute("tipos", tipos);
            model.addAttribute("ejercicioUI", ejercicioUI);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/modificarEjercicio")
    public String doModificarEjercicio(HttpSession session, EjercicioUI ejercicioUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarEjercicios";
            Ejercicio ejercicio = ejercicioRepository.findById(ejercicioUI.getId()).orElse(null);
            ejercicio.setNombre(ejercicioUI.getNombre());
            ejercicio.setTipo(tipoEjercicioRepository.findById(ejercicioUI.getIdTipo()).orElse(null));
            ejercicio.setEnlaceVideo(ejercicioUI.getEnlaceVideo());
            ejercicio.setDescripcion(ejercicioUI.getDescripcion());

            ejercicioRepository.save(ejercicio);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/borrarEjercicio")
    public String doBorrarEjercicio(@RequestParam("id") Integer id, HttpSession session){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarEjercicios";
            this.ejercicioRepository.deleteById(id);

        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/verRutinasAsociadas")
    public String doRutinasAsociadas(@RequestParam("id") Integer id, HttpSession session, Model model){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "admin/mostrarRutinas";
            Ejercicio ejercicio = ejercicioRepository.findById(id).orElse(null);
            List<ImplementacionEjercicioRutina> rutinas = implementacionEjercicioRutinaRepository.buscarPorEjercicio(ejercicio);
            model.addAttribute("rutinas", rutinas);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearImplementacion")
    public String doCrearImplementacion( HttpSession session, Model model, Implementacion implementacion){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "crearImplementacion";
            model.addAttribute("ejercicios", ejercicioRepository.findAll());
            model.addAttribute("implementacion", implementacion);
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarImplementacion")
    public String doEditarImplementacion(@RequestParam("id") Integer id, HttpSession session, Model model, Implementacion implementacion){
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "crearImplementacion";
            ImplementacionEjercicioRutina i = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
            implementacion.setPeso(i.getPeso());
            model.addAttribute("ejercicios", ejercicioRepository.findAll());
            model.addAttribute("implementacion", implementacion);
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
            model.addAttribute("platos", platos);
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
            dir = "crearPlato";
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
            dir = "redirect:/admin/mostrarPlatos";
            Plato nuevoPlato = new Plato();
            nuevoPlato.setNombre(platoUI.getNombre());
            nuevoPlato.setTiempoDePreparacion(platoUI.getTiempoDePreparacion());
            nuevoPlato.setEnlaceReceta(platoUI.getEnlaceReceta());
            nuevoPlato.setReceta(platoUI.getReceta());

            platosRepository.save(nuevoPlato);
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
            dir = "crearPlato";
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

    @PostMapping("/modificarPlato")
    public String doModificarPlato(HttpSession session, PlatoUI platoUI) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esAdmin(rol)) {
            dir = "redirect:/admin/mostrarPlatos";
            Plato plato = platosRepository.findById(platoUI.getId()).orElse(null);
            plato.setNombre(platoUI.getNombre());
            plato.setEnlaceReceta(platoUI.getEnlaceReceta());
            plato.setReceta(platoUI.getReceta());
            plato.setTiempoDePreparacion(platoUI.getTiempoDePreparacion());

            platosRepository.save(plato);
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
            model.addAttribute("ingredientes", ingredientes);
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
}