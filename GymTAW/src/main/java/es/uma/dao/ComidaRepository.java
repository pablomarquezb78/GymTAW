package es.uma.dao;

import es.uma.entity.Comida;
import es.uma.entity.DiaDieta;
import es.uma.entity.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComidaRepository extends JpaRepository<Comida, Integer> {

    @Query("select c from Comida c where c.diaDieta = :diaDieta")
    List<Comida> findByDiaDieta(@Param("diaDieta") DiaDieta diaDieta);

    @Query("select distinct c from Comida c where c.diaDieta = :diaDieta and c.tipoComida = :tipoComida")
    List<Comida> findByDiaAndTipoComido(@Param("diaDieta") DiaDieta diaDieta, @Param("tipoComida")TipoComida tipoComida);

}
