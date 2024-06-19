package es.uma.service;

import es.uma.dto.FeedbackEjercicioDTO;
import es.uma.dto.FeedbackEjercicioserieDTO;
import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.FeedbackEjercicioserie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackEjercicioSerieService {

    //Feedback ejercicio

    public FeedbackEjercicioserieDTO convertEntityToDto(FeedbackEjercicioserie feedbackEjercicioserie) {
        FeedbackEjercicioserieDTO feedbackEjercicioserieDTO = new FeedbackEjercicioserieDTO();
        feedbackEjercicioserieDTO.setId(feedbackEjercicioserie.getId());
        feedbackEjercicioserieDTO.setSerie(feedbackEjercicioserie.getSerie());
        feedbackEjercicioserieDTO.setPesoRealizado(feedbackEjercicioserie.getPesoRealizado());
        feedbackEjercicioserieDTO.setMetrosRealizado(feedbackEjercicioserie.getMetrosRealizado());
        feedbackEjercicioserieDTO.setRepeticionesRealizadas(feedbackEjercicioserie.getRepeticionesRealizadas());
        feedbackEjercicioserieDTO.setKilocaloriasRealizado(feedbackEjercicioserie.getKilocaloriasRealizado());
        feedbackEjercicioserieDTO.setTiempoRealizado(feedbackEjercicioserie.getTiempoRealizado());
        return feedbackEjercicioserieDTO;
    }

    public List<FeedbackEjercicioserieDTO> convertListEntityToDto(List<FeedbackEjercicioserie> feedbackEjercicioseries){
        List<FeedbackEjercicioserieDTO> feedbackEjercicioserieDTOSList = new ArrayList<>();
        for (FeedbackEjercicioserie feedbackEjercicioserie : feedbackEjercicioseries){
            feedbackEjercicioserieDTOSList.add(this.convertEntityToDto(feedbackEjercicioserie));
        }
        return feedbackEjercicioserieDTOSList;
    }
}
