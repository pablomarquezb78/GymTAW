package es.uma.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiaEntrenamientoDTO {
    private Integer id;
    private LocalDate fecha;
    private String seguimiento;
    private UserDTO cliente;
    private RutinaDTO rutina;
}
