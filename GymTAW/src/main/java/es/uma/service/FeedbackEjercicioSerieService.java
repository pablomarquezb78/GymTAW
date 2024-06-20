package es.uma.service;

import es.uma.dao.FeedbackejercicioRepository;
import es.uma.dao.FeedbackejercicioserieRepository;
import es.uma.dto.FeedbackEjercicioserieDTO;
import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.FeedbackEjercicioserie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackEjercicioSerieService {

    @Autowired
    protected FeedbackejercicioserieRepository feedbackejercicioserieRepository;
    @Autowired
    private FeedbackEjercicioService feedbackEjercicioService;
    @Autowired
    private FeedbackejercicioRepository feedbackejercicioRepository;

    public List<FeedbackEjercicioserieDTO> getFeedbackSeriePorFeedbackEjercicio(Integer feedbackEjercicioId){
        return feedbackejercicioserieRepository.encontrarPorFeedbackEjercicio(feedbackEjercicioId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public FeedbackEjercicioserieDTO getFeedbackPorEjecicioYSerie(Integer feedbackEjercicioId, String set){
        FeedbackEjercicioserie feedbackEjercicioserie = feedbackejercicioserieRepository.encontrarPorFeedbackEjercicioYSerie(feedbackEjercicioId,set);
        if(feedbackEjercicioserie!=null){
            return convertEntityToDto(feedbackEjercicioserie);
        }else{
            return null;
        }
    }

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

    public FeedbackEjercicioserie convertDtoToEntity(FeedbackEjercicioserieDTO feedbackEjercicioserieDTO) {
        FeedbackEjercicioserie feedbackEjercicioserie = new FeedbackEjercicioserie();
        feedbackEjercicioserie.setId(feedbackEjercicioserieDTO.getId());
        feedbackEjercicioserie.setSerie(feedbackEjercicioserieDTO.getSerie());
        feedbackEjercicioserie.setPesoRealizado(feedbackEjercicioserieDTO.getPesoRealizado());
        feedbackEjercicioserie.setMetrosRealizado(feedbackEjercicioserieDTO.getMetrosRealizado());
        feedbackEjercicioserie.setRepeticionesRealizadas(feedbackEjercicioserieDTO.getRepeticionesRealizadas());
        feedbackEjercicioserie.setKilocaloriasRealizado(feedbackEjercicioserieDTO.getKilocaloriasRealizado());
        feedbackEjercicioserie.setTiempoRealizado(feedbackEjercicioserieDTO.getTiempoRealizado());
        return feedbackEjercicioserie;
    }


    public List<FeedbackEjercicioserieDTO> convertListEntityToDto(List<FeedbackEjercicioserie> feedbackEjercicioseries){
        List<FeedbackEjercicioserieDTO> feedbackEjercicioserieDTOSList = new ArrayList<>();
        for (FeedbackEjercicioserie feedbackEjercicioserie : feedbackEjercicioseries){
            feedbackEjercicioserieDTOSList.add(this.convertEntityToDto(feedbackEjercicioserie));
        }
        return feedbackEjercicioserieDTOSList;
    }

    public void borrarFeedbackEjercicioSerie(Integer feedbackEjercicioserieId){
        FeedbackEjercicioserie feedbackEjercicioserie = feedbackejercicioserieRepository.findById(feedbackEjercicioserieId).orElse(null);
        feedbackejercicioserieRepository.delete(feedbackEjercicioserie);
    }

    public void prerararFeedbackEjercicioSeries(Integer serie,Integer feedbackEjercicioId){
        FeedbackEjercicio feedbackEjercicio = feedbackejercicioRepository.findById(feedbackEjercicioId).orElse(null);

        FeedbackEjercicioserie feedbackEjercicioSerie = new FeedbackEjercicioserie();
        feedbackEjercicioSerie.setSerie("" + serie);
        feedbackEjercicioSerie.setFeedbackEjercicio(feedbackEjercicio);

        feedbackejercicioserieRepository.save(feedbackEjercicioSerie);
    }

    public void guardarFeedbackEjercicioSerie(FeedbackEjercicioserieDTO feedbackEjercicioserieDTO){
        FeedbackEjercicioserie feedbackEjercicioserie = convertDtoToEntity(feedbackEjercicioserieDTO);
        feedbackejercicioserieRepository.save(feedbackEjercicioserie);
    }

}
