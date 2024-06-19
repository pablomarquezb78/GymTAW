package es.uma.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiaDietaDTO {
    private Integer id;
    private LocalDate fecha;
    private String seguimiento;
    private UserDTO cliente;
    private UserDTO dietista;
}
