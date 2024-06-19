package es.uma.dto;

import lombok.Data;

@Data
public class ComidaDTO {
    private Integer id;
    private Byte realizado;
    private TipoComidaDTO tipoComida;
    private DiaDietaDTO diaDieta;
}
