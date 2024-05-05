package es.uma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "registro")
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idregistro", nullable = false)
    private Integer id;

    @Column(name = "username", length = 45)
    private String username;

    @Column(name = "password", length = 70)
    private String password;

    @Column(name = "nombre", length = 70)
    private String nombre;

    @Column(name = "apellidos", length = 150)
    private String apellidos;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "fecha_nacimiento")
    private Instant fechaNacimiento;

    @Column(name = "rol")
    private Integer rol;

}