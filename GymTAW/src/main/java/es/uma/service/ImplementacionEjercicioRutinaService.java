package es.uma.service;


import es.uma.dao.DiaEntrenamientoRepository;
import es.uma.dao.ImplementacionEjercicioRutinaRepository;
import es.uma.dao.RutinaRepository;
import es.uma.dto.DiaEntrenamientoDTO;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.ImplementacionEjercicioRutinaDTO;
import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.Ejercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import es.uma.ui.Implementacion;
import es.uma.ui.PlatoUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImplementacionEjercicioRutinaService {

    //Ejercicio y rutina y feedback
    @Autowired
    private RutinaRepository rutinaRepository;
    @Autowired
    private ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private RutinaService rutinaService;
    @Autowired
    private DiaEntrenamientoRepository diaEntrenamientoRepository;


    public void guardarImplementacion(ImplementacionEjercicioRutinaDTO implmentacionDTO){
        this.implementacionEjercicioRutinaRepository.save(convertDtoToEntity(implmentacionDTO));
    }

    private void asignarImplementacionReal(ImplementacionEjercicioRutina implementacion, Implementacion imp){
        implementacion.setEjercicio(ejercicioService.convertDtoToEntity(imp.getEjercicio()));
        implementacion.setSets(imp.getSets());
        implementacion.setRepeticiones(imp.getRepeticiones());
        implementacion.setPeso(imp.getPeso());
        implementacion.setTiempo(imp.getTiempo());
        implementacion.setKilocalorias(imp.getKilocalorias());
        implementacion.setMetros(imp.getMetros());
    }

    public void guardarImplementacionUI(Implementacion implementacionUI){

        ImplementacionEjercicioRutina imp;

        if(implementacionUI.getId()!=null){ //Editar

            imp = this.implementacionEjercicioRutinaRepository.findById(implementacionUI.getId()).orElse(null);
            asignarImplementacionReal(imp,implementacionUI);

        }else{//Crear

            imp = new ImplementacionEjercicioRutina();
            DiaEntrenamiento dia = diaEntrenamientoRepository.getById(implementacionUI.getIdDia());
            imp.setRutina(dia.getRutina());
            asignarImplementacionReal(imp,implementacionUI);

        }

        this.implementacionEjercicioRutinaRepository.save(imp);

    }



    public ImplementacionEjercicioRutinaDTO getByID(Integer idImplementacion){
        return convertEntityToDto(implementacionEjercicioRutinaRepository.getById(idImplementacion));
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
        implementacionUI.setTiempo(implementacionDTO.getTiempo());//
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

    public List<ImplementacionEjercicioRutinaDTO> getImplementacionByRutina(Integer rutinaId){
        List<ImplementacionEjercicioRutina> implementacionEjercicioRutinaList = implementacionEjercicioRutinaRepository.encontrarImplementacionesPorRutinaID(rutinaId);
        if(implementacionEjercicioRutinaList!=null){
            return implementacionEjercicioRutinaList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        }else{
            return null;
        }
    }

    public void deleteById(Integer id){
        implementacionEjercicioRutinaRepository.deleteById(id);
    }

    public void guardarNuevaImplementacion(Implementacion implementacion){
        ImplementacionEjercicioRutina imp;
        if(implementacion.getId()!=null){
            imp = this.implementacionEjercicioRutinaRepository.findById(implementacion.getId()).orElse(null);
            asignarImplementacionReal(imp,implementacion);
        }else{
            imp = new ImplementacionEjercicioRutina();
            imp.setRutina(rutinaRepository.findById(implementacion.getRutina()).orElse(null));

            asignarImplementacionReal(imp,implementacion);
        }

        this.implementacionEjercicioRutinaRepository.save(imp);
    }

    public ImplementacionEjercicioRutinaDTO convertEntityToDto(ImplementacionEjercicioRutina implementacionEjercicioRutina) {
        ImplementacionEjercicioRutinaDTO implementacionEjercicioRutinaDTO = new ImplementacionEjercicioRutinaDTO();
        implementacionEjercicioRutinaDTO.setId(implementacionEjercicioRutina.getId());
        implementacionEjercicioRutinaDTO.setMetros(implementacionEjercicioRutina.getMetros());
        implementacionEjercicioRutinaDTO.setPeso(implementacionEjercicioRutina.getPeso());
        implementacionEjercicioRutinaDTO.setSets(implementacionEjercicioRutina.getSets());
        implementacionEjercicioRutinaDTO.setTiempo(implementacionEjercicioRutina.getTiempo());
        implementacionEjercicioRutinaDTO.setRepeticiones(implementacionEjercicioRutina.getRepeticiones());
        implementacionEjercicioRutinaDTO.setRutina(rutinaService.convertEntityToDto(implementacionEjercicioRutina.getRutina()));
        implementacionEjercicioRutinaDTO.setEjercicio(ejercicioService.convertEntityToDto(implementacionEjercicioRutina.getEjercicio()));
        implementacionEjercicioRutinaDTO.setKilocalorias(implementacionEjercicioRutina.getKilocalorias());
        implementacionEjercicioRutinaDTO.setTiempo(implementacionEjercicioRutina.getTiempo());

        return implementacionEjercicioRutinaDTO;
    }

    public ImplementacionEjercicioRutina convertDtoToEntity(ImplementacionEjercicioRutinaDTO implementacionEjercicioRutinaDTO) {
        ImplementacionEjercicioRutina implementacionEjercicioRutina = new ImplementacionEjercicioRutina();
        implementacionEjercicioRutina.setId(implementacionEjercicioRutinaDTO.getId());
        implementacionEjercicioRutina.setMetros(implementacionEjercicioRutinaDTO.getMetros());
        implementacionEjercicioRutina.setPeso(implementacionEjercicioRutinaDTO.getPeso());
        implementacionEjercicioRutina.setSets(implementacionEjercicioRutinaDTO.getSets());
        implementacionEjercicioRutina.setTiempo(implementacionEjercicioRutinaDTO.getTiempo());
        implementacionEjercicioRutina.setRepeticiones(implementacionEjercicioRutinaDTO.getRepeticiones());
        implementacionEjercicioRutina.setRutina(rutinaService.convertDtoToEntity(implementacionEjercicioRutinaDTO.getRutina()));
        implementacionEjercicioRutina.setEjercicio(ejercicioService.convertDtoToEntity(implementacionEjercicioRutinaDTO.getEjercicio()));
        implementacionEjercicioRutina.setKilocalorias(implementacionEjercicioRutinaDTO.getKilocalorias());
        implementacionEjercicioRutina.setTiempo(implementacionEjercicioRutinaDTO.getTiempo());

        return implementacionEjercicioRutina;
    }

    public List<ImplementacionEjercicioRutinaDTO> convertListEntityToDto(List<ImplementacionEjercicioRutina> implementacionEjercicioRutinaList){
        List<ImplementacionEjercicioRutinaDTO> implementacionEjercicioRutinaDTOList = new ArrayList<>();
        for (ImplementacionEjercicioRutina implementacionEjercicioRutina : implementacionEjercicioRutinaList){
            implementacionEjercicioRutinaDTOList.add(this.convertEntityToDto(implementacionEjercicioRutina));
        }
        return implementacionEjercicioRutinaDTOList;
    }

    public ImplementacionEjercicioRutinaDTO getImplementacionPorId(Integer id){
        return convertEntityToDto(implementacionEjercicioRutinaRepository.findById(id).orElse(null));
    }

}