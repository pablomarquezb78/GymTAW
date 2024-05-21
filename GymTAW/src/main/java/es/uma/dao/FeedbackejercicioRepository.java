package es.uma.dao;

import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.FeedbackEjercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackejercicioRepository extends JpaRepository<FeedbackEjercicio,Integer> {

    @Query("select f from FeedbackEjercicio f where f.implementacion=:implementacion and f.diaEntrenamiento=:dia")
    public FeedbackEjercicio encontrarFeedbackEjercicioPorImplementacionYDia(@Param("implementacion") ImplementacionEjercicioRutina implementacion, @Param("dia") DiaEntrenamiento dia);

}
