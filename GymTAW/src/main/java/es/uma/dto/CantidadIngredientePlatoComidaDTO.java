package es.uma.dto;

import lombok.Data;

@Data
public class CantidadIngredientePlatoComidaDTO {
    private Integer id;
    private Integer cantidad;
    private TipoCantidadDTO tipoCantidad;
    private Integer cantidadConsumida;
    private IngredienteDTO ingrediente;
    private PlatoDTO plato;
    private ComidaDTO comida;
}
