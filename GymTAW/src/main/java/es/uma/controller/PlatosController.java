package es.uma.controller;

import es.uma.dao.AsignacionPlatoIngredienteDietistacreadorRepositoy;
import es.uma.dao.IngredienteRepository;
import es.uma.dao.PlatosRepository;
import es.uma.dto.IngredienteDTO;
import es.uma.dto.PlatoDTO;
import es.uma.dto.UserDTO;
import es.uma.entity.*;
import es.uma.service.IngredienteService;
import es.uma.service.PlatoService;
import es.uma.service.UserService;
import es.uma.ui.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dietista")
public class PlatosController extends BaseController{

    @Autowired
    protected PlatosRepository platosRepository;
    @Autowired
    protected AsignacionPlatoIngredienteDietistacreadorRepositoy asignacionPlatoIngredienteDietistacreadorRepositoy;
    @Autowired
    private IngredienteRepository ingredienteRepository;
    @Autowired
    private PlatoService platoService;
    @Autowired
    private UserService userService;
    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping("/")
    public String doLoadMain(HttpSession session,
                         Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            if(session.getAttribute("platoCreando") != null) { session.removeAttribute("platoCreando"); }
            //Remove resto de session attributes
            if(session.getAttribute("clienteSeleccionado") != null) { session.removeAttribute("clienteSeleccionado"); }
            if(session.getAttribute("diaDieta") != null) { session.removeAttribute("diaDieta"); }
            if(session.getAttribute("diaComida") != null) { session.removeAttribute("diaComida"); }
            if(session.getAttribute("fecha") != null) { session.removeAttribute("fecha"); }
            if(session.getAttribute("selectedComida") != null) { session.removeAttribute("selectedComida"); }
            if(session.getAttribute("comidaUI") != null) { session.removeAttribute("comidaUI"); }
            dir = "redirect:/dietista/platos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/platos")
    public String doLoad(HttpSession session,
                         Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            if(session.getAttribute("platoCreando") != null) { session.removeAttribute("platoCreando"); }
            if(session.getAttribute("clienteSeleccionado") != null) { session.removeAttribute("clienteSeleccionado"); }
            if(session.getAttribute("diaDieta") != null) { session.removeAttribute("diaDieta"); }
            if(session.getAttribute("diaComida") != null) { session.removeAttribute("diaComida"); }
            if(session.getAttribute("fecha") != null) { session.removeAttribute("fecha"); }
            if(session.getAttribute("selectedComida") != null) { session.removeAttribute("selectedComida"); }
            if(session.getAttribute("comidaUI") != null) { session.removeAttribute("comidaUI"); }

            List<PlatoDTO> platosLinkedToDietista = platoService.getPlatosLinkedToDietista(session);
            model.addAttribute("listaPlatos", platosLinkedToDietista);
            dir = "dietista/dietista_platos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/mostrarPlato")
    public String doLoadPlato (@RequestParam(value = "platosDisplay") String platoId
                         , HttpSession session
                         , Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            if(platoId != null){
                PlatoDTO platoDTO = platoService.findById(Integer.valueOf(platoId));
                model.addAttribute("selectedPlato", platoDTO);
                model.addAttribute("listaIngredientes",ingredienteService.getIngredientesLinkedToPlato(platoDTO));
            }

            List<PlatoDTO> platosLinkedToDietista = platoService.getPlatosLinkedToDietista(session);
            model.addAttribute("listaPlatos", platosLinkedToDietista);
            dir = "dietista/dietista_platos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/crearPlato")
    public String openCrearPlato(HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            PlatoDietistaUI platoDietista;
            if(session.getAttribute("platoCreando") != null) { platoDietista = (PlatoDietistaUI) session.getAttribute("platoCreando");}
            else {
                platoDietista = new PlatoDietistaUI();
            }
            model.addAttribute("platoDietista", platoDietista);
            model.addAttribute("ingredientesExistentes", ingredienteService.findAllIngredientes());
            session.setAttribute("platoCreando", platoDietista);
            dir = "dietista/dietista_crearPlato";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/editarPlato")
    public String openEditarPlato(@RequestParam("platoId") Integer platoId, HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            PlatoDietistaUI platoDietistaUI = platoService.prepareEditarPlatoByPlatoDietistaUI(platoId);

            model.addAttribute("platoDietista", platoDietistaUI);
            model.addAttribute("ingredientesExistentes", ingredienteRepository.findAll());
            session.setAttribute("platoCreando", platoDietistaUI);
            dir = "dietista/dietista_crearPlato";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/borrarPlato")
    public String doBorrarPlato(@RequestParam("platoId") Integer platoId, HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            platoService.borrarPlatoByPlatoId(platoId, session);
            dir = "redirect:/dietista/platos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/addIngredienteExistente")
    public String doAddIngredienteExistente(@ModelAttribute("platoDietista") PlatoDietistaUI platoDietistaUI
            , HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            if(platoDietistaUI.getIngredientes() == null) platoDietistaUI.setIngredientes(new ArrayList<>());
            platoDietistaUI = ingredienteService.addIngredienteToPlatoDietistaUI(platoDietistaUI);

            model.addAttribute("platoDietista", platoDietistaUI);
            model.addAttribute("ingredientesExistentes", ingredienteService.findAllIngredientes());
            session.setAttribute("platoCreando", platoDietistaUI);
            dir = "dietista/dietista_crearPlato";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/addNuevoIngrediente")
    public String doAddNuevoIngrediente(HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            IngredienteDTO nuevoIngrediente = new IngredienteDTO();
            model.addAttribute("nuevoIngrediente", nuevoIngrediente);
            dir = "dietista/dietista_crearIngrediente";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarNuevoIngrediente")
    public String doSaveNuevoIngrediente(@ModelAttribute("nuevoIngrediente") IngredienteDTO ingredienteDTO , HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            PlatoDietistaUI platoDietistaUI = platoService.addNewIngredienteToPlatoDietistaUI(ingredienteDTO, session);
            session.setAttribute("platoCreando", platoDietistaUI);
            dir = "redirect:/dietista/crearPlato";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @GetMapping("/eliminarIngrediente")
    public String doEliminarIngredienteExistente(@RequestParam("ingredienteId") Integer ingredienteId
            , HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            PlatoDietistaUI platoDietistaUI = (PlatoDietistaUI) session.getAttribute("platoCreando");
            platoDietistaUI = ingredienteService.eliminarIngredienteToPlatoDietista(ingredienteId, platoDietistaUI);

            session.setAttribute("platoCreando", platoDietistaUI);
            dir = "redirect:/dietista/crearPlato";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarPlato")
    public String doGuardarPlato(@ModelAttribute("platoDietista") PlatoDietistaUI platoDietistaUI, HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            //Crear plato
            if(platoDietistaUI.getId() == null) {
                platoService.crearPlatoByPlatoDietstaUI(platoDietistaUI, session);
            } else { //Editar plato
                platoService.editarPlatoByPlatoDietistaUI(platoDietistaUI, session);
            }
            session.removeAttribute("platoCreando");
            dir = "redirect:/dietista/platos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }
}
