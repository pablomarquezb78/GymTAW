package es.uma.dao;

import es.uma.entity.*;
import es.uma.ui.IngredienteImplementandoUI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CantidadIngredientePlatoComidaRepository extends JpaRepository<CantidadIngredientePlatoComida, Integer> {

    @Query("select cipc from CantidadIngredientePlatoComida cipc where cipc.plato.id = :id")
    List<CantidadIngredientePlatoComida> buscarPorPlato(@Param("id") Integer id);

    @Query("select ing from CantidadIngredientePlatoComida cipc, Ingrediente ing where cipc.plato.id = :id and cipc.ingrediente = ing")
    List<Ingrediente> buscarIngredientesPorPlato(@Param("id") Integer id);

    @Query("select cipc from CantidadIngredientePlatoComida cipc where cipc.plato = :plato and cipc.comida.diaDieta.cliente.nombre like concat('%', :nombreCliente, '%') and cipc.comida.diaDieta.dietista.nombre like concat('%', :nombreDietista, '%') and cipc.comida.tipoComida = :tipoComida")
    List<CantidadIngredientePlatoComida> filtrarPlatos(@Param("plato") Plato plato, @Param("nombreCliente") String nombreCliente, @Param("nombreDietista") String nombreDietista, @Param("tipoComida") TipoComida tipoComida);

    @Query("select cipc from CantidadIngredientePlatoComida cipc where cipc.plato = :plato and cipc.comida.diaDieta.cliente.nombre like concat('%', :nombreCliente, '%') and cipc.comida.diaDieta.dietista.nombre like concat('%', :nombreDietista, '%') and cipc.comida.tipoComida = :tipoComida and cipc.cantidad >= :cantidad")
    List<CantidadIngredientePlatoComida> filtrarPlatosConCantidad(@Param("plato") Plato plato, @Param("nombreCliente") String nombreCliente, @Param("nombreDietista") String nombreDietista, @Param("tipoComida") TipoComida tipoComida, @Param("cantidad") Integer cantidad);

    @Query("select p from CantidadIngredientePlatoComida c, Plato p where c.comida = :comida and c.plato = p")
    List<Plato> findPlatosInComida(@Param("comida")Comida comida);

    @Query("select c from CantidadIngredientePlatoComida c where c.comida = :comida and c.plato = :plato")
    List<CantidadIngredientePlatoComida> findCantidadByPlatoComida(@Param("plato") Plato plato, @Param("comida") Comida comida);

    @Query("select c from CantidadIngredientePlatoComida c where c.comida = :comida and c.plato = :plato and c.ingrediente = :ingrediente")
    List<CantidadIngredientePlatoComida> findCantidadByPlatoComidaIngrediente(@Param("plato") Plato plato, @Param("comida") Comida comida, @Param("ingrediente")Ingrediente ingrediente);

    @Query("select distinct c from CantidadIngredientePlatoComida c order by c.id desc limit 1")
    CantidadIngredientePlatoComida getUltimaCantidadAdded();
}
