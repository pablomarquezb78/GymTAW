package es.uma.service;

import es.uma.dao.CantidadIngredientePlatoComidaRepository;
import es.uma.dao.ComidaRepository;
import es.uma.dto.*;
import es.uma.entity.*;
import es.uma.ui.AsignacionPlatoComida;
import es.uma.ui.FeedbackDietistaMostrarUI;
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
    @Autowired
    private ComidaRepository comidaRepository;
    @Autowired
    private TipoCantidadService tipoCantidadService;

    public List<PlatoDTO> getPlatosByComida(Integer comidaId){
        return cantidadIngredientePlatoComidaRepository.findPlatosInComida(comidaId)
                .stream()
                .map(platoService::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<CantidadIngredientePlatoComidaDTO> getCantidadesByPlatoComida(Integer platoId,Integer comidaId){
        return cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(platoId,comidaId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public void guardarCantidad(CantidadIngredientePlatoComidaDTO cantidadIngredientePlatoComidaDTO){
        CantidadIngredientePlatoComida cantidadIngredientePlatoComida = cantidadIngredientePlatoComidaRepository.findById(cantidadIngredientePlatoComidaDTO.getId()).orElse(null);
        if(cantidadIngredientePlatoComida!=null){
            cantidadIngredientePlatoComida.setCantidadConsumida(cantidadIngredientePlatoComidaDTO.getCantidadConsumida());
            cantidadIngredientePlatoComidaRepository.save(cantidadIngredientePlatoComida);
        }
    }

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

    public void deleteById(Integer id){
        cantidadIngredientePlatoComidaRepository.deleteById(id);
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public FeedbackDietistaMostrarUI setUpFeedbackComidaSelectedPlato(FeedbackDietistaMostrarUI feedback, ComidaDTO comidaDTO)
    {
        List<CantidadIngredientePlatoComida> listaCantidades = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(feedback.getPlatoMostrando().getId(), comidaDTO.getId());
        feedback.setCantidadesIngredientePlatoComida(listaCantidades);
        return feedback;
    }

    public CantidadIngredientePlatoComidaDTO convertEntityToDto(CantidadIngredientePlatoComida cantidadIngredientePlatoComida){
        CantidadIngredientePlatoComidaDTO cantidadIngredientePlatoComidaDTO = new CantidadIngredientePlatoComidaDTO();
        cantidadIngredientePlatoComidaDTO.setId(cantidadIngredientePlatoComida.getId());
        cantidadIngredientePlatoComidaDTO.setCantidad(cantidadIngredientePlatoComida.getCantidad());
        cantidadIngredientePlatoComidaDTO.setComida(comidaService.convertEntityToDto(cantidadIngredientePlatoComida.getComida()));
        cantidadIngredientePlatoComidaDTO.setCantidadConsumida(cantidadIngredientePlatoComida.getCantidadConsumida());
        if(cantidadIngredientePlatoComida.getIngrediente()!=null){
            cantidadIngredientePlatoComidaDTO.setIngrediente(ingredienteService.convertEntityToDto(cantidadIngredientePlatoComida.getIngrediente()));
        if(cantidadIngredientePlatoComida.getTipoCantidad() != null){
            cantidadIngredientePlatoComidaDTO.setTipoCantidad(tipoCantidadService.convertEntityToDto(cantidadIngredientePlatoComida.getTipoCantidad()));
        }
        return cantidadIngredientePlatoComidaDTO;
    }

    public CantidadIngredientePlatoComida convertDtoToEntity(CantidadIngredientePlatoComidaDTO cantidadIngredientePlatoComidaDTO) {
        CantidadIngredientePlatoComida cantidadIngredientePlatoComida = new CantidadIngredientePlatoComida();
        cantidadIngredientePlatoComida.setId(cantidadIngredientePlatoComidaDTO.getId());
        cantidadIngredientePlatoComida.setCantidad(cantidadIngredientePlatoComidaDTO.getCantidad());
        cantidadIngredientePlatoComida.setComida(comidaService.convertDtoToEntity(cantidadIngredientePlatoComidaDTO.getComida()));
        cantidadIngredientePlatoComida.setCantidadConsumida(cantidadIngredientePlatoComidaDTO.getCantidadConsumida());
        if(cantidadIngredientePlatoComidaDTO.getIngrediente()!=null){
            cantidadIngredientePlatoComida.setIngrediente(ingredienteService.convertDtoToEntity(cantidadIngredientePlatoComidaDTO.getIngrediente()));
        if(cantidadIngredientePlatoComida.getTipoCantidad() != null){
            cantidadIngredientePlatoComida.setTipoCantidad(tipoCantidadService.convertDtoToEntity(cantidadIngredientePlatoComidaDTO.getTipoCantidad()));
        }
        return cantidadIngredientePlatoComida;
    }


}
