package es.uma.dao;

import es.uma.entity.TipoCantidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCantidadRepository extends JpaRepository<TipoCantidad, Integer> {
}
