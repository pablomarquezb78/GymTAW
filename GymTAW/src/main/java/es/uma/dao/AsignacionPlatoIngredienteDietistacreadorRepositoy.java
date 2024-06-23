package es.uma.dao;

import es.uma.entity.AsignacionPlatoIngredienteDietistaCreador;
import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@author: Jaime Ezequiel Rodriguez Rodriguez
//@author: Pablo Miguel Aguilar Blanco

public interface AsignacionPlatoIngredienteDietistacreadorRepositoy extends JpaRepository<AsignacionPlatoIngredienteDietistaCreador, Integer> {

    @Query("select a from AsignacionPlatoIngredienteDietistaCreador a where a.ingrediente = :ingrediente and a.plato = :plato and a.dietista = :dietista")
    List<AsignacionPlatoIngredienteDietistaCreador> getAsignacionBy(@Param("ingrediente") Ingrediente ingrediente,@Param("plato") Plato plato,@Param("dietista") User dietista);

    @Query("select a from AsignacionPlatoIngredienteDietistaCreador a where a.dietista.id = :id ")
    List<AsignacionPlatoIngredienteDietistaCreador> getByDietist(@Param("id") Integer id);


}
