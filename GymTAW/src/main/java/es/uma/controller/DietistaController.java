package es.uma.controller;

import es.uma.dao.*;
import es.uma.dto.*;
import es.uma.entity.*;
import es.uma.service.*;
import es.uma.ui.*;
import org.antlr.v4.runtime.misc.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@Controller
@RequestMapping("/dietista")
public class DietistaController extends BaseController{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TipoComidaRepository tipoComidaRepository;
    @Autowired
    private DiaDietaRepository diaDietaRepository;
    @Autowired
    private ComidaRepository comidaRepository;
    @Autowired
    private CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;
    @Autowired
    private PlatosRepository platosRepository;
    @Autowired
    private TipoCantidadRepository tipoCantidadRepository;
    @Autowired
    private AsignacionPlatoIngredienteDietistacreadorRepositoy asignacionPlatoIngredienteDietistacreadorRepositoy;
    @Autowired
    private IngredienteRepository ingredienteRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DiaDietaService diaDietaService;
    @Autowired
    private CantidadIngredientePlatoComidaService cantidadIngredientePlatoComidaService;
    @Autowired
    private ComidaService comidaService;
    @Autowired
    private TipoComidaService tipoComidaService;
    @Autowired
    private PlatoService platoService;
    @Autowired
    private IngredienteService ingredienteService;
    @Autowired
    private TipoCantidadService tipoCantidadService;

    @GetMapping("/mostrarPerfil")
    public String doLoadProfile(HttpSession session,
                             Model model) {
        String dir;
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            if(session.getAttribute("platoCreando") != null) { session.removeAttribute("platoCreando"); }
            if(session.getAttribute("clienteSeleccionado") != null) { session.removeAttribute("clienteSeleccionado"); }
            if(session.getAttribute("diaDieta") != null) { session.removeAttribute("diaDieta"); }
            if(session.getAttribute("diaComida") != null) { session.removeAttribute("diaComida"); }
            if(session.getAttribute("fecha") != null) { session.removeAttribute("fecha"); }
            if(session.getAttribute("selectedComida") != null) { session.removeAttribute("selectedComida"); }
            if(session.getAttribute("comidaUI") != null) { session.removeAttribute("comidaUI"); }
            UserDTO dietista = userDTO;
            model.addAttribute("dietista", dietista);
            dir = "dietista/dietista_perfil";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarPerfil")
    public String doEditProfile(HttpSession session,
                                Model model) {
        String dir;
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO dietistaUser = (UserDTO) session.getAttribute("user");
            Usuario dietista = new Usuario();
            dietista.setNombre(dietistaUser.getNombre());
            dietista.setApellidos(dietistaUser.getApellidos());
            dietista.setAltura(dietistaUser.getAltura());
            dietista.setFechaNacimiento(dietistaUser.getFechaNacimiento().toString());
            dietista.setPeso(dietistaUser.getPeso());
            dietista.setDescripcionPersonal(dietistaUser.getDescripcionPersonal());
            model.addAttribute("dietista", dietista);

            dir = "dietista/dietista_editarPerfil";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarPerfil")
    public String doSaveProfile(@ModelAttribute("dietista") Usuario dietista, HttpSession session,
                                Model model) {
        String dir;
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO userOutput = userService.guardarDietistaPerfilEdit(userDTO, dietista);
            session.setAttribute("user", userOutput);

            dir = "redirect:/dietista/mostrarPerfil";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/mostrarClientes")
    public String doLoadClientes(HttpSession session,
                                Model model) {
        String dir;
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            if(session.getAttribute("platoCreando") != null) { session.removeAttribute("platoCreando"); }
            if(session.getAttribute("clienteSeleccionado") != null) { session.removeAttribute("clienteSeleccionado"); }
            if(session.getAttribute("diaDieta") != null) { session.removeAttribute("diaDieta"); }
            if(session.getAttribute("diaComida") != null) { session.removeAttribute("diaComida"); }
            if(session.getAttribute("fecha") != null) { session.removeAttribute("fecha"); }
            if(session.getAttribute("selectedComida") != null) { session.removeAttribute("selectedComida"); }
            if(session.getAttribute("comidaUI") != null) { session.removeAttribute("comidaUI"); }

            UserDTO dietistaDTO = userDTO;
            List<UserDTO> clientes = userService.getClientesAsociadosAlDietista(dietistaDTO);
            model.addAttribute("clientes", clientes);
            dir = "dietista/dietista_clientesAsociados";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/cliente")
    public String doShowCliente(@RequestParam("id") Integer clienteId, HttpSession session,
                                 Model model) {
        String dir;
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            if(session.getAttribute("clienteSeleccionado") != null) { session.removeAttribute("clienteSeleccionado"); }
            if(session.getAttribute("diaDieta") != null) { session.removeAttribute("diaDieta"); }
            if(session.getAttribute("diaComida") != null) { session.removeAttribute("diaComida"); }
            if(session.getAttribute("fecha") != null) { session.removeAttribute("fecha"); }
            if(session.getAttribute("selectedComida") != null) { session.removeAttribute("selectedComida"); }
            if(session.getAttribute("comidaUI") != null) { session.removeAttribute("comidaUI"); }

            UserDTO clienteDTO = userService.getById(clienteId);
            session.setAttribute("clienteSeleccionado", clienteDTO);

            UserDTO dietistaDTO = userDTO;

            List<TipoComidaDTO> tiposDeComida = tipoComidaService.getAll();
            DiaComida diaComida = new DiaComida();
            diaComida.setYear(Year.now().getValue());
            diaComida.setMonth(YearMonth.now().getMonth().getValue());
            diaComida.setDay(MonthDay.now().getDayOfMonth());
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDietaDTO diaDieta = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            session.setAttribute("diaDieta", diaDieta);

            Pair<List<LocalDate>,Map<String, List<PlatoDTO>>> par = comidaService.obtenerTablaComidas(clienteDTO, dietistaDTO, diaComida);
            List<LocalDate> listaFechas = par.a;
            Map<String, List<PlatoDTO>> tablaComidas = par.b;
            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("tiposDeComida", tiposDeComida);
            model.addAttribute("diaComida", diaComida);
            model.addAttribute("listaFechas", listaFechas);
            model.addAttribute("tablaComidas", tablaComidas);
            dir = "dietista/dietista_mostrarCliente";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/setFechaCliente")
    public String doShowClienteAlterDate(@ModelAttribute("diaComida") DiaComida diaComida, HttpSession session,
                                 Model model) {
        String dir;
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = userDTO;
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDietaDTO diaDieta = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            List<TipoComidaDTO> tiposDeComida = tipoComidaService.getAll();
            Pair<List<LocalDate>,Map<String, List<PlatoDTO>>> par = comidaService.obtenerTablaComidas(clienteDTO, dietistaDTO, diaComida);
            List<LocalDate> listaFechas = par.a;
            Map<String, List<PlatoDTO>> tablaComidas = par.b;
            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("tiposDeComida", tiposDeComida);
            session.setAttribute("diaDieta", diaDieta);
            model.addAttribute("listaFechas", listaFechas);
            model.addAttribute("tablaComidas", tablaComidas);
            dir = "dietista/dietista_mostrarCliente";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/showFechaCliente")
    public String doShowClienteAlterDateBack(HttpSession session,
                                         Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            if(session.getAttribute("fecha") != null) { session.removeAttribute("fecha"); }
            if(session.getAttribute("selectedComida") != null) { session.removeAttribute("selectedComida"); }
            if(session.getAttribute("comidaUI") != null) { session.removeAttribute("comidaUI"); }
            DiaComida diaComida = (DiaComida) session.getAttribute("diaComida");
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDietaDTO diaDieta = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            List<TipoComidaDTO> tiposDeComida = tipoComidaService.getAll();
            Pair<List<LocalDate>,Map<String, List<PlatoDTO>>> par = comidaService.obtenerTablaComidas(clienteDTO, dietistaDTO, diaComida);
            List<LocalDate> listaFechas = par.a;
            Map<String, List<PlatoDTO>> tablaComidas = par.b;
            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("tiposDeComida", tiposDeComida);
            session.setAttribute("diaDieta", diaDieta);
            model.addAttribute("diaComida", diaComida);
            model.addAttribute("listaFechas", listaFechas);
            model.addAttribute("tablaComidas", tablaComidas);
            dir = "dietista/dietista_mostrarCliente";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/selectComidaCliente")
    public String doShowComidaCliente(@ModelAttribute("diaComida") DiaComida diaComida, HttpSession session,
                                         Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            if(session.getAttribute("fecha") != null) { session.removeAttribute("fecha"); }
            if(session.getAttribute("selectedComida") != null) { session.removeAttribute("selectedComida"); }
            if(session.getAttribute("comidaUI") != null) { session.removeAttribute("comidaUI"); }
            session.setAttribute("diaComida", diaComida);
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDietaDTO diaDietaDTO = diaDietaService.getDiaDietaByDietistaClienteFechaOrCreateIfNull(dietistaDTO, clienteDTO, fecha);
            TipoComidaDTO selectedComidaDTO = tipoComidaService.getSelectedComidaFromDiaComida(diaComida);
            List<PlatoDTO> platosDisponibles = platoService.getPlatosLinkedToDietista(dietistaDTO);

            session.setAttribute("fecha", fecha);
            session.setAttribute("selectedComida", selectedComidaDTO);

            ComidaUI comidaUI = comidaService.initialSetUpComidaUI(diaDietaDTO, selectedComidaDTO);

            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("fecha", fecha);
            model.addAttribute("selectedComida", selectedComidaDTO);
            model.addAttribute("platosDisponibles", platosDisponibles);
            model.addAttribute("comidaUI", comidaUI);
            session.setAttribute("comidaUI", comidaUI);

            dir = "dietista/dietista_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/mostrarPlatoComida")
    public String doShowPlatoComidaCliente(@ModelAttribute("comidaUI") ComidaUI comidaUI, HttpSession session,
                                      Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDietaDTO diaDietaDTO = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            TipoComidaDTO selectedComida = (TipoComidaDTO) session.getAttribute("selectedComida");
            List<PlatoDTO> platosDisponibles = platoService.getPlatosLinkedToDietista(dietistaDTO);

            comidaUI = comidaService.mostrarPlatoComidaSetUpComidaUI(comidaUI, diaDietaDTO, selectedComida);

            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("fecha", fecha);
            model.addAttribute("selectedComida", selectedComida);
            model.addAttribute("platosDisponibles", platosDisponibles);
            model.addAttribute("comidaUI", comidaUI);
            session.setAttribute("comidaUI", comidaUI);

            dir = "dietista/dietista_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/volverComida")
    public String doShowPlatoComidaClienteBack(HttpSession session,
                                           Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDietaDTO diaDietaDTO = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            TipoComidaDTO selectedComida = (TipoComidaDTO) session.getAttribute("selectedComida");
            List<PlatoDTO> platosDisponibles = platoService.getPlatosLinkedToDietista(dietistaDTO);

            ComidaUI comidaUI = comidaService.initialSetUpComidaUI(diaDietaDTO, selectedComida);

            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("fecha", fecha);
            model.addAttribute("selectedComida", selectedComida);
            model.addAttribute("platosDisponibles", platosDisponibles);
            model.addAttribute("comidaUI", comidaUI);
            session.setAttribute("comidaUI", comidaUI);

            dir = "dietista/dietista_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/addPlatoToPlatoComida")
    public String doAddPlatoToPlatoComidaCliente(@ModelAttribute("comidaUI") ComidaUI comidaUI, HttpSession session,
                                           Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDietaDTO diaDietaDTO = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            TipoComidaDTO selectedComida = (TipoComidaDTO) session.getAttribute("selectedComida");
            List<PlatoDTO> platosDisponibles = platoService.getPlatosLinkedToDietista(dietistaDTO);

            comidaUI = comidaService.addPlatoToPlatoComida(comidaUI, diaDietaDTO, selectedComida);

            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("fecha", fecha);
            model.addAttribute("selectedComida", selectedComida);
            model.addAttribute("platosDisponibles", platosDisponibles);
            model.addAttribute("comidaUI", comidaUI);
            session.setAttribute("comidaUI", comidaUI);

            dir = "dietista/dietista_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/eliminarPlatoComida")
    public String doDeletePlatoFromPlatoComidaCliente(@ModelAttribute("comidaUI") ComidaUI comidaUI, HttpSession session,
                                                 Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDietaDTO diaDietaDTO = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            TipoComidaDTO selectedComida = (TipoComidaDTO) session.getAttribute("selectedComida");
            List<PlatoDTO> platosDisponibles = platoService.getPlatosLinkedToDietista(dietistaDTO);

            comidaUI = comidaService.deletePlatoFromPlatoComida(comidaUI, diaDietaDTO, selectedComida);

            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("fecha", fecha);
            model.addAttribute("selectedComida", selectedComida);
            model.addAttribute("platosDisponibles", platosDisponibles);
            model.addAttribute("comidaUI", comidaUI);
            session.setAttribute("comidaUI", comidaUI);

            dir = "dietista/dietista_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/addIngredientePlatoComida")
    public String doLoadNewIngrediente(@ModelAttribute("comidaUI") ComidaUI comidaUI, HttpSession session,
                                 Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            if(comidaUI.getSelectedPlato() != null)
            {
                session.setAttribute("comidaUI", comidaUI);
                IngredienteImplementandoUI ingredienteImplementandoUI = new IngredienteImplementandoUI();
                List<IngredienteDTO> listaIngredientes = ingredienteService.findAllIngredientes();
                List<TipoCantidadDTO> listaTipoCantidad = tipoCantidadService.getAll();
                model.addAttribute("ingredienteImplementandoUI", ingredienteImplementandoUI);
                model.addAttribute("listaIngredientes", listaIngredientes);
                model.addAttribute("listaTipoCantidad", listaTipoCantidad);

                dir = "dietista/dietista_ingredienteImplementando";
            } else {
                dir = "redirect:/dietista/volverComida";
            }
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarCantidadIngrediente")
    public String doLoadEditIngrediente(@RequestParam("cantidadId") Integer cantidadId, HttpSession session,
                                       Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            IngredienteImplementandoUI ingredienteImplementandoUI = ingredienteService.setUpEditIngredienteFromPlatoComida(cantidadId);
            List<Ingrediente> listaIngredientes = ingredienteRepository.findAll();
            List<TipoCantidad> listaTipoCantidad = tipoCantidadRepository.findAll();
            model.addAttribute("ingredienteImplementandoUI", ingredienteImplementandoUI);
            model.addAttribute("listaIngredientes", listaIngredientes);
            model.addAttribute("listaTipoCantidad", listaTipoCantidad);

            dir = "dietista/dietista_ingredienteImplementando";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/deleteIngrediente")
    public String doDeleteIngrediente(@RequestParam("cantidadId") Integer cantidadId, HttpSession session,
                                        Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            TipoComidaDTO selectedComida = (TipoComidaDTO) session.getAttribute("selectedComida");
            List<PlatoDTO> platosDisponibles = platoService.getPlatosLinkedToDietista(dietistaDTO);

            ComidaUI comidaUI = (ComidaUI) session.getAttribute("comidaUI");
            comidaUI = comidaService.deleteIngredienteFromPlatoComida(comidaUI, cantidadId);

            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("fecha", fecha);
            model.addAttribute("selectedComida", selectedComida);
            model.addAttribute("platosDisponibles", platosDisponibles);
            model.addAttribute("comidaUI", comidaUI);
            session.setAttribute("comidaUI", comidaUI);

            dir = "dietista/dietista_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarIngredienteImplementando")
    public String doSaveIngrediente(@ModelAttribute("ingredienteImplementandoUI") IngredienteImplementandoUI ingredienteImplementandoUI, HttpSession session,
                                       Model model) {
        String dir;
        UserRolDTO userRolDTO = (UserRolDTO) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(userRolDTO))
        {
            UserDTO clienteDTO = (UserDTO) session.getAttribute("clienteSeleccionado");
            UserDTO dietistaDTO = (UserDTO) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDietaDTO diaDietaDTO = diaDietaService.getDiaDietaByDietistaClienteFecha(dietistaDTO, clienteDTO, fecha);
            TipoComidaDTO selectedComida = (TipoComidaDTO) session.getAttribute("selectedComida");
            List<PlatoDTO> platosDisponibles = platoService.getPlatosLinkedToDietista(dietistaDTO);
            List<ComidaDTO> comidaActual = comidaService.getComidasByDiaDietaAndTipoComida(diaDietaDTO, selectedComida);

            ComidaUI comidaUI = (ComidaUI) session.getAttribute("comidaUI");
            //Pasar esto de abajo a Service
            /*
            List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComida = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComidaIngrediente(comidaUI.getSelectedPlato(), comidaActual.getFirst(), ingredienteImplementandoUI.getIngrediente());
            boolean modoEdicion = !cantidadIngredientePlatoComida.isEmpty();
            CantidadIngredientePlatoComida cantidad;
            if(modoEdicion)
            {
                cantidad = cantidadIngredientePlatoComida.getFirst();
                cantidadIngredientePlatoComida.remove(cantidad);
            }
            else
            {
                cantidad = new CantidadIngredientePlatoComida();
                cantidad.setIngrediente(ingredienteImplementandoUI.getIngrediente());
                cantidad.setComida(comidaActual.getFirst());
                cantidad.setPlato(comidaUI.getSelectedPlato());
            }
            cantidad.setCantidad(ingredienteImplementandoUI.getCantidad());
            cantidad.setTipoCantidad(ingredienteImplementandoUI.getTipoCantidad());
            cantidadIngredientePlatoComidaRepository.save(cantidad);

            if(modoEdicion)
            {
                List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComidaList = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(comidaUI.getSelectedPlato().getId(), comidaActual.getFirst().getId());
                comidaUI.setListaCantidadIngredientesPlatoSeleccionado(cantidadIngredientePlatoComidaList);
            }
            else
            {
                List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComidaList = comidaUI.getListaCantidadIngredientesPlatoSeleccionado();
                CantidadIngredientePlatoComida c = cantidadIngredientePlatoComidaRepository.getUltimaCantidadAdded();
                cantidadIngredientePlatoComidaList.add(c);
                comidaUI.setListaCantidadIngredientesPlatoSeleccionado(cantidadIngredientePlatoComidaList);
            }
            comidaUI.setPlatoExistente(false);
            */

            model.addAttribute("cliente", clienteDTO);
            model.addAttribute("fecha", fecha);
            model.addAttribute("selectedComida", selectedComida);
            model.addAttribute("platosDisponibles", platosDisponibles);
            model.addAttribute("comidaUI", comidaUI);
            session.setAttribute("comidaUI", comidaUI);

            dir = "dietista/dietista_comida";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    /*
    @GetMapping("/accederFeedbackComida")
    public String doLoadFeedbackComida(@RequestParam("comidaID") Integer comidaID, HttpSession session,
                                        Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            TipoComida tipoComida = tipoComidaRepository.findById(comidaID).orElse(null);
            session.setAttribute("selectedComida", tipoComida);

            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            List<Comida> listaComidas = comidaRepository.findByDiaAndTipoComido(diaDieta, tipoComida);
            Comida comida = listaComidas.getFirst();
            List<Plato> listaPlatos = cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida.getId());
            FeedbackDietistaMostrarUI feedback = new FeedbackDietistaMostrarUI();
            feedback.setPlatoMostrando(null);
            feedback.setCantidadesIngredientePlatoComida(new ArrayList<>());

            model.addAttribute("diaDieta", diaDieta);
            model.addAttribute("comida", comida);
            model.addAttribute("listaPlatos", listaPlatos);
            model.addAttribute("feedback", feedback);

            dir = "dietista/dietista_feedback";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }
    */

    /*
    @PostMapping("/feedbackComidaSelectedPlato")
    public String doLoadFeedbackComidaConPlato(@ModelAttribute("feedback") FeedbackDietistaMostrarUI feedback, HttpSession session,
                                       Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            TipoComida tipoComida = (TipoComida) session.getAttribute("selectedComida");

            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            List<Comida> listaComidas = comidaRepository.findByDiaAndTipoComido(diaDieta, tipoComida);
            Comida comida = listaComidas.getFirst();
            List<Plato> listaPlatos = cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida.getId());
            List<CantidadIngredientePlatoComida> listaCantidades = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(feedback.getPlatoMostrando().getId(), comida.getId());
            feedback.setCantidadesIngredientePlatoComida(listaCantidades);

            model.addAttribute("diaDieta", diaDieta);
            model.addAttribute("comida", comida);
            model.addAttribute("listaPlatos", listaPlatos);
            model.addAttribute("feedback", feedback);

            dir = "dietista/dietista_feedback";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }
    */
}
