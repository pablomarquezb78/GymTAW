package es.uma.dao;

import es.uma.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select distinct i from Ingrediente i order by i.id desc")
    List<Ingrediente> getUltimosIngredientesAdded();
}
