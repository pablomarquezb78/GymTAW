package es.uma.ui;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class Usuario {
    protected Integer id;
    protected String username;
    protected String password;
    protected String nombre;
    protected String apellidos;
    protected Integer telefono;
    protected Integer peso;
    protected Integer altura;
    protected LocalDate fechaNacimiento;
    protected Integer rol;

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

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }
    public boolean estaVacio(){
        return (this.getNombre().isEmpty() && this.getApellidos().isEmpty()  && this.getRol() == null && this.getPeso() == null
                && this.getAltura() == null && this.getTelefono() == null);
    }

}
