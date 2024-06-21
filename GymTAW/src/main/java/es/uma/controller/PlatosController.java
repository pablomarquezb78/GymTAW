package es.uma.controller;

import es.uma.dao.AsignacionPlatoIngredienteDietistacreadorRepositoy;
import es.uma.dao.IngredienteRepository;
import es.uma.dao.PlatosRepository;
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
            //TODO: Hacer que la session lleve un UserDTO en lugar de user
            UserDTO userDTO = userService.convertEntityToDto((User) session.getAttribute("user")); //Esta linea será eliminada
            //----------------------------------------------------------------------------------------
            List<PlatoDTO> platosLinkedToDietista = platoService.getPlatosLinkedToDietista(userDTO);
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
            //TODO: Hacer que la session lleve un UserDTO en lugar de user
            UserDTO userDTO = userService.convertEntityToDto((User) session.getAttribute("user")); //Esta linea será eliminada
            //----------------------------------------------------------------------------------------
            List<PlatoDTO> platosLinkedToDietista = platoService.getPlatosLinkedToDietista(userDTO);
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
                platoDietista.setIngredientes(new ArrayList<>());
            }
            model.addAttribute("platoDietista", platoDietista);
            model.addAttribute("ingredientesExistentes", ingredienteRepository.findAll());
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
            Plato plato = platosRepository.findById(platoId).orElse(null);
            PlatoDietistaUI platoDietista = new PlatoDietistaUI();

            platoDietista.setId(plato.getId());
            ArrayList<Ingrediente> ingredientes = new ArrayList<>();
            ingredientes.addAll(platosRepository.getIngredientesLinkedToPlato(plato));
            platoDietista.setIngredientes(ingredientes);
            platoDietista.setNombre(plato.getNombre());
            platoDietista.setReceta(plato.getReceta());
            platoDietista.setEnlaceReceta(plato.getEnlaceReceta());
            platoDietista.setTiempoDePreparacion(plato.getTiempoDePreparacion());

            model.addAttribute("platoDietista", platoDietista);
            model.addAttribute("ingredientesExistentes", ingredienteRepository.findAll());
            session.setAttribute("platoCreando", platoDietista);
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
            Plato plato = platosRepository.findById(platoId).orElse(null);

            List<Ingrediente> ingredientes = platosRepository.getIngredientesLinkedToPlato(plato);
            for(Ingrediente ingrediente : ingredientes)
            {
                User dietista = (User) session.getAttribute("user");
                AsignacionPlatoIngredienteDietistaCreador asignacion =
                        asignacionPlatoIngredienteDietistacreadorRepositoy.getAsignacionBy(ingrediente, plato, dietista).getFirst();
                asignacionPlatoIngredienteDietistacreadorRepositoy.delete(asignacion);
            }
            platosRepository.delete(plato);
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
            ArrayList<Ingrediente> listaIngredientesPlato = platoDietistaUI.getIngredientes();
            if(listaIngredientesPlato == null) listaIngredientesPlato = new ArrayList<>();
            listaIngredientesPlato.add(ingredienteRepository.findById(platoDietistaUI.getAddedIngrediente()).orElse(null));
            platoDietistaUI.setIngredientes(listaIngredientesPlato);

            model.addAttribute("platoDietista", platoDietistaUI);
            model.addAttribute("ingredientesExistentes", ingredienteRepository.findAll());
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
            Ingrediente nuevoIngrediente = new Ingrediente();
            model.addAttribute("nuevoIngrediente", nuevoIngrediente);
            dir = "dietista/dietista_crearIngrediente";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarNuevoIngrediente")
    public String doSaveNuevoIngrediente(@ModelAttribute("nuevoIngrediente") Ingrediente ingrediente , HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            Ingrediente ingredienteSave = new Ingrediente();
            ingredienteSave.setNombre(ingrediente.getNombre());
            ingredienteSave.setKilocalorias(ingrediente.getKilocalorias());
            ingredienteSave.setProteinas(ingrediente.getProteinas());
            ingredienteSave.setAzucares(ingrediente.getAzucares());
            ingredienteSave.setGrasas(ingrediente.getGrasas());
            ingredienteSave.setHidratosDeCarbono(ingrediente.getHidratosDeCarbono());
            ingredienteRepository.save(ingredienteSave);
            PlatoDietistaUI plato = (PlatoDietistaUI) session.getAttribute("platoCreando");
            plato.getIngredientes().add(ingredienteRepository.getUltimosIngredientesAdded().getFirst());
            session.setAttribute("platoCreando", plato);
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
            PlatoDietistaUI platoDietista = (PlatoDietistaUI) session.getAttribute("platoCreando");
            Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId).orElse(null);
            for(int i=0; i < platoDietista.getIngredientes().size(); ++i)
            {
                if(platoDietista.getIngredientes().get(i).getId() == ingrediente.getId())
                {
                    platoDietista.getIngredientes().remove(i);
                }
            }

            session.setAttribute("platoCreando", platoDietista);
            dir = "redirect:/dietista/crearPlato";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    @PostMapping("/guardarPlato")
    public String doGuardarPlato(@ModelAttribute("platoDietista") PlatoDietistaUI platoDietista, HttpSession session, Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            //Crear plato
            if(platoDietista.getId() == null) {
                Plato plato = new Plato();
                plato.setNombre(platoDietista.getNombre());
                plato.setTiempoDePreparacion(platoDietista.getTiempoDePreparacion());
                plato.setReceta(platoDietista.getReceta());
                plato.setEnlaceReceta(platoDietista.getEnlaceReceta());
                platosRepository.saveAndFlush(plato);
                if(platoDietista.getIngredientes() != null)
                {
                    for(Ingrediente ingrediente : platoDietista.getIngredientes())
                    {
                        AsignacionPlatoIngredienteDietistaCreador asignacion = new AsignacionPlatoIngredienteDietistaCreador();
                        asignacion.setPlato(platosRepository.getUltimoPlatoAdded());
                        asignacion.setDietista((User) session.getAttribute("user"));
                        asignacion.setIngrediente(ingredienteRepository.findById(ingrediente.getId()).orElse(null));
                        asignacionPlatoIngredienteDietistacreadorRepositoy.saveAndFlush(asignacion);
                    }
                }
            } else { //Editar plato
                Plato plato = platosRepository.findById(platoDietista.getId()).orElse(null);
                plato.setNombre(platoDietista.getNombre());
                plato.setReceta(platoDietista.getReceta());
                plato.setEnlaceReceta(platoDietista.getEnlaceReceta());
                plato.setTiempoDePreparacion(platoDietista.getTiempoDePreparacion());
                platosRepository.save(plato);
                if(platoDietista.getIngredientes() != null)
                {
                    List<Ingrediente> ingredientesPrevios = platosRepository.getIngredientesLinkedToPlato(plato);
                    if(!ingredientesPrevios.equals(platoDietista.getIngredientes()))
                    {
                        for(Ingrediente ingrediente : ingredientesPrevios)
                        {
                            User dietista = (User) session.getAttribute("user");
                            AsignacionPlatoIngredienteDietistaCreador asignacion =
                                    asignacionPlatoIngredienteDietistacreadorRepositoy.getAsignacionBy(ingrediente, plato, dietista).getFirst();
                            asignacionPlatoIngredienteDietistacreadorRepositoy.delete(asignacion);
                        }
                        for(Ingrediente ingrediente : platoDietista.getIngredientes())
                        {
                            AsignacionPlatoIngredienteDietistaCreador asignacion = new AsignacionPlatoIngredienteDietistaCreador();
                            asignacion.setPlato(platosRepository.getUltimoPlatoAdded());
                            asignacion.setDietista((User) session.getAttribute("user"));
                            asignacion.setIngrediente(ingredienteRepository.findById(ingrediente.getId()).orElse(null));
                            asignacionPlatoIngredienteDietistacreadorRepositoy.saveAndFlush(asignacion);
                        }
                    }
                }
            }
            session.removeAttribute("platoCreando");
            dir = "redirect:/dietista/platos";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }
}
