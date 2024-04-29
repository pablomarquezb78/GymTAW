package es.uma.dao;

import es.uma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.id = :id")
    public User findById(@Param("id") int id);

    @Query("select u from User u where u.username = :username and u.password = :password")
    public User findByUsernamePassword(@Param("username") String username, @Param("password") String password);

}
