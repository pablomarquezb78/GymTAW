package es.uma.dto;

import lombok.Data;

@Data
public class AsignacionClienteEntrenadorDTO {
    private Integer id;
    private UserDTO cliente;
    private UserDTO entrenador;
}
