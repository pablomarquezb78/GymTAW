package es.uma.service;


import es.uma.dao.RutinaRepository;
import es.uma.dto.RutinaDTO;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaService {

    @Autowired
    public UserService userService;
    @Autowired
    public ImplementacionEjercicioRutinaService implementacionEjercicioRutinaService;
    @Autowired
    private RutinaRepository rutinaRepository;


    public RutinaDTO convertEntityToDto(Rutina rutina) {
        RutinaDTO rutinaDTO = new RutinaDTO();
        rutinaDTO.setId(rutina.getId());
        rutinaDTO.setNombre(rutina.getNombre());
        rutinaDTO.setEntrenador(userService.convertEntityToDto(rutina.getEntrenador()));
        rutinaDTO.setFechaCreacion(rutina.getFechaCreacion());
        rutinaDTO.setImplementacionesEjercicioRutina(implementacionEjercicioRutinaService.convertListEntityToDto(rutina.getImplementacionesEjercicioRutina()));
        return rutinaDTO;
    }

    public RutinaDTO getRutinaByID(Integer idrutina) {
        return convertEntityToDto(rutinaRepository.getById(idrutina));
    }
}
