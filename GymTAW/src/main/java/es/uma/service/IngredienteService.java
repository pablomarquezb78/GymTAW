package es.uma.service;

import es.uma.dao.IngredienteRepository;
import es.uma.dao.PlatosRepository;
import es.uma.dto.IngredienteDTO;
import es.uma.dto.PlatoDTO;
import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredienteService {
    @Autowired
    IngredienteRepository ingredienteRepository;
    @Autowired
    private PlatoService platoService;
    @Autowired
    private PlatosRepository platosRepository;


    public List<IngredienteDTO> getIngredientesLinkedToPlato(PlatoDTO platoDTO)
    {
        Plato p = platoService.convertDtoToEntity(platoDTO);
        List<Ingrediente> listaIngredientes = platosRepository.getIngredientesLinkedToPlato(p);
        List<IngredienteDTO> ingredienteDTOList = this.convertlistEntityToDto(listaIngredientes);
        return ingredienteDTOList;
    }

    public IngredienteDTO convertEntityToDto(Ingrediente ingrediente){
        IngredienteDTO ingredienteDTO = new IngredienteDTO();
        ingredienteDTO.setId(ingrediente.getId());
        ingredienteDTO.setNombre(ingrediente.getNombre());
        ingredienteDTO.setAzucares(ingrediente.getAzucares());
        ingredienteDTO.setGrasas(ingrediente.getGrasas());
        ingredienteDTO.setKilocalorias(ingrediente.getKilocalorias());
        ingredienteDTO.setProteinas(ingrediente.getProteinas());
        ingredienteDTO.setHidratosDeCarbono(ingrediente.getHidratosDeCarbono());
        return ingredienteDTO;
    }

    public Ingrediente convertDtoToEntity(IngredienteDTO ingredienteDTO){
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(ingredienteDTO.getId());
        ingrediente.setNombre(ingredienteDTO.getNombre());
        ingrediente.setAzucares(ingredienteDTO.getAzucares());
        ingrediente.setGrasas(ingredienteDTO.getGrasas());
        ingrediente.setKilocalorias(ingredienteDTO.getKilocalorias());
        ingrediente.setProteinas(ingredienteDTO.getProteinas());
        ingrediente.setHidratosDeCarbono(ingredienteDTO.getHidratosDeCarbono());
        return ingrediente;
    }

    public List<IngredienteDTO> convertlistEntityToDto(List<Ingrediente> ingredienteList){
        List<IngredienteDTO> ingredienteDTOList = new ArrayList<>();
        for(Ingrediente ingrediente : ingredienteList){
            ingredienteDTOList.add(this.convertEntityToDto(ingrediente));
        }
        return ingredienteDTOList;
    }

    public List<Ingrediente> convertlistDtoToEntity(List<IngredienteDTO> ingredienteDTOList){
        List<Ingrediente> ingredienteList = new ArrayList<>();
        for(IngredienteDTO ingredienteDTO : ingredienteDTOList){
            ingredienteList.add(this.convertDtoToEntity(ingredienteDTO));
        }
        return ingredienteList;
    }
}
