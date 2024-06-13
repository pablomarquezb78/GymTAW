package es.uma.dao;

import es.uma.entity.AsignacionPlatoIngredienteDietistacreador;
import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsignacionPlatoIngredienteDietistacreadorRepositoy extends JpaRepository<AsignacionPlatoIngredienteDietistacreador, Integer> {

    @Query("select a from AsignacionPlatoIngredienteDietistacreador a where a.ingrediente = :ingrediente and a.plato = :plato and a.dietista = :dietista")
    List<AsignacionPlatoIngredienteDietistacreador> getAsignacionBy(@Param("ingrediente") Ingrediente ingrediente,@Param("plato") Plato plato,@Param("dietista") User dietista);
}
