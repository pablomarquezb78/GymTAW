package es.uma.dao;

import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ImplementacionEjercicioRutinaRepository extends JpaRepository<ImplementacionEjercicioRutina, Integer> {

    @Query("SELECT i from ImplementacionEjercicioRutina  i where i.rutina = :rutina")
    List<ImplementacionEjercicioRutina> encontrarImplementacionesPorRutinas(@Param("rutina") Rutina rutina);

}
