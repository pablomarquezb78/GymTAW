package es.uma.service;

import es.uma.dao.ComidaRepository;
import es.uma.dto.ComidaDTO;
import es.uma.entity.Comida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComidaService {

    @Autowired
    ComidaRepository comidaRepository;
    @Autowired
    DiaDietaService diaDietaService;
    @Autowired
    TipoComidaService tipoComidaService;

    public void save(Comida comida){
        comidaRepository.save(comida);
    }

    public ComidaDTO convertEntityToDto(Comida comida){
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comida.getId());
        comidaDTO.setTipoComida(tipoComidaService.convertEntityToDto(comida.getTipoComida()));
        comidaDTO.setDiaDieta(diaDietaService.convertEntityToDto(comida.getDiaDieta()));
        return comidaDTO;
    }
}
