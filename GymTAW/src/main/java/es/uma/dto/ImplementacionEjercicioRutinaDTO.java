package es.uma.dto;

import es.uma.entity.Ejercicio;
import es.uma.entity.Rutina;
import es.uma.entity.FeedbackEjercicio;
import lombok.Data;

import java.util.List;

@Data
public class ImplementacionEjercicioRutinaDTO {
    private Integer id;
    private Ejercicio ejercicio;
    private Rutina rutina;
    private List<FeedbackEjercicioDTO> feedbacks;
    private String sets;
    private String repeticiones;
    private String peso;
    private String tiempo;
    private String kilocalorias;
    private String metros;
}
