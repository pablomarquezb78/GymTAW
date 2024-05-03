package es.uma.dao;

import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.id = :id")
    public User findById(@Param("id") int id);

    @Query("select u from User u where u.username = :username and u.password = :password")
    public User findByUsernamePassword(@Param("username") String username, @Param("password") String password);

    @Query("select u from User u,AsignacionClienteEntrenador asig WHERE u.id = asig.cliente.id and asig.entrenador = :entrenador")
    public List<User> clientesAsociadosConEntrenador(@Param("entrenador") User entrenador);

    @Query("select u from User u WHERE u.rol.id = 2")
    public List<User> listarClientes();

    @Query("select u from User u WHERE u.rol.id = 3 or u.rol.id = 4")
    public List<User> listarEntrenadores();

    @Query("select u from User u WHERE u.rol.id = 5")
    public List<User> listarDietistas();



}
