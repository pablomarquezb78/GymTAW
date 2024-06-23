package es.uma.dao;

import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.FeedbackEjercicioserie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackejercicioserieRepository extends JpaRepository<FeedbackEjercicioserie,Integer> {

    //@author: Pablo Márquez Benítez
    @Query("select f from FeedbackEjercicioserie f where f.feedbackEjercicio.id=:feedbackEjercicioId")
    public List<FeedbackEjercicioserie> encontrarPorFeedbackEjercicio(@Param("feedbackEjercicioId") Integer feedbackEjercicioId);

    //@author: Pablo Márquez Benítez
    @Query("select f from FeedbackEjercicioserie f where f.feedbackEjercicio.id=:feedbackEjercicioId and f.serie=:set")
    public FeedbackEjercicioserie encontrarPorFeedbackEjercicioYSerie(@Param("feedbackEjercicioId") Integer feedbackEjercicioId, @Param("set") String set);

}
