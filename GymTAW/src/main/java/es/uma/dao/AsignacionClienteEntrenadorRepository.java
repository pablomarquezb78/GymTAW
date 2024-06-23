package es.uma.dao;

import es.uma.entity.AsignacionClienteEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//@author: Pablo Miguel Aguilar Blanco

public interface AsignacionClienteEntrenadorRepository extends JpaRepository<AsignacionClienteEntrenador, Integer> {
    @Query("select ace from AsignacionClienteEntrenador ace where ace.cliente.id = :id")
    public List<AsignacionClienteEntrenador> buscarPorCliente(@Param("id") Integer id);

    @Query("select ace from AsignacionClienteEntrenador ace where ace.entrenador.id = :id")
    public List<AsignacionClienteEntrenador> buscarPorEntrenador(@Param("id") Integer id);
}
