package es.uma.service;

import es.uma.dao.IngredienteRepository;
import es.uma.dto.IngredienteDTO;
import es.uma.entity.Ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {
    @Autowired
    IngredienteRepository ingredienteRepository;

    public IngredienteDTO convertEntityToDto(Ingrediente ingrediente){
        IngredienteDTO ingredienteDTO = new IngredienteDTO();
        ingredienteDTO.setId(ingrediente.getId());
        ingredienteDTO.setNombre(ingrediente.getNombre());
        return ingredienteDTO;
    }
}
