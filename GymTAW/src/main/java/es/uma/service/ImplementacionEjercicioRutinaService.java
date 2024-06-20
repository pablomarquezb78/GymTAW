package es.uma.service;


import es.uma.dao.ImplementacionEjercicioRutinaRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.ImplementacionEjercicioRutinaDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import es.uma.ui.Implementacion;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void guardarImplementacion(ImplementacionEjercicioRutinaDTO implmentacionDTO){
        this.implementacionEjercicioRutinaRepository.save(convertDtoToEntity(implmentacionDTO));
    }

    public ImplementacionEjercicioRutinaDTO getByID(Integer idImplementacion){
        return convertEntityToDto(implementacionEjercicioRutinaRepository.getById(idImplementacion));
    }

    public ImplementacionEjercicioRutina convertDtoToEntity(ImplementacionEjercicioRutinaDTO implementacionEjercicioRutinaDTO) {
        ImplementacionEjercicioRutina implementacionEjercicioRutina = new ImplementacionEjercicioRutina();
        implementacionEjercicioRutina.setId(implementacionEjercicioRutinaDTO.getId());
        implementacionEjercicioRutina.setMetros(implementacionEjercicioRutinaDTO.getMetros());
        implementacionEjercicioRutina.setPeso(implementacionEjercicioRutinaDTO.getPeso());
        implementacionEjercicioRutina.setSets(implementacionEjercicioRutinaDTO.getSets());
        implementacionEjercicioRutina.setRepeticiones(implementacionEjercicioRutinaDTO.getRepeticiones());
        implementacionEjercicioRutina.setRutina(rutinaService.convertDtoToEntity(implementacionEjercicioRutinaDTO.getRutina()));
        implementacionEjercicioRutina.setEjercicio(ejercicioService.convertDtoToEntity(implementacionEjercicioRutinaDTO.getEjercicio()));

        return implementacionEjercicioRutina;
    }


    public void asignarImplementacionUIaImplementacionDTO(ImplementacionEjercicioRutinaDTO implementacionDTO,Implementacion implementacionUI){
        //implementacion.setEjercicio(imp.getEjercicio());
        implementacionDTO.setSets(implementacionUI.getSets());
        implementacionDTO.setRepeticiones(implementacionUI.getRepeticiones());
        implementacionDTO.setPeso(implementacionUI.getPeso());
        implementacionDTO.setTiempo(implementacionUI.getTiempo());
        implementacionDTO.setKilocalorias(implementacionUI.getKilocalorias());
        implementacionDTO.setMetros(implementacionUI.getMetros());
    }

    public void asignarImplementacionDTOaImplementacionUI(Implementacion implementacionUI,ImplementacionEjercicioRutinaDTO implementacionDTO){
        implementacionUI.setId(implementacionDTO.getId());
        implementacionUI.setEjercicio(implementacionDTO.getEjercicio());
        if(implementacionDTO.getRutina()!=null) implementacionUI.setRutina(implementacionDTO.getRutina().getId());
        implementacionUI.setSets(implementacionDTO.getSets());
        implementacionUI.setRepeticiones(implementacionDTO.getRepeticiones());
        implementacionUI.setPeso(implementacionDTO.getPeso());
        implementacionUI.setTiempo(implementacionDTO.getTiempo());
        implementacionUI.setKilocalorias(implementacionDTO.getKilocalorias());
        implementacionUI.setMetros(implementacionDTO.getMetros());
    }

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

    public ImplementacionEjercicioRutinaDTO getImplementacionPorId(Integer id){
        return convertEntityToDto(implementacionEjercicioRutinaRepository.findById(id).orElse(null));
    }

}