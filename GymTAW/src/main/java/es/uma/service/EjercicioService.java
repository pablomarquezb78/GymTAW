package es.uma.service;

import es.uma.dao.EjercicioRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.entity.Ejercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EjercicioService {

    @Autowired
    EjercicioRepository ejercicioRepository;

    //ImplementacionEjercicioRutina

    @Autowired
    public TipoEjercicioService tipoEjercicioService;


    public List<EjercicioDTO> getAllExercises(){
        return ejercicioRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public EjercicioDTO convertEntityToDto(Ejercicio ejercicio){
        EjercicioDTO ejercicioDTO = new EjercicioDTO();
        ejercicioDTO.setId(ejercicio.getId());
        ejercicioDTO.setNombre(ejercicio.getNombre());
        ejercicioDTO.setEnlaceVideo(ejercicio.getEnlaceVideo());
        ejercicioDTO.setDescripcion(ejercicio.getDescripcion());
        ejercicioDTO.setTipo(tipoEjercicioService.convertEntityToDto(ejercicio.getTipo()));
       return ejercicioDTO;
    }
}
