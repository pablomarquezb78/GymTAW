package es.uma.dao;

import es.uma.entity.CantidadIngredientePlatoComida;
import es.uma.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CantidadIngredientePlatoComidaRepository extends JpaRepository<CantidadIngredientePlatoComida, Integer> {

    @Query("select cipc from CantidadIngredientePlatoComida cipc where cipc.plato.id = :id")
    List<CantidadIngredientePlatoComida> buscarPorPlato(@Param("id") Integer id);

    @Query("select ing from CantidadIngredientePlatoComida cipc, Ingrediente ing where cipc.plato.id = :id and cipc.ingrediente = ing")
    List<Ingrediente> buscarIngredientesPorPlato(@Param("id") Integer id);
}
