package es.uma.service;

import es.uma.dao.TipoComidaRepository;
import es.uma.dto.TipoComidaDTO;
import es.uma.entity.TipoComida;
import es.uma.ui.DiaComida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoComidaService {

    @Autowired
    TipoComidaRepository tipoComidaRepository;

    public List<TipoComidaDTO> getAll(){
        return tipoComidaRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public TipoComidaDTO getById(Integer id){
        return this.convertEntityToDto(tipoComidaRepository.findById(id).orElse(null));
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public TipoComidaDTO getSelectedComidaFromDiaComida(DiaComida diaComida)
    {
        return convertEntityToDto(diaComida.getTipoComida());
    }

    public TipoComidaDTO convertEntityToDto(TipoComida tipoComida){
        TipoComidaDTO tipoComidaDTO = new TipoComidaDTO();
        tipoComidaDTO.setId(tipoComida.getId());
        tipoComidaDTO.setComidaDelDia(tipoComida.getComidaDelDia());
        return tipoComidaDTO;
    }

    public TipoComida convertDtoToEntity(TipoComidaDTO tipoComidaDTO){
        TipoComida tipoComida = new TipoComida();
        tipoComida.setId(tipoComidaDTO.getId());
        tipoComida.setComidaDelDia(tipoComidaDTO.getComidaDelDia());
        return tipoComida;
    }
}
