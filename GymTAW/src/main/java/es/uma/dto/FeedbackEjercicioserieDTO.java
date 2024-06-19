package es.uma.dto;

import es.uma.entity.FeedbackEjercicio;
import lombok.Data;

@Data
public class FeedbackEjercicioserieDTO {
    private Integer id;
    private String serie;
    private String repeticionesRealizadas;
    private String pesoRealizado;
    private String tiempoRealizado;
    private String kilocaloriasRealizado;
    private String metrosRealizado;
    private FeedbackEjercicioDTO feedbackEjercicio;
}
