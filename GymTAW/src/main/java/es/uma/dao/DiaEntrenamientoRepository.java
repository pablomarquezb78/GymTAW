package es.uma.dao;

import es.uma.entity.DiaEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DiaEntrenamientoRepository extends JpaRepository <DiaEntrenamiento,Integer> {

    @Query("SELECT d FROM DiaEntrenamiento d WHERE d.cliente.id = :cliente ORDER BY d.fecha ASC")
    public List<DiaEntrenamiento> diasEntrenamientosdeCliente (@Param("cliente") Integer cliente);

    @Query("SELECT d FROM DiaEntrenamiento d WHERE d.cliente.id = :cliente and d.fecha = :fecha")
    public DiaEntrenamiento diaEntrenamientoConcretoCliente(@Param("cliente") Integer cliente, @Param("fecha") LocalDate fecha);


}
