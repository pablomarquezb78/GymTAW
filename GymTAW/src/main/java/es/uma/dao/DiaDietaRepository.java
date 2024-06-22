package es.uma.dao;

import es.uma.entity.DiaDieta;
import es.uma.entity.Plato;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DiaDietaRepository extends JpaRepository<DiaDieta, Integer> {

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select distinct d from DiaDieta d where d.dietista = :dietista and d.cliente = :cliente and year(d.fecha) = year(:fecha) and month(d.fecha) = month(:fecha) and day(d.fecha) = day(:fecha)")
    DiaDieta findByFecha(@Param("dietista") User dietista, @Param("cliente") User cliente, @Param("fecha") LocalDate fecha);

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select distinct d from DiaDieta d order by d.id desc limit 1")
    DiaDieta getUltimoDiaDietaAdded();

    //@Author Pablo Márquez Benítez
    @Query("select distinct d from DiaDieta d where d.cliente.id = :clienteId and d.fecha = :fecha")
    DiaDieta diaDietaConcretoCliente(@Param("clienteId") Integer clienteId, @Param("fecha") LocalDate fecha);

}
