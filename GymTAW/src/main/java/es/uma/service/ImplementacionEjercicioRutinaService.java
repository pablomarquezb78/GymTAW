package es.uma.service;


import es.uma.dao.ImplementacionEjercicioRutinaRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.FeedbackEjercicioDTO;
import es.uma.dto.ImplementacionEjercicioRutinaDTO;
import es.uma.entity.Ejercicio;
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
    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private RutinaService rutinaService;

    public List<ImplementacionEjercicioRutinaDTO> findByExercise(EjercicioDTO ejercicioDTO){
        Ejercicio ejercicio = ejercicioService.convertDtoToEntity(ejercicioDTO);
        return implementacionEjercicioRutinaRepository.buscarPorEjercicio(ejercicio)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<ImplementacionEjercicioRutinaDTO> filterImplementations(EjercicioDTO ejercicioDTO, Integer idRutina, String sets,
                                                                        String repeticiones, String peso, String tiempo, String metros, String kcal){
        Ejercicio ejercicio = ejercicioService.convertDtoToEntity(ejercicioDTO);
        Rutina rutina = rutinaService.convertDtoToEntity(rutinaService.getRutinaByID(idRutina));
        return implementacionEjercicioRutinaRepository.filtrarImplementaciones(ejercicio, rutina, sets, repeticiones, peso, tiempo, metros, kcal)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id){
        implementacionEjercicioRutinaRepository.deleteById(id);
    }

    public ImplementacionEjercicioRutinaDTO convertEntityToDto(ImplementacionEjercicioRutina implementacionEjercicioRutina) {
        ImplementacionEjercicioRutinaDTO implementacionEjercicioRutinaDTO = new ImplementacionEjercicioRutinaDTO();
        implementacionEjercicioRutinaDTO.setId(implementacionEjercicioRutina.getId());
        implementacionEjercicioRutinaDTO.setMetros(implementacionEjercicioRutina.getMetros());
        implementacionEjercicioRutinaDTO.setPeso(implementacionEjercicioRutina.getPeso());
        implementacionEjercicioRutinaDTO.setSets(implementacionEjercicioRutina.getSets());
        implementacionEjercicioRutinaDTO.setRepeticiones(implementacionEjercicioRutina.getRepeticiones());
        implementacionEjercicioRutinaDTO.setRutina(rutinaService.convertEntityToDto(implementacionEjercicioRutina.getRutina()));
        implementacionEjercicioRutinaDTO.setEjercicio(ejercicioService.convertEntityToDto(implementacionEjercicioRutina.getEjercicio()));

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
