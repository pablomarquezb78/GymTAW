package es.uma.service;


import es.uma.dao.AsignacionPlatoIngredienteDietistacreadorRepositoy;
import es.uma.dao.IngredienteRepository;
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
    private IngredienteRepository ingredienteRepository;
    @Autowired
    private AsignacionPlatoIngredienteDietistacreadorRepositoy asignacionPlatoIngredienteDietistacreadorRepositoy;
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public PlatoDTO findById(Integer platoId)
    {
        Plato p = platosRepository.findPlatoById(platoId);
        PlatoDTO platoDTO = this.convertEntityToDto(p);
        return platoDTO;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<PlatoDTO> getPlatosLinkedToDietista(UserDTO dietistaDTO)
    {
        User dietista = userService.convertDtoToEntity(dietistaDTO);
        List<Plato> platosList = platosRepository.getPlatosLinkedToDietista(dietista);
        List<PlatoDTO> platosDTO = convertlistEntityToDto(platosList);
        return platosDTO;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public void crearPlatoByPlatoDietstaUI(PlatoDietistaUI platoDietistaUI, UserDTO userDTO)
    {
        Plato plato = new Plato();
        plato.setNombre(platoDietistaUI.getNombre());
        plato.setTiempoDePreparacion(platoDietistaUI.getTiempoDePreparacion());
        plato.setReceta(platoDietistaUI.getReceta());
        plato.setEnlaceReceta(platoDietistaUI.getEnlaceReceta());
        platosRepository.saveAndFlush(plato);
        if(platoDietistaUI.getIngredientes() != null)
        {
            for(Ingrediente ingrediente : platoDietistaUI.getIngredientes())
            {
                AsignacionPlatoIngredienteDietistaCreador asignacion = new AsignacionPlatoIngredienteDietistaCreador();
                asignacion.setPlato(platosRepository.getUltimoPlatoAdded());
                User user = userService.convertDtoToEntity(userDTO);
                asignacion.setDietista(user);
                asignacion.setIngrediente(ingredienteRepository.findById(ingrediente.getId()).orElse(null));
                asignacionPlatoIngredienteDietistacreadorRepositoy.saveAndFlush(asignacion);
            }
        }
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public void editarPlatoByPlatoDietistaUI(PlatoDietistaUI platoDietistaUI, UserDTO userDTO)
    {
        Plato plato = platosRepository.findById(platoDietistaUI.getId()).orElse(null);
        plato.setNombre(platoDietistaUI.getNombre());
        plato.setReceta(platoDietistaUI.getReceta());
        plato.setEnlaceReceta(platoDietistaUI.getEnlaceReceta());
        plato.setTiempoDePreparacion(platoDietistaUI.getTiempoDePreparacion());
        platosRepository.save(plato);
        if(platoDietistaUI.getIngredientes() != null)
        {
            List<Ingrediente> ingredientesPrevios = platosRepository.getIngredientesLinkedToPlato(plato);
            if(!ingredientesPrevios.equals(platoDietistaUI.getIngredientes()))
            {
                for(Ingrediente ingrediente : ingredientesPrevios)
                {
                    User dietista = userService.convertDtoToEntity(userDTO);
                    AsignacionPlatoIngredienteDietistaCreador asignacion =
                            asignacionPlatoIngredienteDietistacreadorRepositoy.getAsignacionBy(ingrediente, plato, dietista).getFirst();
                    asignacionPlatoIngredienteDietistacreadorRepositoy.delete(asignacion);
                }
                for(Ingrediente ingrediente : platoDietistaUI.getIngredientes())
                {
                    AsignacionPlatoIngredienteDietistaCreador asignacion = new AsignacionPlatoIngredienteDietistaCreador();
                    asignacion.setPlato(platosRepository.getUltimoPlatoAdded());
                    User dietista = userService.convertDtoToEntity(userDTO);
                    asignacion.setDietista(dietista);
                    asignacion.setIngrediente(ingredienteRepository.findById(ingrediente.getId()).orElse(null));
                    asignacionPlatoIngredienteDietistacreadorRepositoy.saveAndFlush(asignacion);
                }
            }
        }
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public void borrarPlatoByPlatoId(Integer platoId, UserDTO userDTO)
    {
        Plato plato = platosRepository.findById(platoId).orElse(null);

        List<Ingrediente> ingredientes = platosRepository.getIngredientesLinkedToPlato(plato);
        for(Ingrediente ingrediente : ingredientes)
        {
            User dietista = userService.convertDtoToEntity(userDTO);
            AsignacionPlatoIngredienteDietistaCreador asignacion =
                    asignacionPlatoIngredienteDietistacreadorRepositoy.getAsignacionBy(ingrediente, plato, dietista).getFirst();
            asignacionPlatoIngredienteDietistacreadorRepositoy.delete(asignacion);
        }
        platosRepository.delete(plato);
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public PlatoDietistaUI addNewIngredienteToPlatoDietistaUI(IngredienteDTO ingredienteDTO, PlatoDietistaUI platoDietistaUI)
    {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(ingredienteDTO.getId());
        ingrediente.setNombre(ingredienteDTO.getNombre());
        ingrediente.setAzucares(ingredienteDTO.getAzucares());
        ingrediente.setGrasas(ingredienteDTO.getGrasas());
        ingrediente.setKilocalorias(ingredienteDTO.getKilocalorias());
        ingrediente.setProteinas(ingredienteDTO.getProteinas());
        ingrediente.setHidratosDeCarbono(ingredienteDTO.getHidratosDeCarbono());
        ingredienteRepository.save(ingrediente);
        if (platoDietistaUI.getIngredientes() == null) platoDietistaUI.setIngredientes(new ArrayList<>());
        platoDietistaUI.getIngredientes().add(ingredienteRepository.getUltimosIngredientesAdded().getFirst());
        return platoDietistaUI;
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<PlatoDTO> convertlistEntityToDto(List<Plato> platoList){
        List<PlatoDTO> platoDTOList = new ArrayList<>();
        for(Plato plato : platoList){
            platoDTOList.add(this.convertEntityToDto(plato));
        }
        return platoDTOList;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<Plato> convertlistDtoToEntity(List<PlatoDTO> platoDTOList){
        List<Plato> platoList = new ArrayList<>();
        for(PlatoDTO plato : platoDTOList){
            platoList.add(this.convertDtoToEntity(plato));
        }
        return platoList;
    }
}
