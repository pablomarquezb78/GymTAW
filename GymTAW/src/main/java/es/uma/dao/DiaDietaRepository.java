package es.uma.dao;

import es.uma.entity.DiaDieta;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DiaDietaRepository extends JpaRepository<DiaDieta, Integer> {

    @Query("select distinct d from DiaDieta d where d.dietista = :dietista and d.cliente = :cliente and year(d.fecha) = year(:fecha) and month(d.fecha) = month(:fecha) and day(d.fecha) = day(:fecha)")
    DiaDieta findByFecha(@Param("dietista") User dietista, @Param("cliente") User cliente, @Param("fecha") LocalDate fecha);

}
