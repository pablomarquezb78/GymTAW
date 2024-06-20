package es.uma.service;


import es.uma.dao.RutinaRepository;
import es.uma.dto.RutinaDTO;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import es.uma.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutinaService {

    @Autowired
    public UserService userService;
    @Autowired
    private RutinaRepository rutinaRepository;

    public List<RutinaDTO> getAllRutinas(){
        return rutinaRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public Rutina convertDtoToEntity(RutinaDTO rutinaDTO) {
        Rutina rutina = new Rutina();
        rutina.setId(rutinaDTO.getId());
        rutina.setNombre(rutinaDTO.getNombre());
        rutina.setEntrenador(userService.convertDtoToEntity(rutinaDTO.getEntrenador()));
        rutina.setFechaCreacion(rutinaDTO.getFechaCreacion());
        return rutina;
    }

    public void setNombreRutina(Integer idrutina,String nombre){
        Rutina rutina = rutinaRepository.getById(idrutina);
        rutina.setNombre(nombre);
        rutinaRepository.save(rutina);
    }

    public RutinaDTO convertEntityToDto(Rutina rutina) {
        RutinaDTO rutinaDTO = new RutinaDTO();
        rutinaDTO.setId(rutina.getId());
        rutinaDTO.setNombre(rutina.getNombre());
        rutinaDTO.setEntrenador(userService.convertEntityToDto(rutina.getEntrenador()));
        rutinaDTO.setFechaCreacion(rutina.getFechaCreacion());
        return rutinaDTO;
    }

    public List<RutinaDTO> getRutinasPorNombreYEntrenador(String nombre, Integer tipofiltrado, User self){

        List<RutinaDTO> rutinas;
        if(tipofiltrado==0){
            rutinas = rutinaRepository.buscarPorNombre(nombre).stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        }else{
            rutinas = rutinaRepository.buscarPorNombreyEntrenador(nombre,self.getId()).stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        }

        return rutinas;
    }

    public RutinaDTO getRutinaByID(Integer idrutina) {
        return convertEntityToDto(rutinaRepository.getById(idrutina));
    }
}
