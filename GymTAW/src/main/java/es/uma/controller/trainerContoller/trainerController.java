package es.uma.controller.trainerContoller;


import es.uma.entity.Ejercicio;
import org.springframework.ui.Model;
import es.uma.entity.User;
import es.uma.entity.UserRol;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trainer")
public class trainerController {



    @GetMapping("/")
    public String trainerIndex(Model model, HttpSession session) {

        UserRol trainer = (UserRol) session.getAttribute("rol");
        User userTrainer = (User) session.getAttribute("user");
        model.addAttribute("trainer",trainer);
        model.addAttribute("user",userTrainer);

        return "trainer/trainer_index";
    }

    @GetMapping("/crear")
    public String crearEjercicio(Model model, HttpSession session) {

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(-1);

        UserRol trainer = (UserRol) session.getAttribute("rol");
        model.addAttribute("trainer",trainer);
        model.addAttribute("ejercicio",ejercicio);

        return "trainer/trainer_crear_ejercicio";

    }






}
