package es.uma.dao;

import es.uma.entity.FeedbackEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackejercicioRepository extends JpaRepository<FeedbackEjercicio,Integer> {

    @Query("select f from FeedbackEjercicio f where f.implementacion.id=:implementacionId and f.diaEntrenamiento.id=:diaId")
    public FeedbackEjercicio encontrarFeedbackEjercicioPorImplementacionYDia(@Param("implementacionId") Integer implementacionId, @Param("diaId") Integer diaId);

    @Query("select f from FeedbackEjercicio f where f.diaEntrenamiento.id=:diaId")
    public List<FeedbackEjercicio> encontrarFeedbackEjercicioPorDia(@Param("diaId")Integer diaId);

}
