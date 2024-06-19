package es.uma.service;


import es.uma.dao.DiaEntrenamientoRepository;
import es.uma.dto.DiaEntrenamientoDTO;
import es.uma.entity.DiaEntrenamiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaEntrenamientoService {

    @Autowired
    protected DiaEntrenamientoRepository diaEntrenamientoRepository;
    @Autowired
    private UserService userService;

    public List<DiaEntrenamientoDTO> getDiasDeClienteID(Integer idclient){
        List<DiaEntrenamientoDTO> dias = diaEntrenamientoRepository.diasEntrenamientosdeCliente(idclient)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());


        return dias;
    }

    private DiaEntrenamientoDTO convertEntityToDto(DiaEntrenamiento diaEntrenamiento) {
        DiaEntrenamientoDTO diaEntrenamientoDTO = new DiaEntrenamientoDTO();
        diaEntrenamientoDTO.setId(diaEntrenamiento.getId());
        diaEntrenamientoDTO.setFecha(diaEntrenamiento.getFecha());
        diaEntrenamientoDTO.setSeguimiento(diaEntrenamiento.getSeguimiento());
        diaEntrenamientoDTO.setCliente(userService.convertEntityToDto(diaEntrenamiento.getCliente()));
        //diaEntrenamientoDTO.setRutina(convertRutinaEntityToDto(diaEntrenamiento.getRutina()));
        return diaEntrenamientoDTO;
    }

}
