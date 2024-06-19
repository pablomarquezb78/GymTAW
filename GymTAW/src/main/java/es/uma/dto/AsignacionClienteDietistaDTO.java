package es.uma.dto;

import lombok.Data;

@Data
public class AsignacionClienteDietistaDTO {
    private Integer id;
    private UserDTO cliente;
    private UserDTO dietista;
}
