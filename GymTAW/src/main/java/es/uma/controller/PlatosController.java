package es.uma.controller;

import es.uma.dao.PlatosRepository;
import es.uma.entity.Plato;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.uma.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dietista")
public class PlatosController {

    @Autowired
    protected PlatosRepository platosRepository;

    @GetMapping("/mostrarPlato")
    public String doLoad (@RequestParam(value = "platosDisplay",required = false) String platoId
                         , HttpSession session
                         , Model model) {
        if(platoId != null){
            Plato plato = platosRepository.findPlatoById(Integer.valueOf(platoId));
            model.addAttribute("selectedPlato", plato);
            model.addAttribute("listaIngredientes",platosRepository.getIngredientesFromPlato(plato));
        }
        model.addAttribute("panel", "main");
        model.addAttribute("listaPlatos", platosRepository.getPlatosFromDietista((User) session.getAttribute("user")));
<<<<<<< Updated upstream


        return "dietista/dietista_platos";
=======
        model.addAttribute("selectedPlato", plato);
        model.addAttribute("listaIngredientes",platosRepository.getIngredientesFromPlato(plato));
        return "dietista_platos";
>>>>>>> Stashed changes
    }
}
