package es.uma.dao;

import es.uma.entity.Rutina;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RutinaRepository extends JpaRepository<Rutina,Integer> {

    @Query("SELECT r FROM Rutina r WHERE r IN (SELECT d.rutina FROM DiaEntrenamiento d WHERE d.cliente = :user AND d.fecha >= :fechaInicio AND d.fecha <= :fechaFin)")
    List<Rutina> encontrarRutinasPorClienteYFechas(@Param("user") User user, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT r FROM Rutina r WHERE r IN (SELECT d.rutina FROM DiaEntrenamiento d WHERE d.cliente = :user AND d.fecha = :fecha)")
    Rutina encontrarRutinasPorClienteYFecha(@Param("user") User user, @Param("fecha") LocalDate fecha);

    @Query("select r from Rutina r where r.entrenador.id = :trainerId")
    public List<Rutina> listarRutinasUsuario(@Param("trainerId") Integer trainerId);

    @Query("SELECT r FROM Rutina r WHERE r.nombre like %:nombre%")
    public List<Rutina> buscarPorNombre(@Param("nombre") String nombre);

     @Query("SELECT r FROM Rutina r WHERE r.nombre like %:nombre% and r.entrenador.id = :identrenador")
    public List<Rutina> buscarPorNombreyEntrenador(@Param("nombre") String nombre,@Param("identrenador") Integer id);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("SELECT r FROM Rutina r WHERE r.entrenador.id = :id")
    public List<Rutina> buscarPorEntrenador(@Param("id") Integer id);
}
