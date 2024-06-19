package es.uma.service;

import es.uma.dto.FeedbackEjercicioDTO;
import es.uma.dto.ImplementacionEjercicioRutinaDTO;
import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackEjercicioService {

    //Feedbackserie e implementacion

    public FeedbackEjercicioDTO convertEntityToDto(FeedbackEjercicio feedbackEjercicio) {
        FeedbackEjercicioDTO feedbackEjercicioDTO = new FeedbackEjercicioDTO();
        feedbackEjercicioDTO.setId(feedbackEjercicio.getId());
        feedbackEjercicioDTO.setSeguimientoKilocaloriasDone(feedbackEjercicio.getSeguimientoKilocaloriasDone());
        feedbackEjercicioDTO.setSeguimientoMetrosDone(feedbackEjercicio.getSeguimientoMetrosDone());
        feedbackEjercicioDTO.setSeguimientoPesoDone(feedbackEjercicio.getSeguimientoPesoDone());
        feedbackEjercicioDTO.setSeguimientoSetsDone(feedbackEjercicio.getSeguimientoSetsDone());
        feedbackEjercicioDTO.setSeguimientoTiempoDone(feedbackEjercicio.getSeguimientoTiempoDone());
        feedbackEjercicioDTO.setRealizado(feedbackEjercicio.getRealizado());
        return feedbackEjercicioDTO;
    }

    public List<FeedbackEjercicioDTO> convertListEntityToDto(List<FeedbackEjercicio> feedbackEjercicioList){
        List<FeedbackEjercicioDTO> feedbackEjercicioDTOList = new ArrayList<>();
        for (FeedbackEjercicio feedbackEjercicio : feedbackEjercicioList){
            feedbackEjercicioDTOList.add(this.convertEntityToDto(feedbackEjercicio));
        }
        return feedbackEjercicioDTOList;
    }

}
