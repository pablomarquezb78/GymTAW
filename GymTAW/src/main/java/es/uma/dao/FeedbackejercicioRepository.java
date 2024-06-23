package es.uma.dao;

import es.uma.entity.FeedbackEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackejercicioRepository extends JpaRepository<FeedbackEjercicio,Integer> {

    //@author: Pablo Márquez Benítez
    @Query("select f from FeedbackEjercicio f where f.implementacion.id=:implementacionId and f.diaEntrenamiento.id=:diaId")
    public FeedbackEjercicio encontrarFeedbackEjercicioPorImplementacionYDia(@Param("implementacionId") Integer implementacionId, @Param("diaId") Integer diaId);

    //@author: Pablo Márquez Benítez
    @Query("select f from FeedbackEjercicio f where f.diaEntrenamiento.id=:diaId")
    public List<FeedbackEjercicio> encontrarFeedbackEjercicioPorDia(@Param("diaId")Integer diaId);

}
