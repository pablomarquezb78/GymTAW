package es.uma.service;

import es.uma.dao.DiaEntrenamientoRepository;
import es.uma.dao.FeedbackejercicioRepository;
import es.uma.dao.ImplementacionEjercicioRutinaRepository;
import es.uma.dto.DiaEntrenamientoDTO;
import es.uma.dto.FeedbackEjercicioDTO;
import es.uma.dto.ImplementacionEjercicioRutinaDTO;
import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackEjercicioService {

    @Autowired
    private FeedbackejercicioRepository feedbackejercicioRepository;
    @Autowired
    private ImplementacionEjercicioRutinaService implementacionEjercicioRutinaService;
    @Autowired
    private ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    private DiaEntrenamientoRepository diaEntrenamientoRepository;
    @Autowired
    private FeedbackEjercicioSerieService feedbackEjercicioSerieService;

    //Feedbackserie e implementacion

    //@author: Pablo Márquez Benítez
    public FeedbackEjercicioDTO getFeedbackEjercicioPorImplementacionYDia(ImplementacionEjercicioRutinaDTO implementacion, DiaEntrenamientoDTO diaEntrenamiento){
        FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYDia(implementacion.getId(),diaEntrenamiento.getId());
        if(feedbackEjercicio!=null){
            return convertEntityToDto(feedbackEjercicio);
        }else{
            return null;
        }
    }

    //@author: Pablo Márquez Benítez
    public FeedbackEjercicioDTO getFeedbackEjercicioById(Integer id){
        FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.findById(id).orElse(null);
        if(feedbackEjercicio!=null){
            return convertEntityToDto(feedbackEjercicio);
        }else{
            return null;
        }
    }

    //@author: Pablo Márquez Benítez
    public FeedbackEjercicioDTO createFeedbackEjercicio(Integer diaEntrenamientoId, Integer implementacionId) {
        DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.findById(diaEntrenamientoId).orElse(null);
        ImplementacionEjercicioRutina implementacionEjercicioRutina = implementacionEjercicioRutinaRepository.findById(implementacionId).orElse(null);

        FeedbackEjercicio feedbackEjercicio = new FeedbackEjercicio();
        feedbackEjercicio.setDiaEntrenamiento(diaEntrenamiento);
        feedbackEjercicio.setImplementacion(implementacionEjercicioRutina);
        feedbackEjercicio.setRealizado((byte) 0);
        feedbackejercicioRepository.save(feedbackEjercicio);

        FeedbackEjercicioDTO feedbackEjercicioDTO = new FeedbackEjercicioDTO();
        feedbackEjercicioDTO.setId(feedbackEjercicio.getId());
        feedbackEjercicioDTO.setRealizado(feedbackEjercicio.getRealizado());
        feedbackEjercicioDTO.setImplementacion(implementacionEjercicioRutinaService.convertEntityToDto(feedbackEjercicio.getImplementacion()));

        return feedbackEjercicioDTO;
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
        feedbackEjercicioDTO.setFeedbacks(feedbackEjercicioSerieService.convertListEntityToDto(feedbackEjercicio.getFeedbacks()));
        feedbackEjercicioDTO.setImplementacion(implementacionEjercicioRutinaService.convertEntityToDto(feedbackEjercicio.getImplementacion()));
        return feedbackEjercicioDTO;
    }

    //@author: Pablo Márquez Benítez
    public FeedbackEjercicio convertDtoToEntity(FeedbackEjercicioDTO feedbackEjercicioDTO) {
        FeedbackEjercicio feedbackEjercicio = new FeedbackEjercicio();
        feedbackEjercicio.setId(feedbackEjercicioDTO.getId());
        feedbackEjercicio.setSeguimientoKilocaloriasDone(feedbackEjercicioDTO.getSeguimientoKilocaloriasDone());
        feedbackEjercicio.setSeguimientoMetrosDone(feedbackEjercicioDTO.getSeguimientoMetrosDone());
        feedbackEjercicio.setSeguimientoPesoDone(feedbackEjercicioDTO.getSeguimientoPesoDone());
        feedbackEjercicio.setSeguimientoSetsDone(feedbackEjercicioDTO.getSeguimientoSetsDone());
        feedbackEjercicio.setSeguimientoTiempoDone(feedbackEjercicioDTO.getSeguimientoTiempoDone());
        feedbackEjercicio.setRealizado(feedbackEjercicioDTO.getRealizado());
        feedbackEjercicio.setFeedbacks(feedbackEjercicioSerieService.convertListDtoToEntity(feedbackEjercicioDTO.getFeedbacks()));
        feedbackEjercicio.setImplementacion(implementacionEjercicioRutinaService.convertDtoToEntity(feedbackEjercicioDTO.getImplementacion()));
        return feedbackEjercicio;
    }

    //@author: Pablo Márquez Benítez
    public void guardarFeedbackEjercicio(FeedbackEjercicioDTO feedbackEjercicioDTO){
        FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.findById(feedbackEjercicioDTO.getId()).orElse(null);
        if(feedbackEjercicio!=null){
            feedbackEjercicio.setRealizado(feedbackEjercicioDTO.getRealizado());
            feedbackEjercicio.setSeguimientoSetsDone(feedbackEjercicioDTO.getSeguimientoSetsDone());
            feedbackEjercicio.setSeguimientoMetrosDone(feedbackEjercicioDTO.getSeguimientoMetrosDone());
            feedbackEjercicio.setSeguimientoTiempoDone(feedbackEjercicioDTO.getSeguimientoTiempoDone());
            feedbackEjercicio.setSeguimientoKilocaloriasDone(feedbackEjercicioDTO.getSeguimientoKilocaloriasDone());
            feedbackEjercicio.setSeguimientoPesoDone(feedbackEjercicioDTO.getSeguimientoPesoDone());
            feedbackejercicioRepository.save(feedbackEjercicio);
        }
    }

}
