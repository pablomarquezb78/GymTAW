package es.uma.service;


import es.uma.dao.ImplementacionEjercicioRutinaRepository;
import es.uma.dto.FeedbackEjercicioDTO;
import es.uma.dto.ImplementacionEjercicioRutinaDTO;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImplementacionEjercicioRutinaService {
    
    //Ejercicio y rutina y feedback
    @Autowired
    private ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;

    public ImplementacionEjercicioRutinaDTO convertEntityToDto(ImplementacionEjercicioRutina implementacionEjercicioRutina) {
        ImplementacionEjercicioRutinaDTO implementacionEjercicioRutinaDTO = new ImplementacionEjercicioRutinaDTO();
        implementacionEjercicioRutinaDTO.setId(implementacionEjercicioRutina.getId());
        implementacionEjercicioRutinaDTO.setMetros(implementacionEjercicioRutina.getMetros());
        implementacionEjercicioRutinaDTO.setPeso(implementacionEjercicioRutina.getPeso());
        implementacionEjercicioRutinaDTO.setSets(implementacionEjercicioRutina.getSets());
        implementacionEjercicioRutinaDTO.setRepeticiones(implementacionEjercicioRutina.getRepeticiones());
        return implementacionEjercicioRutinaDTO;
    }

    public List<ImplementacionEjercicioRutinaDTO> convertListEntityToDto(List<ImplementacionEjercicioRutina> implementacionEjercicioRutinaList){
        List<ImplementacionEjercicioRutinaDTO> implementacionEjercicioRutinaDTOList = new ArrayList<>();
        for (ImplementacionEjercicioRutina implementacionEjercicioRutina : implementacionEjercicioRutinaList){
            implementacionEjercicioRutinaDTOList.add(this.convertEntityToDto(implementacionEjercicioRutina));
        }
        return implementacionEjercicioRutinaDTOList;
    }

    public List<ImplementacionEjercicioRutinaDTO> getImplementacionesDeRutinaID(Integer id){

        List<ImplementacionEjercicioRutinaDTO> lista = implementacionEjercicioRutinaRepository.encontrarImplementacionesPorRutinaID(id)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());


        return lista;
    }

}
