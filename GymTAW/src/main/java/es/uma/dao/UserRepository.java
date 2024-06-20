package es.uma.dao;

import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.id = :id")
    User findById(@Param("id") int id);

    @Query("select u from User u where u.username = :username and u.password = :password")
    User findByUsernamePassword(@Param("username") String username, @Param("password") String password);

    @Query("select u from User u,AsignacionClienteEntrenador asig WHERE u.id = asig.cliente.id and asig.entrenador = :entrenador")
    List<User> clientesAsociadosConEntrenador(@Param("entrenador") User entrenador);

    @Query("select u from User u,AsignacionClienteEntrenador asig WHERE u.id = asig.cliente.id and asig.entrenador = :entrenador and u.nombre like %:nombre%")
    List<User> clientesAsociadosConEntrenadorYNombre(@Param("entrenador") User entrenador,@Param("nombre") String nombre);

    @Query("select u from User u,AsignacionClienteDietista asig WHERE u.id = asig.cliente.id and asig.dietista = :dietista")
    List<User> clientesAsociadosConDietista(@Param("dietista") User dietista);

    @Query("select u from User u WHERE u.rol.id = 2")
    List<User> listarClientes();

    @Query("select u from User u WHERE u.rol.id = 3 or u.rol.id = 4")
    List<User> listarEntrenadores();

    @Query("select u from User u WHERE u.rol.id = 5")
    List<User> listarDietistas();

    @Query("select u from User u where (u.rol.id = 3 or u.rol.id = 4) and u not in (select ace.entrenador from AsignacionClienteEntrenador ace) and u not in :entrenadores")
    List<User> entrenadoresNoAsociadosAlCliente(@Param("entrenadores") List<User> entrenadores);

    @Query("select u from User u where u.rol.id = 5 and u not in (select acd.dietista from AsignacionClienteDietista acd) and u not in :dietistas")
    List<User> dietistasNoAsociadosAlCliente(@Param("dietistas") List<User> dietistas);

    @Query("select u from User u where u.nombre like concat('%', :nombre, '%') and u.apellidos like concat('%', :apellidos, '%')")
    List<User> filtrarUsuarios(@Param("nombre") String nombre, @Param("apellidos") String apellidos);

    @Query("select u from User u where u.nombre like concat('%', :nombre, '%') and u.apellidos like concat('%', :apellidos, '%') and u.fechaNacimiento >= :fechaNacimiento")
    List<User> filtrarUsuariosConFecha(@Param("nombre") String nombre, @Param("apellidos") String apellidos, @Param("fechaNacimiento") LocalDate localDate);

    @Query("select u from User u where u.nombre like concat('%', :nombre, '%') and u.apellidos like concat('%', :apellidos, '%') and u.fechaNacimiento >= :fechaNacimiento and u.rol.id = :rol")
    List<User> filtrarUsuariosConRol(@Param("nombre") String nombre, @Param("apellidos") String apellidos,@Param("fechaNacimiento")LocalDate fechaNacimiento, @Param("rol") Integer rol);



}
