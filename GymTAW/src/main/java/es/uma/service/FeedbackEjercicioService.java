package es.uma.service;

import es.uma.dao.FeedbackejercicioRepository;
import es.uma.dto.DiaEntrenamientoDTO;
import es.uma.dto.FeedbackEjercicioDTO;
import es.uma.dto.ImplementacionEjercicioRutinaDTO;
import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackEjercicioService {

    @Autowired
    private FeedbackejercicioRepository feedbackejercicioRepository;
    @Autowired
    private ImplementacionEjercicioRutinaService implementacionEjercicioRutinaService;
    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private DiaEntrenamientoService diaEntrenamientoService;

    //Feedbackserie e implementacion

    public FeedbackEjercicioDTO getFeedbackEjercicioPorImplementacionYDia(ImplementacionEjercicioRutinaDTO implementacion, DiaEntrenamientoDTO diaEntrenamiento){
        ImplementacionEjercicioRutina implementacionEntity = implementacionEjercicioRutinaService.convertDtoToEntity(implementacion);
        DiaEntrenamiento diaEntrenamientoEntity = diaEntrenamientoService.convertDtoToEntity(diaEntrenamiento);
        return convertEntityToDto(feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYDia(implementacionEntity,diaEntrenamientoEntity));
    }

    public void createFeedbackEjercicio(DiaEntrenamientoDTO diaEntrenamiento, ImplementacionEjercicioRutinaDTO implementacion){
        FeedbackEjercicio feedbackEjercicio = new FeedbackEjercicio();
        DiaEntrenamiento diaEntrenamientoEntity = diaEntrenamientoService.convertDtoToEntity(diaEntrenamiento);
        feedbackEjercicio.setDiaEntrenamiento(diaEntrenamientoEntity);
        ImplementacionEjercicioRutina implementacionEjercicioRutinaEntity = implementacionEjercicioRutinaService.convertDtoToEntity(implementacion);
        feedbackEjercicio.setImplementacion(implementacionEjercicioRutinaEntity);
        feedbackEjercicio.setRealizado((byte) 0);
        feedbackejercicioRepository.save(feedbackEjercicio);
    }

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

    public FeedbackEjercicio convertDtoToEntity(FeedbackEjercicioDTO feedbackEjercicioDTO) {
        FeedbackEjercicio feedbackEjercicio = new FeedbackEjercicio();
        feedbackEjercicio.setId(feedbackEjercicioDTO.getId());
        feedbackEjercicio.setSeguimientoKilocaloriasDone(feedbackEjercicioDTO.getSeguimientoKilocaloriasDone());
        feedbackEjercicio.setSeguimientoMetrosDone(feedbackEjercicioDTO.getSeguimientoMetrosDone());
        feedbackEjercicio.setSeguimientoPesoDone(feedbackEjercicioDTO.getSeguimientoPesoDone());
        feedbackEjercicio.setSeguimientoSetsDone(feedbackEjercicioDTO.getSeguimientoSetsDone());
        feedbackEjercicio.setSeguimientoTiempoDone(feedbackEjercicioDTO.getSeguimientoTiempoDone());
        feedbackEjercicio.setRealizado(feedbackEjercicioDTO.getRealizado());
        return feedbackEjercicio;
    }

    public List<FeedbackEjercicioDTO> convertListEntityToDto(List<FeedbackEjercicio> feedbackEjercicioList){
        List<FeedbackEjercicioDTO> feedbackEjercicioDTOList = new ArrayList<>();
        for (FeedbackEjercicio feedbackEjercicio : feedbackEjercicioList){
            feedbackEjercicioDTOList.add(this.convertEntityToDto(feedbackEjercicio));
        }
        return feedbackEjercicioDTOList;
    }

}
