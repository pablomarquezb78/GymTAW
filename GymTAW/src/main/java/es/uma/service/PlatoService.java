package es.uma.service;


import es.uma.dao.PlatosRepository;
import es.uma.dto.*;
import es.uma.entity.*;
import es.uma.ui.PlatoDietistaUI;
import es.uma.ui.PlatoUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatoService {

    @Autowired
    public PlatosRepository platosRepository;
    @Autowired
    private UserService userService;


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

    public PlatoDTO findById(Integer platoId)
    {
        Plato p = platosRepository.findPlatoById(platoId);
        PlatoDTO platoDTO = this.convertEntityToDto(p);
        return platoDTO;
    }

    public List<PlatoDTO> getPlatosLinkedToDietista(UserDTO dietistaDTO)
    {
        User dietista = userService.convertDtoToEntity(dietistaDTO);
        List<Plato> platosList = platosRepository.getPlatosLinkedToDietista(dietista);
        List<PlatoDTO> platosDTO = convertlistEntityToDto(platosList);
        return platosDTO;
    }

    public PlatoDietistaUI prepareEditarPlatoByPlatoDietistaUI(Integer platoId)
    {
        Plato plato = platosRepository.findById(platoId).orElse(null);
        PlatoDietistaUI platoDietista = new PlatoDietistaUI();

        platoDietista.setId(plato.getId());
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.addAll(platosRepository.getIngredientesLinkedToPlato(plato));
        platoDietista.setIngredientes(ingredientes);
        platoDietista.setNombre(plato.getNombre());
        platoDietista.setReceta(plato.getReceta());
        platoDietista.setEnlaceReceta(plato.getEnlaceReceta());
        platoDietista.setTiempoDePreparacion(plato.getTiempoDePreparacion());
        return platoDietista;
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

    public List<PlatoDTO> convertlistEntityToDto(List<Plato> platoList){
        List<PlatoDTO> platoDTOList = new ArrayList<>();
        for(Plato plato : platoList){
            platoDTOList.add(this.convertEntityToDto(plato));
        }
        return platoDTOList;
    }

    public List<Plato> convertlistDtoToEntity(List<PlatoDTO> platoDTOList){
        List<Plato> platoList = new ArrayList<>();
        for(PlatoDTO plato : platoDTOList){
            platoList.add(this.convertDtoToEntity(plato));
        }
        return platoList;
    }
}
