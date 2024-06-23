package es.uma.dao;

import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where u.id = :id")
    User findById(@Param("id") int id);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where u.username = :username and u.password = :password")
    User findByUsernamePassword(@Param("username") String username, @Param("password") String password);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u,AsignacionClienteEntrenador asig WHERE u.id = asig.cliente.id and asig.entrenador = :entrenador")
    List<User> clientesAsociadosConEntrenador(@Param("entrenador") User entrenador);

    @Query("select u from User u,AsignacionClienteEntrenador asig WHERE u.id = asig.cliente.id and asig.entrenador = :entrenador and u.nombre like %:nombre%")
    List<User> clientesAsociadosConEntrenadorYNombre(@Param("entrenador") User entrenador,@Param("nombre") String nombre);

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    @Query("select u from User u,AsignacionClienteDietista asig WHERE u.id = asig.cliente.id and asig.dietista = :dietista")
    List<User> clientesAsociadosConDietista(@Param("dietista") User dietista);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u WHERE u.rol.id = 2")
    List<User> listarClientes();

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u WHERE u.rol.id = 3 or u.rol.id = 4")
    List<User> listarEntrenadores();

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u WHERE u.rol.id = 5")
    List<User> listarDietistas();

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where (u.rol.id = 3 or u.rol.id = 4) and u not in :entrenadores")
    List<User> entrenadoresNoAsociadosAlCliente(@Param("entrenadores") List<User> entrenadores);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where u.rol.id = 5 and u not in :dietistas")
    List<User> dietistasNoAsociadosAlCliente(@Param("dietistas") List<User> dietistas);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where u.nombre like concat('%', :nombre, '%') and u.apellidos like concat('%', :apellidos, '%')")
    List<User> filtrarUsuarios(@Param("nombre") String nombre, @Param("apellidos") String apellidos);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where u.nombre like concat('%', :nombre, '%') and u.apellidos like concat('%', :apellidos, '%') and u.fechaNacimiento >= :fechaNacimiento")
    List<User> filtrarUsuariosConFecha(@Param("nombre") String nombre, @Param("apellidos") String apellidos, @Param("fechaNacimiento") LocalDate fechaNacimiento);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where u.nombre like concat('%', :nombre, '%') and u.apellidos like concat('%', :apellidos, '%') and u.rol.id = :rol")
    List<User> filtrarUsuariosConRol(@Param("nombre") String nombre, @Param("apellidos") String apellidos, @Param("rol") Integer rol);

    //@author: Pablo Miguel Aguilar Blanco
    @Query("select u from User u where u.nombre like concat('%', :nombre, '%') and u.apellidos like concat('%', :apellidos, '%') and u.fechaNacimiento >= :fechaNacimiento and u.rol.id = :rol")
    List<User> filtrarUsuariosConRolYFecha(@Param("nombre") String nombre, @Param("apellidos") String apellidos,@Param("fechaNacimiento")LocalDate fechaNacimiento, @Param("rol") Integer rol);

}
