package es.uma.service;

import es.uma.dao.EjercicioRepository;
import es.uma.dao.TipoEjercicioRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.TipoEjercicioDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.TipoEjercicio;
import es.uma.ui.EjercicioUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EjercicioService {

    @Autowired
    EjercicioRepository ejercicioRepository;
    @Autowired
    TipoEjercicioRepository tipoEjercicioRepository;

    @Autowired
    public TipoEjercicioService tipoEjercicioService;

    public List<EjercicioDTO> getEjerciciosDeTipoDeEjercicio(Integer idtipo){
        return  ejercicioRepository.filtrarEjercicioSoloDeTipoID(idtipo)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<EjercicioDTO> getAllExercises(){
        return ejercicioRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
    
    public EjercicioDTO getById(Integer id){
        return ejercicioRepository.findById(id).map(this::convertEntityToDto).orElse(null);
    }

    public List<EjercicioDTO> filterExercises(String nombre, String descripcion){
        return ejercicioRepository.filtrarEjercicios(nombre, descripcion)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<EjercicioDTO> filterExercisesWithType(String nombre, String descripcion, Integer idTipo){
        return ejercicioRepository.filtrarEjerciciosConTipo(nombre, descripcion, idTipo)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public void saveExercise(EjercicioUI ejercicioUI){
        Ejercicio nuevoEjercicio = new Ejercicio();
        nuevoEjercicio.setNombre(ejercicioUI.getNombre());
        nuevoEjercicio.setTipo(tipoEjercicioService.convertDtoToEntity(tipoEjercicioService.getById(ejercicioUI.getIdTipo())));
        nuevoEjercicio.setEnlaceVideo(ejercicioUI.getEnlaceVideo());
        nuevoEjercicio.setDescripcion(ejercicioUI.getDescripcion());

        this.save(nuevoEjercicio);

    }

    public void editExercise(EjercicioUI ejercicioUI){
        Ejercicio ejercicio = ejercicioRepository.findById(ejercicioUI.getId()).orElse(null);
        ejercicio.setNombre(ejercicioUI.getNombre());
        ejercicio.setTipo(tipoEjercicioService.convertDtoToEntity(tipoEjercicioService.getById(ejercicioUI.getIdTipo())));
        ejercicio.setEnlaceVideo(ejercicioUI.getEnlaceVideo());
        ejercicio.setDescripcion(ejercicioUI.getDescripcion());

        this.save(ejercicio);
    }

    public void save(Ejercicio ejercicio){
        ejercicioRepository.save(ejercicio);

    }

    public EjercicioUI setEjercicioUI(Integer id, EjercicioUI ejercicioUI){
        Ejercicio ejercicio = ejercicioRepository.findById(id).orElse(null);
        ejercicioUI.setNombre(ejercicio.getNombre());
        ejercicioUI.setIdTipo(ejercicio.getTipo().getId());
        ejercicioUI.setEnlaceVideo(ejercicio.getEnlaceVideo());
        ejercicioUI.setDescripcion(ejercicio.getDescripcion());
        return ejercicioUI;
    }

    public void deleteById(Integer id){
        ejercicioRepository.deleteById(id);
    }

    public List<EjercicioDTO> getEjerciciosPorNombre(String nombre){
        return ejercicioRepository.filtrarEjercicioPorNombre(nombre).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public void crearEjercicioDeUI( EjercicioUI ejercicioUI){
        Ejercicio nuevoEjercicio = new Ejercicio();
        nuevoEjercicio.setNombre(ejercicioUI.getNombre());
        nuevoEjercicio.setDescripcion(ejercicioUI.getDescripcion());
        nuevoEjercicio.setEnlaceVideo(ejercicioUI.getEnlaceVideo());

        TipoEjercicio tipoEjercicio = tipoEjercicioRepository.getById(ejercicioUI.getIdTipo());
        nuevoEjercicio.setTipo(tipoEjercicio);
        ejercicioRepository.save(nuevoEjercicio);
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

    public Ejercicio convertDtoToEntity(EjercicioDTO ejercicioDTO){
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(ejercicioDTO.getId());
        ejercicio.setNombre(ejercicioDTO.getNombre());
        ejercicio.setEnlaceVideo(ejercicioDTO.getEnlaceVideo());
        ejercicio.setDescripcion(ejercicioDTO.getDescripcion());
        ejercicio.setTipo(tipoEjercicioService.convertDtoToEntity(ejercicioDTO.getTipo()));
        return ejercicio;
    }
    
}
