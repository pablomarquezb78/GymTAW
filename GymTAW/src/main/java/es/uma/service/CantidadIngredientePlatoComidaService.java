package es.uma.service;

import es.uma.dao.CantidadIngredientePlatoComidaRepository;
import es.uma.dto.CantidadIngredientePlatoComidaDTO;
import es.uma.dto.IngredienteDTO;
import es.uma.dto.PlatoDTO;
import es.uma.dto.TipoComidaDTO;
import es.uma.entity.*;
import es.uma.ui.AsignacionPlatoComida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CantidadIngredientePlatoComidaService {

    @Autowired
    CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;
    @Autowired
    ComidaService comidaService;
    @Autowired
    IngredienteService ingredienteService;
    @Autowired
    PlatoService platoService;
    @Autowired
    TipoComidaService tipoComidaService;
    @Autowired
    private UserService userService;
    @Autowired
    private DiaDietaService diaDietaService;

    public List<CantidadIngredientePlatoComidaDTO> getByDish(Integer id){
        return cantidadIngredientePlatoComidaRepository.buscarPorPlato(id)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<CantidadIngredientePlatoComidaDTO> dishFilter(PlatoDTO platoDTO, String nombreCliente, String nombreDietista, Integer tipoComidaId){
        Plato plato = platoService.convertDtoToEntity(platoDTO);
        TipoComida tipoComida = tipoComidaService.convertDtoToEntity(tipoComidaService.getById(tipoComidaId));
        return cantidadIngredientePlatoComidaRepository.filtrarPlatos(plato, nombreCliente, nombreDietista, tipoComida)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

    }

    public CantidadIngredientePlatoComidaDTO getById(Integer id){
        return this.convertEntityToDto(cantidadIngredientePlatoComidaRepository.findById(id).orElse(null));
    }

    public List<CantidadIngredientePlatoComidaDTO> dishFilterWithCuantity(PlatoDTO platoDTO, String nombreCliente, String nombreDietista, Integer tipoComidaId, Integer cantidad){
        Plato plato = platoService.convertDtoToEntity(platoDTO);
        TipoComida tipoComida = tipoComidaService.convertDtoToEntity(tipoComidaService.getById(tipoComidaId));
        return cantidadIngredientePlatoComidaRepository.filtrarPlatosConCantidad(plato, nombreCliente, nombreDietista, tipoComida, cantidad)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

    }

    public List<IngredienteDTO> getIngredientsByDish(Integer id){
        return cantidadIngredientePlatoComidaRepository.buscarIngredientesPorPlato(id)
                .stream()
                .map(ingredienteService::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public AsignacionPlatoComida setAsignacionPlatoComida(AsignacionPlatoComida asignacionPlatoComida, Integer idComida, Integer idPlato){
        CantidadIngredientePlatoComida apidc = cantidadIngredientePlatoComidaRepository.findById(idComida).orElse(null);
        asignacionPlatoComida.setIdCliente(apidc.getComida().getDiaDieta().getCliente().getId());
        asignacionPlatoComida.setIdCliente(apidc.getComida().getDiaDieta().getDietista().getId());
        asignacionPlatoComida.setCantidad(apidc.getCantidad());
        asignacionPlatoComida.setTipoComida(apidc.getComida().getTipoComida().getId());
        asignacionPlatoComida.setIdComida(apidc.getId());
        asignacionPlatoComida.setIdPlato(apidc.getPlato().getId());
        asignacionPlatoComida.setFecha(apidc.getComida().getDiaDieta().getFecha().toString());
        asignacionPlatoComida.setIdComida(apidc.getId());

        return asignacionPlatoComida;

    }

    public void saveFood(AsignacionPlatoComida asignacionPlatoComida){
        CantidadIngredientePlatoComida cipc = new CantidadIngredientePlatoComida();
        Plato plato = platoService.convertDtoToEntity(platoService.getById(asignacionPlatoComida.getIdPlato()));
        Comida comida = new Comida();
        DiaDieta diaDieta = new DiaDieta();

        diaDieta.setCliente(userService.convertDtoToEntity(userService.getById(asignacionPlatoComida.getIdCliente())));
        diaDieta.setDietista(userService.convertDtoToEntity(userService.getById(asignacionPlatoComida.getIdDietista())));
        diaDieta.setFecha( LocalDate.parse(asignacionPlatoComida.getFecha()));
        diaDietaService.save(diaDieta);

        comida.setTipoComida(tipoComidaService.convertDtoToEntity(tipoComidaService.getById(asignacionPlatoComida.getTipoComida())));
        comida.setDiaDieta(diaDieta);
        comidaService.save(comida);

        cipc.setPlato(plato);
        cipc.setCantidad(asignacionPlatoComida.getCantidad());
        cipc.setComida(comida);
        cantidadIngredientePlatoComidaRepository.save(cipc);
    }

    public void editFood(AsignacionPlatoComida asignacionPlatoComida){
        CantidadIngredientePlatoComida cipc = cantidadIngredientePlatoComidaRepository.findById(asignacionPlatoComida.getIdComida()).orElse(null);
        Comida comida = cipc.getComida();
        DiaDieta diaDieta = comida.getDiaDieta();

        diaDieta.setCliente(userService.convertDtoToEntity(userService.getById(asignacionPlatoComida.getIdCliente())));
        diaDieta.setDietista(userService.convertDtoToEntity(userService.getById(asignacionPlatoComida.getIdDietista())));
        diaDieta.setFecha( LocalDate.parse(asignacionPlatoComida.getFecha()));
        diaDietaService.save(diaDieta);

        comida.setTipoComida(tipoComidaService.convertDtoToEntity(tipoComidaService.getById(asignacionPlatoComida.getTipoComida())));
        comida.setDiaDieta(diaDieta);
        comidaService.save(comida);

        cipc.setCantidad(asignacionPlatoComida.getCantidad());
        cipc.setComida(comida);
        cantidadIngredientePlatoComidaRepository.save(cipc);
    }

    public CantidadIngredientePlatoComidaDTO convertEntityToDto(CantidadIngredientePlatoComida cantidadIngredientePlatoComida){
        CantidadIngredientePlatoComidaDTO cantidadIngredientePlatoComidaDTO = new CantidadIngredientePlatoComidaDTO();
        cantidadIngredientePlatoComidaDTO.setId(cantidadIngredientePlatoComida.getId());
        cantidadIngredientePlatoComidaDTO.setCantidad(cantidadIngredientePlatoComida.getCantidad());
        cantidadIngredientePlatoComidaDTO.setComida(comidaService.convertEntityToDto(cantidadIngredientePlatoComida.getComida()));
        return cantidadIngredientePlatoComidaDTO;

    }

}
