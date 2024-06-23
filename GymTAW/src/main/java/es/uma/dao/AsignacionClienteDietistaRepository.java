package es.uma.dao;

import es.uma.entity.AsignacionClienteDietista;
import es.uma.entity.AsignacionClienteEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@author: Pablo Miguel Aguilar Blanco

public interface AsignacionClienteDietistaRepository extends JpaRepository<AsignacionClienteDietista, Integer> {
    @Query("select acd from AsignacionClienteDietista acd where acd.cliente.id = :id")
    public List<AsignacionClienteDietista> buscarPorCliente(@Param("id") Integer id);

    @Query("select acd from AsignacionClienteDietista acd where acd.dietista.id = :id")
    public List<AsignacionClienteDietista> buscarPorDietista(@Param("id") Integer id);
}
