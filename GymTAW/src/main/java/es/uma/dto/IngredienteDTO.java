package es.uma.dto;

import lombok.Data;

@Data
public class IngredienteDTO {
    private Integer id;
    private String nombre;
    private String kilocalorias;
    private String proteinas;
    private String grasas;
    private String azucares;
    private String hidratosDeCarbono;
}
