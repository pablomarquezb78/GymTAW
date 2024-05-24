package es.uma.dao;

import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;
import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlatosRepository extends JpaRepository<Plato, Integer> {

    @Query("select p from Plato p where p.id = :id")
    public Plato findPlatoById(@Param("id") int id);

    @Query("select distinct p from Plato p, DiaDieta dieta, Comida comida, CantidadIngredientePlatoComida implementacion where " +
            "dieta.dietista = :dietista and comida.diaDieta = dieta and implementacion.comida = comida and implementacion.plato = p")
    public List<Plato> getPlatosFromDietista(@Param("dietista") User dietista);

    @Query("select distinct i from Ingrediente i,CantidadIngredientePlatoComida implementacion where implementacion.plato = :plato and implementacion.ingrediente = i")
    public List<Ingrediente> getIngredientesFromPlato(@Param("plato") Plato plato);

    @Query("select p from Plato p where p.nombre like concat('%', :nombre, '%') and p.tiempoDePreparacion like concat('%', :tiempo, '%') and p.receta like concat('%', :receta, '%')")
    List<Plato> filtrarPlatos(@Param("nombre") String nombre, @Param("tiempo") String tiempo, @Param("receta") String receta);
}
