package es.uma.dto;

import lombok.Data;

@Data
public class AsignacionPlatoIngredienteDietistaCreadorDTO {
    private Integer id;
    private PlatoDTO plato;
    private IngredienteDTO ingrediente;
    private UserDTO dietista;
}
