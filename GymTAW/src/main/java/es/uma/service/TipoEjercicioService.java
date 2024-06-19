package es.uma.service;

import es.uma.dto.EjercicioDTO;
import es.uma.dto.TipoEjercicioDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.TipoEjercicio;
import org.springframework.stereotype.Service;

@Service
public class TipoEjercicioService {

    public TipoEjercicioDTO convertEntityToDto(TipoEjercicio tipoEjercicio) {
        TipoEjercicioDTO tipoEjercicioDTO = new TipoEjercicioDTO();
        tipoEjercicioDTO.setId(tipoEjercicio.getId());
        tipoEjercicioDTO.setTipoDeEjercicio(tipoEjercicio.getTipoDeEjercicio());
        return tipoEjercicioDTO;
    }
}
