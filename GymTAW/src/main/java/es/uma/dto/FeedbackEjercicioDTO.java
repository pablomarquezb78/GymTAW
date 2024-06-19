package es.uma.dto;

import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.FeedbackEjercicioserie;
import es.uma.entity.ImplementacionEjercicioRutina;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackEjercicioDTO {
    private Integer id;
    private ImplementacionEjercicioRutinaDTO implementacion;
    private DiaEntrenamientoDTO diaEntrenamiento;
    private Byte realizado;
    private String seguimientoSetsDone;
    private String seguimientoTiempoDone;
    private String seguimientoKilocaloriasDone;
    private String seguimientoMetrosDone;
    private String seguimientoPesoDone;
    private List<FeedbackEjercicioserieDTO> feedbacks;
}
