package es.uma.dao;

import es.uma.entity.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoComidaRepository extends JpaRepository<TipoComida, Integer> {
}
