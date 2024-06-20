package es.uma.dao;

import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackejercicioRepository extends JpaRepository<FeedbackEjercicio,Integer> {

    @Query("select f from FeedbackEjercicio f where f.implementacion.id=:implementacionId and f.diaEntrenamiento.id=:diaId")
    public FeedbackEjercicio encontrarFeedbackEjercicioPorImplementacionYDia(@Param("implementacionId") Integer implementacionId, @Param("diaId") Integer diaId);

}
