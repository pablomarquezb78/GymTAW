package es.uma.dao;

import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackejercicioRepository extends JpaRepository<FeedbackEjercicio,Integer> {

    @Query("select f from FeedbackEjercicio f where f.implementacionEjercicioRutina.id = :idImplementacion and f.serie= :Set")
    FeedbackEjercicio encontrarFeedbackEjercicioPorImplementacionYSet(@Param("idImplementacion") Integer idImplementacion, @Param("Set") String Set);

    @Query("select f from FeedbackEjercicio f where f.implementacionEjercicioRutina.id = :idImplementacion")
    List<FeedbackEjercicio> encontrarFeedbackEjercicioPorImplementacion(@Param("idImplementacion") Integer idImplementacion);

}
