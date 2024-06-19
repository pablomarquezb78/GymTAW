package es.uma.dto;

import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.User;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class RutinaDTO {
    private Integer id;
    private Instant fechaCreacion;
    private String nombre;
    private User entrenador;
    private List<ImplementacionEjercicioRutina> implementacionesEjercicioRutina;
}
