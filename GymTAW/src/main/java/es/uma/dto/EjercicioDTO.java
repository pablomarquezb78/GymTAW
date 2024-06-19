package es.uma.dto;

import es.uma.entity.TipoEjercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import lombok.Data;

import java.util.List;

@Data
public class EjercicioDTO {
    private Integer id;
    private String nombre;
    private TipoEjercicioDTO tipo;
    private String descripcion;
    private String enlaceVideo;
    private List<ImplementacionEjercicioRutinaDTO> implementaciones;
}