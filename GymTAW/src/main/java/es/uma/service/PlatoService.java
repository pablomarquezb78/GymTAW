package es.uma.service;


import es.uma.dao.PlatosRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.PlatoDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.Plato;
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

    public PlatoDTO convertEntityToDto(Plato plato){
        PlatoDTO platoDTO = new PlatoDTO();
        platoDTO.setId(plato.getId());
        platoDTO.setNombre(plato.getNombre());
        platoDTO.setEnlaceReceta(plato.getEnlaceReceta());
        platoDTO.setReceta(plato.getReceta());
        platoDTO.setTiempoDePreparacion(plato.getTiempoDePreparacion());
        return platoDTO;
    }
}
