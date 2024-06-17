package es.uma.controller.trainerContoller;


import es.uma.dao.UserRepository;
import es.uma.entity.Ejercicio;
import es.uma.entity.TipoEjercicio;
import org.springframework.ui.Model;
import es.uma.entity.User;
import es.uma.entity.UserRol;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/trainer")
public class trainerController {


    private final UserRepository userRepository;

    public trainerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String trainerIndex(Model model, HttpSession session) {

        UserRol trainer = (UserRol) session.getAttribute("rol");
        User userTrainer = (User) session.getAttribute("user");
        model.addAttribute("trainer",trainer);
        model.addAttribute("user",userTrainer);

        return "trainer/trainer_index";
    }

    @GetMapping("/crear")
    public String trainerCrear(Model model, HttpSession session) {

        return "trainer/trainer_crear";
    }


    @GetMapping("/crear-ejercicio")
    public String crearEjercicio(Model model, HttpSession session) {

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(-1);

        UserRol trainer = (UserRol) session.getAttribute("rol");
        model.addAttribute("trainer",trainer);
        model.addAttribute("ejercicio",ejercicio);

        return "trainer/trainer_crear_ejercicio";
    }

    @GetMapping("/crear-tipo")
    public String crearTipo(Model model, HttpSession session) {

        TipoEjercicio tipoEjercicio = new TipoEjercicio();
        tipoEjercicio.setId(-1);

        UserRol trainer = (UserRol) session.getAttribute("rol");
        model.addAttribute("trainer",trainer);
        model.addAttribute("tipoEjercicio",tipoEjercicio);

        return "trainer/trainer_crear_tipo";
    }


    @GetMapping("/clientes")
    public String listadoUsarios(Model model, HttpSession session) {

        List<User> lista = userRepository.clientesAsociadosConEntrenador((User) session.getAttribute("user"));

        model.addAttribute("listadoUsuarios",lista);

        return "trainer/trainer_listado_usuarios";
    }

  /*  @GetMapping("/entrenamientos")
    public String entrenamientos(@RequestParam("id") String id ,Model model, HttpSession session) {





    }*/

    @GetMapping("/perfil-cliente")
    public String perfilCliente(@RequestParam("id") Integer id ,Model model, HttpSession session) {

        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user",user);
        UserRol trainer = (UserRol) session.getAttribute("rol");
        model.addAttribute("rol",trainer);
        return "cliente/perfil_cliente";
    }





}
