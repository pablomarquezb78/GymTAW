package es.uma.service;

import es.uma.dao.ComidaRepository;
import es.uma.dao.DiaDietaRepository;
import es.uma.dto.ComidaDTO;
import es.uma.entity.Comida;
import es.uma.entity.DiaDieta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComidaService {

    @Autowired
    DiaDietaRepository diaDietaRepository;
    @Autowired
    ComidaRepository comidaRepository;
    @Autowired
    DiaDietaService diaDietaService;
    @Autowired
    TipoComidaService tipoComidaService;

    public void save(Comida comida){
        comidaRepository.save(comida);
    }

    public void guardarComida(ComidaDTO comidaDTO){
        Comida comida = convertDtoToEntity(comidaDTO);
        comidaRepository.save(comida);
    }

    public ComidaDTO getComidaByID(Integer id){
        Comida comida = comidaRepository.findById(id).orElse(null);
        if(comida!=null){
            return convertEntityToDto(comida);
        }else{
            return null;
        }
    }

    public List<ComidaDTO> getComidasByDiaDieta(Integer diaDietaID){
        if(diaDietaID!=null){
            return comidaRepository.findByDiaDieta(diaDietaID)
                    .stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }

    public ComidaDTO convertEntityToDto(Comida comida){
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comida.getId());
        comidaDTO.setTipoComida(tipoComidaService.convertEntityToDto(comida.getTipoComida()));
        comidaDTO.setDiaDieta(diaDietaService.convertEntityToDto(comida.getDiaDieta()));
        return comidaDTO;
    }

    public Comida convertDtoToEntity(ComidaDTO comidaDTO) {
        Comida comida = new Comida();
        comida.setId(comidaDTO.getId());
        comida.setTipoComida(tipoComidaService.convertDtoToEntity(comidaDTO.getTipoComida()));
        comida.setDiaDieta(diaDietaService.convertDtoToEntity(comidaDTO.getDiaDieta()));
        return comida;
    }

}
