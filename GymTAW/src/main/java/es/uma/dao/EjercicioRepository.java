package es.uma.dao;

import es.uma.entity.Ejercicio;
import es.uma.entity.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Integer> {

    @Query("select i.ejercicio from ImplementacionEjercicioRutina i where i.rutina =:rutina")
    public String findEjerciciosPorRutina(@Param("rutina") Rutina rutina);

}
