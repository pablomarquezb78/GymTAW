package es.uma.controller;

import es.uma.dao.*;
import es.uma.entity.*;
import es.uma.ui.*;
import org.antlr.v4.runtime.misc.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@Controller
@RequestMapping("/dietista")
public class DietistaController extends BaseController{

    private final UserRepository userRepository;
    private final TipoComidaRepository tipoComidaRepository;
    private final DiaDietaRepository diaDietaRepository;
    private final ComidaRepository comidaRepository;
    private final CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;
    private final PlatosRepository platosRepository;
    private final TipoCantidadRepository tipoCantidadRepository;
    private final AsignacionPlatoIngredienteDietistacreadorRepositoy asignacionPlatoIngredienteDietistacreadorRepositoy;
    private final IngredienteRepository ingredienteRepository;

    public DietistaController(UserRepository userRepository, TipoComidaRepository tipoComidaRepository, DiaDietaRepository diaDietaRepository, ComidaRepository comidaRepository, CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository, PlatosRepository platosRepository, TipoCantidadRepository tipoCantidadRepository, AsignacionPlatoIngredienteDietistacreadorRepositoy asignacionPlatoIngredienteDietistacreadorRepositoy, IngredienteRepository ingredienteRepository) {
        super();
        this.userRepository = userRepository;
        this.tipoComidaRepository = tipoComidaRepository;
        this.diaDietaRepository = diaDietaRepository;
        this.comidaRepository = comidaRepository;
        this.cantidadIngredientePlatoComidaRepository = cantidadIngredientePlatoComidaRepository;
        this.platosRepository = platosRepository;
        this.tipoCantidadRepository = tipoCantidadRepository;
        this.asignacionPlatoIngredienteDietistacreadorRepositoy = asignacionPlatoIngredienteDietistacreadorRepositoy;
        this.ingredienteRepository = ingredienteRepository;
    }

    @GetMapping("/mostrarPerfil")
    public String doLoadProfile(HttpSession session,
                             Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            if(session.getAttribute("platoCreando") != null) { session.removeAttribute("platoCreando"); }
            User dietista = (User) session.getAttribute("user");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User dietistaUser = (User) session.getAttribute("user");
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User dietistaUser = (User) session.getAttribute("user");
            dietistaUser.setNombre(dietista.getNombre());
            dietistaUser.setApellidos(dietista.getApellidos());
            dietistaUser.setAltura(dietista.getAltura());
            dietistaUser.setPeso(dietista.getPeso());
            dietistaUser.setDescripcionPersonal(dietista.getDescripcionPersonal());
            userRepository.save(dietistaUser);

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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            if(session.getAttribute("platoCreando") != null) { session.removeAttribute("platoCreando"); }
            User dietista = (User) session.getAttribute("user");
            List<User> clientes = userRepository.clientesAsociadosConDietista(dietista);
            model.addAttribute("clientes", clientes);
            dir = "dietista/dietista_clientesAsociados";
        } else {
            dir = "redirect:/";
        }
        return dir;
    }

    //ToDo: Hacer el cargado de platos correspondiente a la fecha con un metodo.
    //Puede ser un Map que la clave sea DiaXComidaX y los values sean las listas de Plato
        private Pair<List<LocalDate>,Map<String, List<Plato>>> obtenerTablaComidas(User cliente, User dietista, DiaComida diaComida)
    {

        Map<String, List<Plato>> dataMap = new TreeMap<>();
        List<LocalDate> listaFechas = new ArrayList<>();
        List<TipoComida> tiposComida = tipoComidaRepository.findAll();

        for (int i = 1; i<=7; ++i) //Sabemos que vamos a mostrar los dias de 1 a los 7 siguientes
        {
            LocalDate fecha;
            boolean leapYear = Year.isLeap(diaComida.getYear());
            if(diaComida.getDay() + i - 1 <= Month.of(diaComida.getMonth()).length(leapYear)) //Caso normal en el que sumar dias no supone cambiar de mes
            {
                fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay() + i - 1);
            } else if (diaComida.getMonth() < 12) //Caso en el que sumar dias cambia de mes
            {
                int dayOfMonth = (diaComida.getDay() + i - 1) % Month.of(diaComida.getMonth()).length(leapYear);
                fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth() + 1 , dayOfMonth);
            } else //Caso en el que sumar dias implica cambiar de año
            {
                int dayOfMonth = (diaComida.getDay() + i - 1) % Month.of(diaComida.getMonth()).length(leapYear);
                fecha = LocalDate.of(diaComida.getYear() + 1, 1, dayOfMonth);
            }
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha); //Obtenemos el dia de la dieta (columna de la tabla)
            listaFechas.add(fecha);
            List<Comida> comidasDelDia = comidaRepository.findByDiaDieta(diaDieta);
            int j = 1; //Sabemos que alojamos 5 tipos de comidas al dia
            for(TipoComida tipoComida : tiposComida) //Recorremos cada fila de la columna
            {
                List<Plato> listaPlatosComida = new ArrayList<>();
                Comida comida = null;
                for(Comida c : comidasDelDia) //En caso de que haya alguna comida asignada a ese tramo de comida lo guardamos
                {
                    if(c.getTipoComida().equals(tipoComida)) comida = c;
                }
                if (comida != null)
                {
                    //Como sabemos que esa comida existe sacamos los platos a mostrar
                    listaPlatosComida = cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida);
                }
                String key = "Dia" + i + "Comida" + j;
                //Si la lista de platos esta empty se muestra un mensaje en la tabla
                //Si la lista tiene datos se muestran los datos
                dataMap.put(key, listaPlatosComida);
                ++j;
            }
        }
        Pair<List<LocalDate>,Map<String, List<Plato>>> par = new Pair<>(listaFechas, dataMap);
        return par;
    }

    @GetMapping("/cliente")
    public String doShowCliente(@RequestParam("id") Integer clienteId, HttpSession session,
                                 Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = userRepository.findById(clienteId).orElse(null);
            session.setAttribute("clienteSeleccionado", cliente);
            User dietista = (User) session.getAttribute("user");
            List<TipoComida> tiposDeComida = tipoComidaRepository.findAll();
            DiaComida diaComida = new DiaComida();
            diaComida.setYear(Year.now().getValue());
            diaComida.setMonth(YearMonth.now().getMonth().getValue());
            diaComida.setDay(MonthDay.now().getDayOfMonth());
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            session.setAttribute("diaDieta", diaDieta);
            Pair<List<LocalDate>,Map<String, List<Plato>>> par = obtenerTablaComidas(cliente, dietista, diaComida);
            List<LocalDate> listaFechas = par.a;
            Map<String, List<Plato>> tablaComidas = par.b;
            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            List<TipoComida> tiposDeComida = tipoComidaRepository.findAll();
            Pair<List<LocalDate>,Map<String, List<Plato>>> par = obtenerTablaComidas(cliente, dietista, diaComida);
            List<LocalDate> listaFechas = par.a;
            Map<String, List<Plato>> tablaComidas = par.b;
            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            DiaComida diaComida = (DiaComida) session.getAttribute("diaComida");
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            List<TipoComida> tiposDeComida = tipoComidaRepository.findAll();
            Pair<List<LocalDate>,Map<String, List<Plato>>> par = obtenerTablaComidas(cliente, dietista, diaComida);
            List<LocalDate> listaFechas = par.a;
            Map<String, List<Plato>> tablaComidas = par.b;
            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            session.setAttribute("diaComida", diaComida);
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay());
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            if(diaDieta == null)
            {
                diaDieta = new DiaDieta();
                diaDieta.setCliente(cliente);
                diaDieta.setDietista(dietista);
                diaDieta.setFecha(fecha);
                diaDietaRepository.save(diaDieta);
            }
            TipoComida selectedComida = diaComida.getTipoComida();
            List<Plato> platosDisponibles = platosRepository.getPlatosLinkedToDietista(dietista);

            session.setAttribute("fecha", fecha);
            session.setAttribute("selectedComida", selectedComida);

            ComidaUI comidaUI = new ComidaUI();
            comidaUI.setPlatoExistente(false);
            ArrayList<Plato> platosComida = new ArrayList<>();
            List<Comida> comida = comidaRepository.findByDiaAndTipoComido(diaDieta, selectedComida);
            if(!comida.isEmpty()) {
                platosComida.addAll(cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida.getFirst()));
            } else {
                Comida comidaAdd = new Comida();
                comidaAdd.setTipoComida(selectedComida);
                comidaAdd.setDiaDieta(diaDieta);
                comidaRepository.save(comidaAdd);
            }
            comidaUI.setListaPlatosComida(platosComida);
            ArrayList<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado = new ArrayList<>();
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(listaCantidadIngredientesPlatoSeleccionado);
            comidaUI.setSelectedPlato(null);

            model.addAttribute("cliente", cliente);
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

    @PostMapping("/mostrarPlatoComida")
    public String doShowPlatoComidaCliente(@ModelAttribute("comidaUI") ComidaUI comidaUI, HttpSession session,
                                      Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            TipoComida selectedComida = (TipoComida) session.getAttribute("selectedComida");
            List<Plato> platosDisponibles = platosRepository.getPlatosLinkedToDietista(dietista);

            comidaUI.setPlatoExistente(false);
            List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, selectedComida);
            List<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(comidaUI.getSelectedPlato(), comidaActual.getFirst());
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(listaCantidadIngredientesPlatoSeleccionado);

            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            TipoComida selectedComida = (TipoComida) session.getAttribute("selectedComida");
            List<Plato> platosDisponibles = platosRepository.getPlatosLinkedToDietista(dietista);

            ComidaUI comidaUI = new ComidaUI();
            comidaUI.setPlatoExistente(false);
            ArrayList<Plato> platosComida = new ArrayList<>();
            List<Comida> comida = comidaRepository.findByDiaAndTipoComido(diaDieta, selectedComida);
            platosComida.addAll(cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida.getFirst()));
            comidaUI.setListaPlatosComida(platosComida);
            ArrayList<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado = new ArrayList<>();
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(listaCantidadIngredientesPlatoSeleccionado);
            comidaUI.setSelectedPlato(null);

            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            TipoComida selectedComida = (TipoComida) session.getAttribute("selectedComida");
            List<Plato> platosDisponibles = platosRepository.getPlatosLinkedToDietista(dietista);

            List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, selectedComida);
            Plato addingPlato = comidaUI.getAddingPlato();
            List<Plato> platosComida = comidaUI.getListaPlatosComida();
            if(!platosComida.contains(addingPlato))
            {
                platosComida.add(addingPlato);
                comidaUI.setListaPlatosComida(platosComida);

                List<Ingrediente> ingredientesPlato = platosRepository.getIngredientesLinkedToPlato(addingPlato);
                for (Ingrediente i : ingredientesPlato)
                {
                    CantidadIngredientePlatoComida cantidadIngredientePlatoComida = new CantidadIngredientePlatoComida();
                    cantidadIngredientePlatoComida.setPlato(addingPlato);
                    cantidadIngredientePlatoComida.setComida(comidaActual.getFirst());
                    cantidadIngredientePlatoComida.setIngrediente(i);
                    cantidadIngredientePlatoComida.setCantidad(0);
                    List<TipoCantidad> tipoCantidadList = tipoCantidadRepository.findAll();
                    cantidadIngredientePlatoComida.setTipoCantidad(tipoCantidadList.getFirst());
                    cantidadIngredientePlatoComidaRepository.save(cantidadIngredientePlatoComida);
                }

                comidaUI.setSelectedPlato(addingPlato);
            } else {
                comidaUI.setPlatoExistente(true);
                comidaUI.setSelectedPlato(null);
            }
            List<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(comidaUI.getSelectedPlato(), comidaActual.getFirst());
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(listaCantidadIngredientesPlatoSeleccionado);


            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            TipoComida selectedComida = (TipoComida) session.getAttribute("selectedComida");
            List<Plato> platosDisponibles = platosRepository.getPlatosLinkedToDietista(dietista);

            List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, selectedComida);
            Plato selectedPlato = comidaUI.getSelectedPlato();
            List<Plato> platosComida = comidaUI.getListaPlatosComida();
            List<Plato> platosRemove = new ArrayList<>();
            for(Plato p : platosComida)
            {
                if(p.equals(selectedPlato))
                {
                    platosRemove.add(p);
                }
            }
            platosComida.removeAll(platosRemove);
            comidaUI.setListaPlatosComida(platosComida);

            List<CantidadIngredientePlatoComida> cantidadesIngredientePlatoComida = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(selectedPlato, comidaActual.getFirst());
            for (CantidadIngredientePlatoComida c : cantidadesIngredientePlatoComida)
            {
                cantidadIngredientePlatoComidaRepository.delete(c);
            }

            comidaUI.setPlatoExistente(false);
            comidaUI.setSelectedPlato(null);
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(new ArrayList<>());


            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            session.setAttribute("comidaUI", comidaUI);
            IngredienteImplementandoUI ingredienteImplementandoUI = new IngredienteImplementandoUI();
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

    @GetMapping("/editarCantidadIngrediente")
    public String doLoadEditIngrediente(@RequestParam("cantidadId") Integer cantidadId, HttpSession session,
                                       Model model) {
        String dir;
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            CantidadIngredientePlatoComida c = cantidadIngredientePlatoComidaRepository.findById(cantidadId).orElse(null);
            IngredienteImplementandoUI ingredienteImplementandoUI = new IngredienteImplementandoUI();
            ingredienteImplementandoUI.setIngrediente(c.getIngrediente());
            ingredienteImplementandoUI.setCantidad(c.getCantidad());
            ingredienteImplementandoUI.setTipoCantidad(c.getTipoCantidad());
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {

            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            TipoComida selectedComida = (TipoComida) session.getAttribute("selectedComida");
            List<Plato> platosDisponibles = platosRepository.getPlatosLinkedToDietista(dietista);

            ComidaUI comidaUI = (ComidaUI) session.getAttribute("comidaUI");
            CantidadIngredientePlatoComida c = cantidadIngredientePlatoComidaRepository.findById(cantidadId).orElse(null);
            Plato platoActual = c.getPlato();
            Comida comidaActual = c.getComida();
            cantidadIngredientePlatoComidaRepository.delete(c);


            List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComidaList = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(platoActual, comidaActual);
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(cantidadIngredientePlatoComidaList);
            comidaUI.setPlatoExistente(false);
            //comidaUI.setSelectedPlato(null);


            model.addAttribute("cliente", cliente);
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
        UserRol rol = (UserRol) session.getAttribute("rol");
        if (estaAutenticado(session) && esDietista(rol))
        {
            User cliente = (User) session.getAttribute("clienteSeleccionado");
            User dietista = (User) session.getAttribute("user");
            LocalDate fecha = (LocalDate) session.getAttribute("fecha");
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
            TipoComida selectedComida = (TipoComida) session.getAttribute("selectedComida");
            List<Plato> platosDisponibles = platosRepository.getPlatosLinkedToDietista(dietista);
            List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, selectedComida);

            ComidaUI comidaUI = (ComidaUI) session.getAttribute("comidaUI");
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
                List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComidaList = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(comidaUI.getSelectedPlato(), comidaActual.getFirst());
                //cantidadIngredientePlatoComidaList.remove(ingredienteImplementandoUI.getIngrediente());
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
            //comidaUI.setSelectedPlato(null);


            model.addAttribute("cliente", cliente);
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
            List<Plato> listaPlatos = cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida);
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
            List<Plato> listaPlatos = cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida);
            List<CantidadIngredientePlatoComida> listaCantidades = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(feedback.getPlatoMostrando(), comida);
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
}
