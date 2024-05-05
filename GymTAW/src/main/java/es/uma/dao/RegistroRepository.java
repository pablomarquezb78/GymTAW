package es.uma.dao;

import es.uma.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {
}
