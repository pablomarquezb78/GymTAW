package es.uma.dao;

import es.uma.entity.CantidadIngredientePlatoComida;
import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;
import es.uma.entity.TipoComida;
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
}
