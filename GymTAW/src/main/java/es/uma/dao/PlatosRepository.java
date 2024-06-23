package es.uma.dao;

import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlatosRepository extends JpaRepository<Plato, Integer> {

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select p from Plato p where p.id = :id")
    Plato findPlatoById(@Param("id") int id);

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select distinct p from Plato p, AsignacionPlatoIngredienteDietistaCreador a where a.plato = p and a.dietista = :dietista")
    List<Plato> getPlatosLinkedToDietista(@Param("dietista") User dietista);

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select distinct i from Ingrediente i,AsignacionPlatoIngredienteDietistaCreador a where a.plato = :plato and a.ingrediente = i")
    List<Ingrediente> getIngredientesLinkedToPlato(@Param("plato") Plato plato);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select p from Plato p where p.nombre like concat('%', :nombre, '%') and p.tiempoDePreparacion like concat('%', :tiempo, '%') and p.receta like concat('%', :receta, '%')")
    List<Plato> filtrarPlatos(@Param("nombre") String nombre, @Param("tiempo") String tiempo, @Param("receta") String receta);

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select distinct p from Plato p order by p.id desc limit 1")
    Plato getUltimoPlatoAdded();
}
