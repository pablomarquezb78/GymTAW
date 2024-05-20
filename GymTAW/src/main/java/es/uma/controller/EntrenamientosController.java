package es.uma.controller;


import es.uma.dao.*;
import es.uma.entity.*;
import es.uma.ui.EjercicioUI;
import es.uma.ui.Implementacion;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/entrenamientos")
public class EntrenamientosController extends BaseController{

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;
    @Autowired
    private EjercicioRepository ejercicioRepository;
    @Autowired
    private RutinaRepository rutinaRepository;
    @Autowired
    private ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    private TipoEjercicioRepository tipoEjercicioRepository;


    @GetMapping("/")
    public String doListar (Model model, HttpSession sesion){
        String strTo = "/crosstrainer/entrenador_listadoclientes";
        UserRol rol = (UserRol) sesion.getAttribute("rol");
        if(estaAutenticado(sesion) == true && esEntrenador(rol) ){
            List<User> lista = userRepository.clientesAsociadosConEntrenador((User) sesion.getAttribute("user"));
            model.addAttribute("lista",lista);
        }else {
            strTo= "redirect:/";
        }

        return strTo;
    }


    @GetMapping("/versemana")
    public String doVerSemanaEntrenamientos (@RequestParam("id") Integer idcliente,Model model, HttpSession session){
        String strTo = "/crosstrainer/entrenador_semana";

        if(!estaAutenticado(session)){
            strTo = "redicrect:/";
        }else{
            User cliente = (User) userRepository.findById(idcliente).orElse(null);
            List<DiaEntrenamiento> dias =  diaEntrenamientoRepository.diasEntrenamientosdeCliente(cliente);
            model.addAttribute("dias",dias);
            model.addAttribute("cliente",cliente);
        }


        return strTo;
    }

    @PostMapping("/borrardia")
    public String doBorrarDiaEntrenamiento (@RequestParam("id") Integer id,HttpSession session){

        DiaEntrenamiento dia = (DiaEntrenamiento) diaEntrenamientoRepository.findById(id).orElse(null);

        String strTo = "redirect:/entrenamientos/versemana?id=" + dia.getCliente().getId();


        if(!estaAutenticado(session)){
            strTo = "redicrect:/";
        }else{
            diaEntrenamientoRepository.deleteById(id);
        }
        return strTo;
    }

    @PostMapping("/creardia")
    public String doCrearDia(@RequestParam("clienteid") Integer clienteid,HttpSession session){
        String strTo = "redirect:/entrenamientos/versemana?id=" + clienteid;
        if(!estaAutenticado(session)){
            strTo = "redirect:/";
        }else{
            User cliente = (User) userRepository.findById(clienteid).orElse(null);
            DiaEntrenamiento dia = new DiaEntrenamiento();
            dia.setFecha(LocalDate.now());
            dia.setCliente(cliente);



            this.diaEntrenamientoRepository.save(dia);
        }

        return strTo;
    }

    @GetMapping("/editardia")
    public String doEditarDia(@RequestParam("iddia")Integer idDia,Model model,HttpSession session){
        String strTo = "/crosstrainer/entrenador_rutina";

        if(!estaAutenticado(session)) {
            strTo = "redirect:/";
        }else{
            DiaEntrenamiento dia = diaEntrenamientoRepository.findById(idDia).orElse(null);

            if(dia.getRutina()==null){
                Rutina r = new Rutina();
                User trainer = (User) session.getAttribute("user");
                r.setEntrenador(trainer);
                r.setFechaCreacion(Instant.now());
                rutinaRepository.save(r);
                dia.setRutina(r);
            }

            int idRutina = dia.getRutina().getId();
            Rutina rutina = rutinaRepository.findById(idRutina).orElse(null);
            List<ImplementacionEjercicioRutina> implementaciones = implementacionEjercicioRutinaRepository.encontrarImplementacionesPorRutinas(rutina);
            model.addAttribute("implementaciones",implementaciones);
            model.addAttribute("iddia",idDia);
        }


        return strTo;
    }

    private void asignarImplementacionUI(Implementacion implementacion, ImplementacionEjercicioRutina imp){
        implementacion.setId(imp.getId());
        implementacion.setEjercicio(imp.getEjercicio());
        implementacion.setRutina(imp.getRutina());
        implementacion.setSets(imp.getSets());
        implementacion.setRepeticiones(imp.getRepeticiones());
        implementacion.setPeso(imp.getPeso());
        implementacion.setTiempo(imp.getTiempo());
        implementacion.setKilocalorias(imp.getKilocalorias());
        implementacion.setMetros(imp.getMetros());
        implementacion.setSeguimientoSetsDone(imp.getSeguimientoSetsDone());
        implementacion.setSeguimientoTiempoDone(imp.getSeguimientoTiempoDone());
        implementacion.setSeguimientoKilocaloriasDone(imp.getSeguimientoKilocaloriasDone());
        implementacion.setSeguimientoMetrosDone(imp.getSeguimientoMetrosDone());
        implementacion.setRealizado(imp.getRealizado());
    }

    @GetMapping("/editarimplementacion")
    public String doEditarImplementacion(@RequestParam("id") Integer id,@RequestParam("iddia") Integer iddia,
                                         Model model,HttpSession sesion){
        String strTo = "/crosstrainer/entrenador_implementacion";

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutina imp = implementacionEjercicioRutinaRepository.findById(id).orElse(null);
            //model.addAttribute("imp", imp);

            Implementacion implementacion = new Implementacion();
            asignarImplementacionUI(implementacion,imp);

            implementacion.setId(id);
            implementacion.setIdDia(iddia);


            model.addAttribute("implementacion",implementacion);

            List<Ejercicio> ejercicios = ejercicioRepository.findAll();
            model.addAttribute("ejercicios",ejercicios);

            Boolean editable = true;
            model.addAttribute("editable",editable);


        }


        return strTo;
    }

    private void asignarImplementacionReal(ImplementacionEjercicioRutina implementacion, Implementacion imp){
        implementacion.setEjercicio(imp.getEjercicio());
        implementacion.setSets(imp.getSets());
        implementacion.setRepeticiones(imp.getRepeticiones());
        implementacion.setPeso(imp.getPeso());
        implementacion.setTiempo(imp.getTiempo());
        implementacion.setKilocalorias(imp.getKilocalorias());
        implementacion.setMetros(imp.getMetros());
        implementacion.setSeguimientoSetsDone(imp.getSeguimientoSetsDone());
        implementacion.setSeguimientoTiempoDone(imp.getSeguimientoTiempoDone());
        implementacion.setSeguimientoKilocaloriasDone(imp.getSeguimientoKilocaloriasDone());
        implementacion.setSeguimientoMetrosDone(imp.getSeguimientoMetrosDone());
        implementacion.setRealizado(imp.getRealizado());
    }

    @PostMapping("/guardarimplementacion")
    public String doGuardarImplementacion(@ModelAttribute("implementacion") Implementacion implementacion,HttpSession sesion){
        String strTo = "redirect:/entrenamientos/editardia?iddia=" + implementacion.getIdDia();

        if(!estaAutenticado(sesion)){
            strTo = "redirect:/";
        }else{
            ImplementacionEjercicioRutina imp = this.implementacionEjercicioRutinaRepository.findById(implementacion.getId()).orElse(null);

            asignarImplementacionReal(imp,implementacion);

            this.implementacionEjercicioRutinaRepository.save(imp);
        }


        return strTo;
    }

    @GetMapping("/crear-ejercicio")
    public String doCrearEjercicio(HttpSession sesion, Model model){


        UserRol userRol = (UserRol) sesion.getAttribute("rol");

        EjercicioUI ejercicioUI = new EjercicioUI();
        ejercicioUI.setTrainerEjercicio(userRol.getRolUsuario());
        List<TipoEjercicio> tipoEjercicios = tipoEjercicioRepository.findAll();
        model.addAttribute("tipos",tipoEjercicios);
        model.addAttribute("ejercicioUI",ejercicioUI);

        return "admin/crearEjercicio";
    }

    @PostMapping("/guardar-ejercicio")
    public String doGuardarEjercicio(@ModelAttribute ("ejercicioUI") EjercicioUI ejercicioUI,HttpSession sesion){

        Ejercicio nuevoEjercicio = new Ejercicio();
        nuevoEjercicio.setNombre(ejercicioUI.getNombre());
        nuevoEjercicio.setDescripcion(ejercicioUI.getDescripcion());
        nuevoEjercicio.setEnlaceVideo(ejercicioUI.getEnlaceVideo());
        TipoEjercicio tipoEjercicio = tipoEjercicioRepository.getById(ejercicioUI.getIdTipo());
        nuevoEjercicio.setTipo(tipoEjercicio);

        ejercicioRepository.save(nuevoEjercicio);
        return "redirect:/";
    }




}