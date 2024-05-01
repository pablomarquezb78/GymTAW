package es.uma.controller;


import es.uma.dao.DiaEntrenamientoRepository;
import es.uma.dao.UserRepository;
import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/entrenamientos")
public class EntrenamientosController extends BaseController{

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;


    @GetMapping("/")
    public String doListar (Model model, HttpSession sesion){
        String strTo = "/crosstrainer/entrenador_listadoclientes";

        if(estaAutenticado(sesion) == false){
            strTo= "redirect:/";
        }else {
            List<User> lista = userRepository.clientesAsociadosConEntrenador((User) sesion.getAttribute("user"));
            model.addAttribute("lista",lista);
        }

        return strTo;
    }


    @GetMapping("/versemana")
    public String doVerSemanaEntrenamientos (@RequestParam("id") Integer id,Model model, HttpSession sesion){
        String strTo = "/crosstrainer/entrenador_semana";

        if(estaAutenticado(sesion) == false){
            strTo = "redicrect:/";
        }else{
            User cliente = (User) userRepository.findById(id).orElse(null);
            List<DiaEntrenamiento> dias =  diaEntrenamientoRepository.diasEntrenamientosdeCliente(cliente);

            model.addAttribute("dias",dias);
        }


        return strTo;
    }


}
