package es.uma.dao;

import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.FeedbackEjercicioserie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackejercicioserieRepository extends JpaRepository<FeedbackEjercicioserie,Integer> {

    @Query("select f from FeedbackEjercicioserie f where f.feedbackEjercicio=:feedbackEjercicio")
    public List<FeedbackEjercicioserie> encontrarPorFeedbackEjercicio(@Param("feedbackEjercicio") FeedbackEjercicio feedbackEjercicio);

    @Query("select f from FeedbackEjercicioserie f where f.feedbackEjercicio=:feedbackEjercicio and f.serie=:set")
    public FeedbackEjercicioserie encontrarPorFeedbackEjercicioYSerie(@Param("feedbackEjercicio") FeedbackEjercicio feedbackEjercicio, @Param("set") String set);

}
