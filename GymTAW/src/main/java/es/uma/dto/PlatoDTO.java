package es.uma.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlatoDTO {
    private Integer id;
    private String nombre;
    private String tiempoDePreparacion;
    private String receta;
    private String enlaceReceta;
    //private List<AsignacionPlatoIngredienteDietistaCreadorDTO> asignaciones;
    //private List<CantidadIngredientePlatoComidaDTO> cantidades;
}
