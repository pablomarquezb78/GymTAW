package es.uma.dao;

import es.uma.entity.Ejercicio;
import es.uma.entity.ImplementacionEjercicioRutina;
import es.uma.entity.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImplementacionEjercicioRutinaRepository extends JpaRepository<ImplementacionEjercicioRutina, Integer> {

    @Query("SELECT i from ImplementacionEjercicioRutina  i where i.rutina.id = :idrutina")
    List<ImplementacionEjercicioRutina> encontrarImplementacionesPorRutinaID(@Param("idrutina") Integer idrutina);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select i from ImplementacionEjercicioRutina i where i.ejercicio = :ejercicio")
    List<ImplementacionEjercicioRutina> buscarPorEjercicio(@Param("ejercicio") Ejercicio ejercicio);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select i from ImplementacionEjercicioRutina i where i.ejercicio = :ejercicio and i.rutina = :rutina and i.sets like concat('%', :series, '%') and i.repeticiones  like concat('%', :repeticiones, '%') and i.peso like concat('%', :peso, '%') and i.tiempo like concat('%', :tiempo, '%') and i.metros like concat('%', :metros, '%') and i.kilocalorias like concat('%', :kcal, '%')")
    List<ImplementacionEjercicioRutina> filtrarImplementaciones(@Param("ejercicio") Ejercicio ejercicio, @Param("rutina") Rutina rutina, @Param("series") String series,
    @Param("repeticiones") String repeticiones, @Param("peso") String peso, @Param("tiempo") String tiempo, @Param("metros") String metros, @Param("kcal") String kcal);

}
