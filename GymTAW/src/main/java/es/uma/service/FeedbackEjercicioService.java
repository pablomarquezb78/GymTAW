package es.uma.service;

import es.uma.dao.DiaDietaRepository;
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
    private EjercicioService ejercicioService;
    @Autowired
    private DiaEntrenamientoService diaEntrenamientoService;
    @Autowired
    private DiaDietaRepository diaDietaRepository;
    @Autowired
    private ImplementacionEjercicioRutinaRepository implementacionEjercicioRutinaRepository;
    @Autowired
    private DiaEntrenamientoRepository diaEntrenamientoRepository;

    //Feedbackserie e implementacion

    public FeedbackEjercicioDTO getFeedbackEjercicioPorImplementacionYDia(ImplementacionEjercicioRutinaDTO implementacion, DiaEntrenamientoDTO diaEntrenamiento){
        return convertEntityToDto(feedbackejercicioRepository.encontrarFeedbackEjercicioPorImplementacionYDia(implementacion.getId(),diaEntrenamiento.getId()));
    }

    public FeedbackEjercicioDTO getFeedbackEjercicioById(Integer id){
        return convertEntityToDto(feedbackejercicioRepository.findById(id).orElse(null));
    }

    public void createFeedbackEjercicio(Integer diaEntrenamientoId, Integer implementacionId){
        DiaEntrenamiento diaEntrenamiento = diaEntrenamientoRepository.findById(diaEntrenamientoId).orElse(null);
        ImplementacionEjercicioRutina implementacionEjercicioRutina = implementacionEjercicioRutinaRepository.findById(implementacionId).orElse(null);
        FeedbackEjercicio feedbackEjercicio = new FeedbackEjercicio();
        feedbackEjercicio.setDiaEntrenamiento(diaEntrenamiento);
        feedbackEjercicio.setImplementacion(implementacionEjercicioRutina);
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

    public void guardarFeedbackEjercicio(FeedbackEjercicioDTO feedbackEjercicioDTO){
        FeedbackEjercicio feedbackEjercicio = convertDtoToEntity(feedbackEjercicioDTO);
        feedbackejercicioRepository.save(feedbackEjercicio);
    }

}
