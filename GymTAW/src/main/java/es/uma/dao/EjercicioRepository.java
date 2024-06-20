package es.uma.dao;

import es.uma.entity.Ejercicio;
import es.uma.entity.Rutina;
import es.uma.entity.TipoEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;



public interface EjercicioRepository extends JpaRepository<Ejercicio, Integer> {

    @Query("select i.ejercicio from ImplementacionEjercicioRutina i where i.rutina =:rutina")
    String findEjerciciosPorRutina(@Param("rutina") Rutina rutina);

    @Query("select e from Ejercicio e where e.nombre  like concat('%', :nombre, '%') and e.descripcion  like concat('%', :descripcion, '%')")
    List<Ejercicio> filtrarEjercicios(@Param("nombre") String nombre, @Param("descripcion") String descripcion);

    @Query("select e from Ejercicio e where e.nombre  like concat('%', :nombre, '%') and e.descripcion  like concat('%', :descripcion, '%') and e.tipo.id = :idTipo")
    List<Ejercicio> filtrarEjerciciosConTipo(@Param("nombre") String nombre, @Param("descripcion") String descripcion, @Param("idTipo") Integer idTipo);

    @Query("select e from Ejercicio e where  e.tipo = :tipo")
    public List<Ejercicio> filtrarEjercicioSoloDeTipo(@Param("tipo")TipoEjercicio tipo);

    @Query("select e from Ejercicio e where  e.tipo.id = :tipoid")
    public List<Ejercicio> filtrarEjercicioSoloDeTipoID(@Param("tipoid") Integer tipoid);

    @Query("select e from Ejercicio e where e.nombre like %:nombre%")
    public List<Ejercicio> filtrarEjercicioPorNombre(@Param("nombre") String nombre);

    @Query("select e from Ejercicio e where e.tipo.id = :idTipo")
    public List<Ejercicio> filtrarEjercicioPorTipo(@Param("idTipo") Integer idTipo);
}
