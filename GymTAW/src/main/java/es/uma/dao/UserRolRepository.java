package es.uma.dao;

import es.uma.entity.User;
import es.uma.entity.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRolRepository extends JpaRepository<UserRol, Integer> {
    @Query("select r from UserRol r where r.id != 1")
    public List<UserRol> buscarRolesNoAdmin();


    @Query("select r from UserRol r where r.id = 3 or r.id= 4")
    public List<UserRol> buscarEntrenadores();
}
