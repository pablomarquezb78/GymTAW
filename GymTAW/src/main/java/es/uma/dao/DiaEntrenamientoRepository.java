package es.uma.dao;

import es.uma.entity.DiaEntrenamiento;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaEntrenamientoRepository extends JpaRepository <DiaEntrenamiento,Integer> {

    @Query("SELECT d FROM DiaEntrenamiento d WHERE d.cliente = :cliente ORDER BY d.fecha ASC")
    public List<DiaEntrenamiento> diasEntrenamientosdeCliente (@Param("cliente") User cliente);

}
