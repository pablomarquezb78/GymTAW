package es.uma.service;


import es.uma.dao.DiaEntrenamientoRepository;
import es.uma.dto.DiaEntrenamientoDTO;
import es.uma.entity.DiaEntrenamiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaEntrenamientoService {

    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RutinaService rutinaService;

    public DiaEntrenamientoDTO getbyID(Integer id) {
        return convertEntityToDto(diaEntrenamientoRepository.getById(id));
    }

    public void guardarDiaEntrenamiento(DiaEntrenamientoDTO diaEntrenamientoDTO){
        DiaEntrenamiento diaEntrenamiento = convertDtoToEntity(diaEntrenamientoDTO);
        diaEntrenamientoRepository.save(diaEntrenamiento);
    }

    public List<DiaEntrenamientoDTO> getDiasDeClienteID(Integer idclient){
        List<DiaEntrenamientoDTO> dias = diaEntrenamientoRepository.diasEntrenamientosdeCliente(idclient)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return dias;
    }

    public DiaEntrenamientoDTO getDiaEntrenamientoDeClienteFecha(Integer idClient, LocalDate fecha){
        DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.diaEntrenamientoConcretoCliente(idClient,fecha);
        if(diaEntrenamiento!=null){
            return convertEntityToDto(diaEntrenamiento);
        }else{
            return null;
        }
    }

    public DiaEntrenamientoDTO convertEntityToDto(DiaEntrenamiento diaEntrenamiento) {
        DiaEntrenamientoDTO diaEntrenamientoDTO = new DiaEntrenamientoDTO();
        diaEntrenamientoDTO.setId(diaEntrenamiento.getId());
        diaEntrenamientoDTO.setFecha(diaEntrenamiento.getFecha());
        diaEntrenamientoDTO.setSeguimiento(diaEntrenamiento.getSeguimiento());
        diaEntrenamientoDTO.setCliente(userService.convertEntityToDto(diaEntrenamiento.getCliente()));
        diaEntrenamientoDTO.setRutina(rutinaService.convertEntityToDto(diaEntrenamiento.getRutina()));
        return diaEntrenamientoDTO;
    }

    public DiaEntrenamiento convertDtoToEntity(DiaEntrenamientoDTO diaEntrenamientoDTO) {
        DiaEntrenamiento diaEntrenamiento = new DiaEntrenamiento();
        diaEntrenamiento.setId(diaEntrenamientoDTO.getId());
        diaEntrenamiento.setFecha(diaEntrenamientoDTO.getFecha());
        diaEntrenamiento.setSeguimiento(diaEntrenamientoDTO.getSeguimiento());
        diaEntrenamiento.setCliente(userService.convertDtoToEntity(diaEntrenamientoDTO.getCliente()));
        diaEntrenamiento.setRutina(rutinaService.convertDtoToEntity(diaEntrenamientoDTO.getRutina()));
        return diaEntrenamiento;
    }


}
