package es.uma.service;


import es.uma.dao.PlatosRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.PlatoDTO;
import es.uma.dto.TipoComidaDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.Plato;
import es.uma.entity.TipoComida;
import es.uma.ui.PlatoUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatoService {

    @Autowired
    public PlatosRepository platosRepository;


    public List<PlatoDTO> getAllDishes(){
        return platosRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public PlatoDTO getById(Integer id){
        return this.convertEntityToDto(platosRepository.findById(id).orElse(null));
    }

    public void deleteById(Integer id){
        platosRepository.deleteById(id);
    }

    public List<PlatoDTO> dishesFilter(String nombre, String tiempo, String receta){
        return platosRepository.filtrarPlatos(nombre,tiempo,receta)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    public void addDish(PlatoUI platoUI){
        Plato nuevoPlato = new Plato();
        nuevoPlato.setNombre(platoUI.getNombre());
        nuevoPlato.setTiempoDePreparacion(platoUI.getTiempoDePreparacion());
        nuevoPlato.setEnlaceReceta(platoUI.getEnlaceReceta());
        nuevoPlato.setReceta(platoUI.getReceta());

        this.saveDish(nuevoPlato);
    }

    public void editDish(PlatoUI platoUI){
        Plato plato = platosRepository.findById(platoUI.getId()).orElse(null);
        plato.setNombre(platoUI.getNombre());
        plato.setEnlaceReceta(platoUI.getEnlaceReceta());
        plato.setReceta(platoUI.getReceta());
        plato.setTiempoDePreparacion(platoUI.getTiempoDePreparacion());

        this.saveDish(plato);
    }

    public PlatoUI setPlatoUI(Integer id, PlatoUI platoUI){
        Plato plato = platosRepository.findById(id).orElse(null);
        platoUI.setNombre(plato.getNombre());
        platoUI.setEnlaceReceta(plato.getEnlaceReceta());
        platoUI.setReceta(plato.getReceta());
        platoUI.setTiempoDePreparacion(plato.getTiempoDePreparacion());

        return platoUI;
    }

    public void saveDish(Plato plato){
        platosRepository.save(plato);

    }

    public PlatoDTO convertEntityToDto(Plato plato){
        PlatoDTO platoDTO = new PlatoDTO();
        platoDTO.setId(plato.getId());
        platoDTO.setNombre(plato.getNombre());
        platoDTO.setEnlaceReceta(plato.getEnlaceReceta());
        platoDTO.setReceta(plato.getReceta());
        platoDTO.setTiempoDePreparacion(plato.getTiempoDePreparacion());
        return platoDTO;
    }

    public Plato convertDtoToEntity(PlatoDTO platoDTO){
        Plato plato = new Plato();
        plato.setId(platoDTO.getId());
        plato.setNombre(platoDTO.getNombre());
        plato.setEnlaceReceta(platoDTO.getEnlaceReceta());
        plato.setReceta(platoDTO.getReceta());
        plato.setTiempoDePreparacion(platoDTO.getTiempoDePreparacion());
        return plato;
    }
}
