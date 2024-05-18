package es.uma.dao;

import es.uma.entity.Ejercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImplementacionEjercicioRutinaRepository extends JpaRepository<ImplementacionEjercicioRutina, Integer> {

    @Query("SELECT i from ImplementacionEjercicioRutina  i where i.rutina = :rutina")
    List<ImplementacionEjercicioRutina> encontrarImplementacionesPorRutinas(@Param("rutina") Rutina rutina);

    @Query("select i from ImplementacionEjercicioRutina i where i.ejercicio = :ejercicio")
    List<ImplementacionEjercicioRutina> buscarPorEjercicio(@Param("ejercicio") Ejercicio ejercicio);

}
