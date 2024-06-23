package es.uma.ui;

import es.uma.entity.TipoEjercicio;
import jakarta.persistence.*;

//@author: Pablo Miguel Aguilar Blanco

public class EjercicioUI {
    private Integer id;
    private String nombre;
    private Integer idTipo;
    private String descripcion;
    private String enlaceVideo;
    private String trainerEjercicio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnlaceVideo() {
        return enlaceVideo;
    }

    public void setEnlaceVideo(String enlaceVideo) {
        this.enlaceVideo = enlaceVideo;
    }

    public String getTrainerEjercicio() {
        return trainerEjercicio;
    }

    public void setTrainerEjercicio(String trainerEjercicio) {
        this.trainerEjercicio = trainerEjercicio;
    }

    public boolean estaVacio(){
        return (this.getNombre().isEmpty() && this.getDescripcion().isEmpty()  && this.getIdTipo() == null);
    }
}
