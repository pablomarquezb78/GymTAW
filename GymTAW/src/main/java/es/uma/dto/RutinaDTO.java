package es.uma.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class RutinaDTO {
    private Integer id;
    private Instant fechaCreacion;
    private String nombre;
    private UserDTO entrenador;
    private List<ImplementacionEjercicioRutinaDTO> implementacionesEjercicioRutina;
}
