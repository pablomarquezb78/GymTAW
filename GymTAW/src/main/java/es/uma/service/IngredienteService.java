package es.uma.service;

import es.uma.dao.CantidadIngredientePlatoComidaRepository;
import es.uma.dao.IngredienteRepository;
import es.uma.dao.PlatosRepository;
import es.uma.dto.IngredienteDTO;
import es.uma.dto.PlatoDTO;
import es.uma.entity.CantidadIngredientePlatoComida;
import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;
import es.uma.ui.IngredienteImplementandoUI;
import es.uma.ui.PlatoDietistaUI;
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
    @Autowired
    private CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<IngredienteDTO> getIngredientesLinkedToPlato(PlatoDTO platoDTO)
    {
        Plato p = platoService.convertDtoToEntity(platoDTO);
        List<Ingrediente> listaIngredientes = platosRepository.getIngredientesLinkedToPlato(p);
        List<IngredienteDTO> ingredienteDTOList = this.convertlistEntityToDto(listaIngredientes);
        return ingredienteDTOList;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<IngredienteDTO> findAllIngredientes()
    {
        List<Ingrediente> listaIngredientes = ingredienteRepository.findAll();
        List<IngredienteDTO> ingredienteDTOList = this.convertlistEntityToDto(listaIngredientes);
        return ingredienteDTOList;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public PlatoDietistaUI addIngredienteToPlatoDietistaUI(PlatoDietistaUI platoDietistaUI)
    {
        ArrayList<Ingrediente> listaIngredientesPlato = platoDietistaUI.getIngredientes();
        if(listaIngredientesPlato == null) listaIngredientesPlato = new ArrayList<>();
        Ingrediente ingrediente = ingredienteRepository.findById(platoDietistaUI.getAddedIngrediente()).orElse(null);
        listaIngredientesPlato.add(ingrediente);
        platoDietistaUI.setIngredientes(listaIngredientesPlato);
        return platoDietistaUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public PlatoDietistaUI eliminarIngredienteToPlatoDietista(Integer ingredienteId, PlatoDietistaUI platoDietistaUI)
    {
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId).orElse(null);
        for(int i=0; i < platoDietistaUI.getIngredientes().size(); ++i)
        {
            if(platoDietistaUI.getIngredientes().get(i).getId() == ingrediente.getId())
            {
                platoDietistaUI.getIngredientes().remove(i);
            }
        }
        return platoDietistaUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public IngredienteImplementandoUI setUpEditIngredienteFromPlatoComida(Integer cantidadId)
    {
        CantidadIngredientePlatoComida c = cantidadIngredientePlatoComidaRepository.findById(cantidadId).orElse(null);
        IngredienteImplementandoUI ingredienteImplementandoUI = new IngredienteImplementandoUI();
        ingredienteImplementandoUI.setIngrediente(c.getIngrediente());
        ingredienteImplementandoUI.setCantidad(c.getCantidad());
        ingredienteImplementandoUI.setTipoCantidad(c.getTipoCantidad());
        return ingredienteImplementandoUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<IngredienteDTO> convertlistEntityToDto(List<Ingrediente> ingredienteList){
        List<IngredienteDTO> ingredienteDTOList = new ArrayList<>();
        for(Ingrediente ingrediente : ingredienteList){
            ingredienteDTOList.add(this.convertEntityToDto(ingrediente));
        }
        return ingredienteDTOList;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<Ingrediente> convertlistDtoToEntity(List<IngredienteDTO> ingredienteDTOList){
        List<Ingrediente> ingredienteList = new ArrayList<>();
        for(IngredienteDTO ingredienteDTO : ingredienteDTOList){
            ingredienteList.add(this.convertDtoToEntity(ingredienteDTO));
        }
        return ingredienteList;
    }

}
