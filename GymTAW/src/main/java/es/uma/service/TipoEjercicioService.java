package es.uma.service;

import es.uma.dao.TipoEjercicioRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.TipoEjercicioDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.TipoEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoEjercicioService {

    @Autowired
    private TipoEjercicioRepository tipoEjercicioRepository;

    public List<TipoEjercicioDTO> getAll(){
        return tipoEjercicioRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public TipoEjercicioDTO getById(Integer id){
        return convertEntityToDto(tipoEjercicioRepository.findById(id).orElse(null));
    }

    public TipoEjercicioDTO convertEntityToDto(TipoEjercicio tipoEjercicio) {
        TipoEjercicioDTO tipoEjercicioDTO = new TipoEjercicioDTO();
        tipoEjercicioDTO.setId(tipoEjercicio.getId());
        tipoEjercicioDTO.setTipoDeEjercicio(tipoEjercicio.getTipoDeEjercicio());
        return tipoEjercicioDTO;
    }

    public TipoEjercicio convertDtoToEntity(TipoEjercicioDTO tipoEjercicioDTO) {
        TipoEjercicio tipoEjercicio = new TipoEjercicio();
        tipoEjercicio.setId(tipoEjercicioDTO.getId());
        tipoEjercicio.setTipoDeEjercicio(tipoEjercicioDTO.getTipoDeEjercicio());
        return tipoEjercicio;
    }
}
