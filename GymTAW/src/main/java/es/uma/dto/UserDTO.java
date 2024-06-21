package es.uma.dto;

import es.uma.entity.UserRol;
import lombok.Data;


import java.time.LocalDate;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private Integer telefono;
    private Integer peso;
    private Integer altura;
    private LocalDate fechaNacimiento;
    private String descripcionPersonal;
    private UserRolDTO rol;

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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }

    public void setDescripcionPersonal(String descripcionPersonal) {
        this.descripcionPersonal = descripcionPersonal;
    }

    public UserRolDTO getRol() {
        return rol;
    }

    public void setRol(UserRolDTO rol) {
        this.rol = rol;
    }
}
