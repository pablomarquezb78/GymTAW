package es.uma.dao;

import es.uma.entity.Comida;
import es.uma.entity.DiaDieta;
import es.uma.entity.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComidaRepository extends JpaRepository<Comida, Integer> {

    //@author: Pablo Márquez Benítez
    @Query("select c from Comida c where c.diaDieta.id = :diaDietaId order by c.tipoComida.id")
    List<Comida> findByDiaDieta(@Param("diaDietaId") Integer diaDietaId);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select c from Comida c where c.diaDieta.cliente.id = :id ")
    List<Comida> findByCustomer(@Param("id") Integer id);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select c from Comida c where c.diaDieta.dietista.id = :id ")
    List<Comida> findByDietist(@Param("id") Integer id);

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select distinct c from Comida c where c.diaDieta = :diaDieta and c.tipoComida = :tipoComida")
    List<Comida> findByDiaAndTipoComido(@Param("diaDieta") DiaDieta diaDieta, @Param("tipoComida")TipoComida tipoComida);

}
