package es.uma.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "iduser", nullable = false)
    private Integer id;

    @Column(name = "username", length = 40)
    private String username;

    @Column(name = "password", length = 70)
    private String password;

    @Column(name = "nombre", length = 70)
    private String nombre;

    @Column(name = "apellidos", length = 150)
    private String apellidos;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "peso")
    private Integer peso;

    @Column(name = "altura")
    private Integer altura;

    @Column(name = "fecha_nacimiento")
    private Instant fechaNacimiento;

    @Column(name = "descripcion_personal", length = 750)
    private String descripcionPersonal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol")
    private UserRol rol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Instant getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Instant fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }

    public void setDescripcionPersonal(String descripcionPersonal) {
        this.descripcionPersonal = descripcionPersonal;
    }

    public UserRol getRol() {
        return rol;
    }

    public void setRol(UserRol rol) {
        this.rol = rol;
    }

}