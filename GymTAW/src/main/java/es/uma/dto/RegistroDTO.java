package es.uma.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegistroDTO {
    private Integer id;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private Integer telefono;
    private LocalDate fechaNacimiento;
    private Integer rol;

    private List<PlatoDTO> platos;
    private List<EjercicioDTO> ejercicios;
    private List<FeedbackEjercicioDTO> feedbackEjercicios;
    private List<FeedbackEjercicioserieDTO> feedbackEjercicioserie;
    private List<ImplementacionEjercicioRutinaDTO> implementaciones;
}
