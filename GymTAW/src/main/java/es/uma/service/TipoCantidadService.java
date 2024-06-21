package es.uma.service;

import es.uma.dao.TipoCantidadRepository;
import es.uma.dao.TipoComidaRepository;
import es.uma.dto.TipoCantidadDTO;
import es.uma.dto.TipoComidaDTO;
import es.uma.entity.TipoCantidad;
import es.uma.entity.TipoComida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoCantidadService {

    @Autowired
    TipoCantidadRepository tipoCantidadRepository;

    public List<TipoCantidadDTO> getAll(){
        return tipoCantidadRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public TipoCantidadDTO getById(Integer id){
        return this.convertEntityToDto(tipoCantidadRepository.findById(id).orElse(null));
    }

    public TipoCantidadDTO convertEntityToDto(TipoCantidad tipoCantidad){
        TipoCantidadDTO tipoCantidadDTO = new TipoCantidadDTO();
        tipoCantidadDTO.setId(tipoCantidad.getId());
        tipoCantidadDTO.setTipoCantidadMedida(tipoCantidad.getTipoCantidadMedida());
        return tipoCantidadDTO;
    }

    public TipoCantidad convertDtoToEntity(TipoCantidadDTO tipoCantidadDTO){
        TipoCantidad tipoCantidad = new TipoCantidad();
        tipoCantidad.setId(tipoCantidadDTO.getId());
        tipoCantidad.setTipoCantidadMedida(tipoCantidadDTO.getTipoCantidadMedida());
        return tipoCantidad;
    }
}

